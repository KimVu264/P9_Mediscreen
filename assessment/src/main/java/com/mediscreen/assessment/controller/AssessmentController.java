package com.mediscreen.assessment.controller;

import com.mediscreen.assessment.model.Report;
import com.mediscreen.assessment.service.AssessmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AssessmentController {

    private static Logger logger = LogManager.getLogger(AssessmentController.class);

    private final AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/assessment/report/id")
    public ResponseEntity<Report> getReport(@RequestParam Long patientId) {
        logger.info(" get report request for patient id {} ", patientId);
        return new ResponseEntity<>(assessmentService.generateReportById(patientId), HttpStatus.OK);
    }

    @PostMapping(value = "assess/id", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCurlReportById(Long patId) {

        Report report = assessmentService.generateReportById(patId);
        if (report != null) {
            logger.info("Report successfully found - code : {}", HttpStatus.OK);
            String reportResponse = "Patient: " + report.getPatient().getFirstName() + " "
                    + report.getPatient().getLastName() + " (age " + report.getAge() + ") diabetes assessment is: " + report.getRiskLevel();
            return ResponseEntity.status(HttpStatus.OK).body(reportResponse);
        } else {
            logger.error("Report can't be send, Patient don't exist - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "assess/familyName", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getCurlReportByFamilyName(String familyName) {
        logger.info(" get report request for patients with family name {} ", familyName);
        List<String> reports = assessmentService.generateReportByFamilyName(familyName).stream().map(p -> "Patient : " + p.getName() + " (age " + p.getAge() + ") diabetes assessment is: " + p.getRiskLevel()
                .toString()).collect(Collectors.toList());
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

}
