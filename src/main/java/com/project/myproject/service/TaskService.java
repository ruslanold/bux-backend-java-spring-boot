package com.project.myproject.service;

import com.project.myproject.dao.CountryRepository;
import com.project.myproject.dao.ModerationStatusRepository;
import com.project.myproject.dao.ReportRepository;
import com.project.myproject.dao.TaskRepository;
import com.project.myproject.dto.*;
import com.project.myproject.entity.*;
import com.project.myproject.enums.ECountry;
import com.project.myproject.enums.EGeoFilter;
import com.project.myproject.enums.EModerationStatus;
import com.project.myproject.enums.EWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService{

    @Value("${defaultCommissionPercent}")
    private int defaultCommissionPercent;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ModerationStatusRepository moderationStatusRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IStorageService storageService;
    @Autowired
    private IURLService urlService;
    @Autowired
    private ReportService reportService;
    @Override
    public List<TaskDto> getTasks(String username) {
        ECountry country = ECountry.UKRAINE;

        return taskRepository.findAllByEnabledTrueAndGeoFilterAndNotExistsUsername(country, username).stream().map(t -> convertToTaskDto(t)).collect(Collectors.toList());
    }

    @Override
    public List<TaskUserDto> getUserTasks(String username) {
        List<Task> tasks = taskRepository.findAllByUserUsername(username);
        return tasks.stream().map(t -> convertToTaskUserDto(t)).collect(Collectors.toList());
    }

    @Override
    public TaskCategoryDto getTasksByCategoryNameAndUsername(String categoryName, String username) {
        Category category = categoryService.getCategoryWithTasksByNameAndUsername(categoryName, username);
        List<TaskDto> taskDtos = category.getTasks().stream().map(t -> convertToTaskDto(t)).collect(Collectors.toList());
        return new TaskCategoryDto(category.getId(), taskDtos);
    }

    @Override
    public Task getTaskById(long id) {

        Task task = taskRepository.getOne(id);

        if (task == null){
            throw new IllegalArgumentException("No task with such id: " + id);
        }

        return task;
    }

    @Override
    public TaskUserReportsDto getTaskByIdWithUserReports(long id, String username) {
        return convertToTaskUserReportsDto(getTaskById(id), username);
    }

    @Override
    public List<TaskDto> getTasksByUserId(long userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return tasks.stream().map(t -> convertToTaskDto(t)).collect(Collectors.toList());
    }

    private BigDecimal calcPriceWithCommission(BigDecimal price){
        BigDecimal basePercent = new BigDecimal(defaultCommissionPercent).divide(new BigDecimal(100));
        BigDecimal calcPercent = price.multiply(basePercent);
        BigDecimal priceWithCommission = price.add(calcPercent).setScale(4, RoundingMode.HALF_UP);

        return priceWithCommission;
    }

    @Override
    public TaskCreateUpdateDto createTask(TaskCreateUpdateDto task, String username) {

        Task taskEntity = new Task();

        BigDecimal priceWithCommission = calcPriceWithCommission(task.getPrice());

        ModerationStatus status = moderationStatusRepository.findByName(EModerationStatus.DRAFT);

        List<URL> urls = urlService.createAllUrls(task.getUrls());

        List<EWeek> days = task.getNotWorkOnDaysOfWeek();
        int sumOfDays = 0;
        if(days != null) {
          sumOfDays = days.stream().mapToInt(EWeek::getValue).reduce((acc, val) -> acc + val).orElse(0);
        }
        System.out.println(sumOfDays);

        List<Country> countries = countryRepository.findAllByNameIn(task.getCountries());

        EGeoFilter geoFilter = EGeoFilter.fromValue(task.getGeoFilter());
        if(geoFilter == null){
            throw new BadRequestException("Invalid geoFilter: " + task.getGeoFilter());
        }
        if(!geoFilter.equals(EGeoFilter.ALL)){
            taskEntity.setCountries(countries);
        }

        System.out.println(geoFilter);
        System.out.println(!geoFilter.equals(EGeoFilter.ALL));


        if(task.isRepeat()){
            taskEntity.setRepeat(task.isRepeat());
            taskEntity.setRepeatTime(task.getRepeatTime());
        }

        taskEntity.setTitle(task.getTitle());
        taskEntity.setUrls(urls);
        taskEntity.setDescription(task.getDescription());
        taskEntity.setDescForApproval(task.getDescForApproval());
        taskEntity.setUser(userService.getUserByUsername(username));
        taskEntity.setCategory(categoryService.getCategoryById(task.getCategoryId()));
        taskEntity.setStatus(status);
        taskEntity.setWorkTime(task.getWorkTime());

        taskEntity.setRepeatBeforeCheck(task.isRepeatBeforeCheck());
        taskEntity.setBalance(BigDecimal.ZERO);
        taskEntity.setNotWorkOnDaysOfWeek(sumOfDays);
        taskEntity.setGeoFilter(geoFilter);
        taskEntity.setPrice(task.getPrice());
        taskEntity.setPriceWithCommission(priceWithCommission);

        taskRepository.saveAndFlush(taskEntity);

        return task;
    }

    @Override
    public TaskCreateUpdateDto updateTask(long id, TaskCreateUpdateDto task, String username) {

        Task taskEntity = taskRepository.getOne(id);

        if (taskEntity == null){
            throw new IllegalArgumentException("No task with such id: " + id);
        }
        if(!taskEntity.getUser().getUsername().equals(username)){
            throw new BadRequestException("Task №{} does not belong to you." + id);
        }

        List<URL> urls = urlService.createAllUrls(task.getUrls());

        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setPrice(calcPriceWithCommission(task.getPrice()));
        taskEntity.setDescForApproval(task.getDescForApproval());
        taskEntity.setUrls(urls);
        taskEntity.setCategory(categoryService.getCategoryById(task.getCategoryId()));

        taskRepository.saveAndFlush(taskEntity);

        return task;
    }

    @Override
    public void updateBalance(Task task) {
        BigDecimal priceWithCommission = task.getPriceWithCommission();
        BigDecimal balance = task.getBalance();
        BigDecimal newBalance = balance.subtract(priceWithCommission);

        //TODO: If balance of the task is < less than one execution
        if(newBalance.compareTo(priceWithCommission) == -1){
            task.setEnabled(false);
        }


        System.out.println("PriceWithCommission: " + priceWithCommission);
        System.out.println("Balance: " + balance);
        System.out.println("NewBalance: " + newBalance);

        task.setBalance(newBalance);

        taskRepository.save(task);

    }

    @Override
    public void deleteTask(long id) {

        //TODO: check user has this task

        if (!taskRepository.existsById(id)){
            throw new IllegalArgumentException("No task with such id: " + id);
        }
        taskRepository.deleteById(id);
    }

    public TaskDto convertToTaskDto(Task task) {
        String imageFullUrl = storageService.getUrlFromS3(task.getUser().getImage()).toString();
        String priceWithCommission = task.getPriceWithCommission().toPlainString();
        List<TaskReportStatusDto> reportsStatus = taskRepository.countReportsById(task.getId());

        TaskUserInfoDto user = new TaskUserInfoDto(task.getUser().getId(), task.getUser().getUsername(), imageFullUrl);

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setDescForApproval(task.getDescForApproval());
        taskDto.setPriceWithCommission(priceWithCommission);
        taskDto.setUser(user);
        taskDto.setCategoryId(task.getCategory().getId());
        taskDto.setReportsStatus(reportsStatus);

        return taskDto;
    }

    public TaskUserReportsDto convertToTaskUserReportsDto(Task task, String username) {

        List<ReportDto> reports = reportService.getReports(task.getId(), username);

        TaskDto taskDto = convertToTaskDto(task);
        TaskUserReportsDto taskUserReportsDto = new TaskUserReportsDto();
        taskUserReportsDto.setId(taskDto.getId());
        taskUserReportsDto.setTitle(taskDto.getTitle());
        taskUserReportsDto.setDescription(taskDto.getDescription());
        taskUserReportsDto.setDescForApproval(taskDto.getDescForApproval());
        taskUserReportsDto.setPriceWithCommission(taskDto.getPriceWithCommission());
        taskUserReportsDto.setUser(taskDto.getUser());
        taskUserReportsDto.setCategoryId(taskDto.getCategoryId());
        taskUserReportsDto.setReportsStatus(taskDto.getReportsStatus());
        taskUserReportsDto.setReports(reports);
        return taskUserReportsDto;
    }

    public TaskUserDto convertToTaskUserDto(Task task) {
        String price = task.getPrice().toPlainString();

        List<TaskReportStatusDto> reportsStatus = taskRepository.countReportsById(task.getId());
        List<TaskReportStatusDto> allReportsStatus = Arrays.stream(EModerationStatus.values()).map(s -> {
            TaskReportStatusDto taskReportStatusDto = new TaskReportStatusDto(s,0);
            Optional<TaskReportStatusDto> optionalTaskReportStatusDto = reportsStatus.stream().filter(r -> r.getStatusName().equals(s)).findAny();

            if(optionalTaskReportStatusDto.isPresent()){
                return optionalTaskReportStatusDto.get();
            }

            return taskReportStatusDto;
        }).collect(Collectors.toList());

        TaskUserDto taskUserDto = new TaskUserDto();
        taskUserDto.setId(task.getId());
        taskUserDto.setTitle(task.getTitle());
        taskUserDto.setDescription(task.getDescription());
        taskUserDto.setPrice(price);
        taskUserDto.setBalance(task.getBalance().toPlainString());
        taskUserDto.setCategoryId(task.getCategory().getId());
        taskUserDto.setStatus(task.getStatus().getName());
        taskUserDto.setReportsStatus(allReportsStatus);

        return taskUserDto;
    }

}
