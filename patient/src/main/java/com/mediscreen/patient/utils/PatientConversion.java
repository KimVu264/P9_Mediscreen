package com.mediscreen.patient.utils;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Gender;
import com.mediscreen.patient.model.Patient;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class PatientConversion {

    public Patient dtoToPatient(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientDto.getGiven());
        patient.setLastName(patientDto.getFamily());
        patient.setBirthdate(Date.valueOf(patientDto.getDob()));
        patient.setGender(patientDto.getSex().equalsIgnoreCase("F") ? Gender.Feminine : Gender.Masculin);
        patient.setTel(patientDto.getPhone());
        patient.setAddress(patient.getAddress());
        return patient;
    }
}