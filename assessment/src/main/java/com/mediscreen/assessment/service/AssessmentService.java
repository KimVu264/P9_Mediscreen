package com.mediscreen.assessment.service;

import com.mediscreen.assessment.dto.NoteDto;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.dto.ReportDto;
import com.mediscreen.assessment.enums.Gender;
import com.mediscreen.assessment.enums.RiskLevel;
import com.mediscreen.assessment.model.Report;
import com.mediscreen.assessment.proxy.NoteProxy;
import com.mediscreen.assessment.proxy.PatientProxy;
import com.mediscreen.assessment.utils.Calculator;
import com.mediscreen.assessment.utils.ReportConversion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssessmentService {

    private static Logger logger = LogManager.getLogger(AssessmentService.class);

    private final NoteProxy noteProxy;
    private final PatientProxy patientProxy;
    private final Calculator calculator;
    private final ReportConversion reportConversion;

    @Autowired
    public AssessmentService(NoteProxy noteProxy, PatientProxy patientProxy, Calculator calculator, ReportConversion reportConversion) {
        this.noteProxy = noteProxy;
        this.patientProxy = patientProxy;
        this.calculator = calculator;
        this.reportConversion = reportConversion;
    }

    public RiskLevel riskAssess(PatientDto patient) {

        logger.info("assessment of the level of risk for patient {} {}", patient.getFirstName(), patient.getLastName());
        boolean isOlderThanThirty        = calculator.isOlderThanThirty(patient.getBirthdate());
        Gender gender = patient.getGender();
        int terminologyTriggersCount = calculateTerminologyTriggers(Math.toIntExact(patient.getId()));

        if (terminologyTriggersCount >= 8) {
            return RiskLevel.EARLY_ONSET;

        } else if (isOlderThanThirty) {
            if (terminologyTriggersCount >= 6) {
                return RiskLevel.IN_DANGER;
            } else {
                return (terminologyTriggersCount >= 2) ? RiskLevel.BORDERLINE : RiskLevel.NONE;
            }
        } else {
            if (gender.equals(Gender.Feminine)) {
                if (terminologyTriggersCount >= 7) {
                    return RiskLevel.EARLY_ONSET;
                } else {return (terminologyTriggersCount >= 4) ? RiskLevel.IN_DANGER : RiskLevel.NONE;}
            } else {
                if (terminologyTriggersCount >= 5) {
                    return RiskLevel.EARLY_ONSET;
                } else {
                    return (terminologyTriggersCount >= 3) ? RiskLevel.IN_DANGER : RiskLevel.NONE;
                }
            }
        }
    }

    private int calculateTerminologyTriggers(int patientId) {
        logger.info("calculate Terminology Triggers for patient id {}", patientId);
        List<String> notes = noteProxy.getAllNotesByPatientId(patientId)
                .stream()
                .map(NoteDto::getNote)
                .collect(Collectors.toList());
        return calculator.calculateTriggersNumber(notes);
    }

    public Report generateReportById(Long patientId) {
        logger.info("generate report for patient id: {}", patientId);
        PatientDto patient   = patientProxy.getPatientById(patientId);
        RiskLevel  riskLevel = riskAssess(patient);
        int        age       = calculator.computeAge(patient.getBirthdate());
        Report report = new Report();
        report.setPatient(patient);
        report.setAge(age);
        report.setRiskLevel(riskLevel);
        return report;
    }

    public List<ReportDto> generateReportByFamilyName(String familyName) {
        logger.info("generation of report for patients with familyName: {}", familyName);
        return patientProxy.getPatientByFamilyName(familyName).stream().map(p -> {
            RiskLevel riskLevel = riskAssess(p);
            int       age       = calculator.computeAge(p.getBirthdate());
            return reportConversion.toReportDto(p, riskLevel, age);
        }).collect(Collectors.toList());

    }

}
