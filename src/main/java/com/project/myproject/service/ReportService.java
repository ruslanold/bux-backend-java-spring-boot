package com.project.myproject.service;

import com.project.myproject.dao.ModerationStatusRepository;
import com.project.myproject.dao.ReportRepository;
import com.project.myproject.dto.BadRequestException;
import com.project.myproject.dto.EntityNotFoundException;
import com.project.myproject.dto.ReportCreateUpdateDto;
import com.project.myproject.dto.ReportDto;
import com.project.myproject.entity.ModerationStatus;
import com.project.myproject.entity.Report;
import com.project.myproject.entity.Task;
import com.project.myproject.entity.User;
import com.project.myproject.enums.EModerationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService implements IReportService{

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ModerationStatusRepository moderationStatusRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITaskService taskService;


    @Override
    public List<ReportDto> getReports(long taskId, String username) {
        List<Report> reports = reportRepository.findAllByTaskIdAndUsername(taskId, username);
        if(reports.isEmpty()) {
            return new ArrayList<>();
        }
        List<ReportDto> reportsDto = reports.stream().map(this::convertToReportDto).collect(Collectors.toList());
        return reportsDto;

    }

    @Override
    public Report getReport(long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (!optionalReport.isPresent()){
            throw new EntityNotFoundException("No report with such id: " + id);
        }
        return optionalReport.get();
    }

    @Override
    public void reworkReport(long id, String username) {

        Report reportEntity = getAndCheckOrThrow(id, username);

        ModerationStatus status = moderationStatusRepository.findByName(EModerationStatus.REWORKING);
        reportEntity.setStatus(status);

        reportRepository.saveAndFlush(reportEntity);
    }

    @Override
    public void rejectReport(long id, String username) {
        Report reportEntity = getAndCheckOrThrow(id, username);

        ModerationStatus status = moderationStatusRepository.findByName(EModerationStatus.REJECTED);
        reportEntity.setStatus(status);

        reportRepository.saveAndFlush(reportEntity);
    }

    @Override
    public void approvalReport(long id, String username) {

        Report reportEntity = getAndCheckOrThrow(id, username);

        //TODO: send money to the report author
        Task task = reportEntity.getTask();
        taskService.updateBalance(task);

        User reportAuthor = reportEntity.getUser();
        userService.updateBalances(reportAuthor.getUsername(), task);


        ModerationStatus status = moderationStatusRepository.findByName(EModerationStatus.APPROVED);
        reportEntity.setStatus(status);

        reportRepository.saveAndFlush(reportEntity);
    }


    @Override
    public ReportDto createReport(ReportCreateUpdateDto report, String username) {

        //TODO: need to check if user has report with status DRAFT for this task
        //TODO: Is there money in the task?

        Task task = taskService.getTaskById(report.getTaskId());

        if(!task.isEnabled()){
            throw new BadRequestException("Task is disabled");
        }

        if(task.getUser().getUsername() == username){
            throw new BadRequestException("You can't do your tasks");
        }

        final int workTimeSec = task.getWorkTime();
        ZoneOffset offset = ZoneOffset.UTC;
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(workTimeSec);
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, offset);


        User user = userService.getUserByUsername(username);
        ModerationStatus status = moderationStatusRepository.findByName(EModerationStatus.DRAFT);

        Report reportEntity = new Report();
        reportEntity.setBody(report.getBody());
        reportEntity.setStatus(status);
        reportEntity.setUser(user);
        reportEntity.setTask(task);
        reportEntity.setExpiresAt(offsetDateTime);
        Report savedReport = reportRepository.saveAndFlush(reportEntity);

        return convertToReportDto(savedReport);
    }

    @Override
    public ReportCreateUpdateDto updateReport(long id, ReportCreateUpdateDto report, String username) {


        List<EModerationStatus> statuses = List.of(EModerationStatus.DRAFT, EModerationStatus.PENDING, EModerationStatus.REWORKING);
        
        Report reportEntity = reportRepository.findByTaskIdAndUsernameAndStatusIn(id, username, statuses);
        if (reportEntity == null){
            throw new EntityNotFoundException("No report with such id: " + id);
        }

        if(!reportEntity.getStatus().getName().equals(EModerationStatus.PENDING.name())){
            ModerationStatus nextStatus  = moderationStatusRepository.findByName(EModerationStatus.PENDING);
            reportEntity.setStatus(nextStatus);
        }

        reportEntity.setBody(report.getBody());

        reportRepository.saveAndFlush(reportEntity);

        return report;
    }


    @Override
    public void deleteReport(long id, String username) {

        Report report = reportRepository.getOne(id);
        if (report == null){
            throw new EntityNotFoundException("No report with such id: " + id);
        }

        if(!report.getUser().getUsername().equals(username)){
            throw new BadRequestException("Report №{} does not belong to you." + id);
        }
        reportRepository.deleteById(id);
    }



    private Report getAndCheckOrThrow(long reportId, String username) {
        Report report = getReport(reportId);
        boolean isPending = report.getStatus().getName().equals(EModerationStatus.PENDING);
        boolean isTaskAuthor = report.getTask().getUser().getUsername().equals(username);
        if (!isPending || !isTaskAuthor){
            throw new BadRequestException("You can not do it");
        }

        return report;

    }

    public ReportDto convertToReportDto(Report report) {
        ReportDto reportDto = new ReportDto();
        reportDto.setId(report.getId());
        reportDto.setBody(report.getBody());
        reportDto.setStatus(report.getStatus().getName());
        reportDto.setTaskId(report.getTask().getId());
        reportDto.setExpiresAt(report.getExpiresAt());
        return reportDto;
    }

}
