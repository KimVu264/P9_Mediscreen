package com.mediscreen.assessment.utils;

import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.dto.ReportDto;
import com.mediscreen.assessment.enums.RiskLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ReportConversion {

    private static final Logger logger = LogManager.getLogger(ReportConversion.class);

    public ReportDto toReportDto(PatientDto patient, RiskLevel riskLevel, int age) {

        logger.debug("mapping of report for patient {} {}", patient.getFirstName(), patient.getLastName());
        return new ReportDto(patient.getId(),patient.getFirstName() + " " + patient.getLastName(),
                age, patient.getGender(), patient.getAddress(), patient.getTel(), riskLevel);
    }
}
