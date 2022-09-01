package com.mediscreen.front.proxy;

import com.mediscreen.front.dto.ReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="assessment" , url="${mediscreen.assessmentUrl}")
public interface AssessmentProxy {

    @RequestMapping("/assessment/report/id")
    ReportDto getReportByPatientId(@RequestParam Long patientId);
}
