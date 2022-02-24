package com.project.myproject.controller;

import com.project.myproject.dto.ReportCreateUpdateDto;
import com.project.myproject.dto.ReportDto;
import com.project.myproject.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/reports")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ReportDto createReport(@Valid @RequestBody ReportCreateUpdateDto report, Principal principal) {
        return reportService.createReport(report, principal.getName());
    }

    @PutMapping(value = "/{id}")
    public ReportCreateUpdateDto updateReport(@PathVariable long id, @Valid @RequestBody ReportCreateUpdateDto report, Principal principal) {
        return reportService.updateReport(id, report, principal.getName());
    }

    @PutMapping(value = "/{id}/rework")
    public void reworkReport(@PathVariable long id, Principal principal) {
        reportService.reworkReport(id, principal.getName());
    }

    @PutMapping(value = "/{id}/reject")
    public void rejectReport(@PathVariable long id, Principal principal) {
        reportService.rejectReport(id, principal.getName());

    }
    @PutMapping(value = "/{id}/approval")
    public void approvalReport(@PathVariable long id, Principal principal) {
        reportService.approvalReport(id, principal.getName());

    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReport(@PathVariable long id, Principal principal){
        reportService.deleteReport(id, principal.getName());
    }
}

