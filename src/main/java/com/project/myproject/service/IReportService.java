package com.project.myproject.service;

import com.project.myproject.dto.ReportCreateUpdateDto;
import com.project.myproject.dto.ReportDto;
import com.project.myproject.entity.Report;

import java.util.List;

public interface IReportService {

    List<ReportDto> getReports(long taskId, String username);
    Report getReport(long id);
    //List<ReportDto> getUserReports(String username);
    void reworkReport(long id, String username);
    void rejectReport(long id, String username);
    void approvalReport(long id, String username);
    ReportDto createReport(ReportCreateUpdateDto report, String username);
    ReportCreateUpdateDto updateReport(long id, ReportCreateUpdateDto report, String username);
    void deleteReport(long id, String username);

}
