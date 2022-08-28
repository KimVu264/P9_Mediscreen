package com.mediscreen.patient.controller;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.utils.PatientConversion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PatientCurlController {

    private static Logger logger = LogManager.getLogger(PatientCurlController.class);

    private final PatientService patientService;
    private final PatientConversion patientConversion;

    public PatientCurlController(PatientService patientService, PatientConversion patientConversion) {
        this.patientService = patientService;
        this.patientConversion = patientConversion;
    }

    @PostMapping(value = "/patient/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> addPatient(@Valid PatientDto patientDto, Model model) {
        logger.info("Add patient named: {} {}", patientDto.getFamily(), patientDto.getGiven());
        patientService.addPatient(model, patientConversion.dtoToPatient(patientDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(patientDto);
    }
}
