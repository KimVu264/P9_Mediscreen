package com.mediscreen.assessment.model;

import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.enums.RiskLevel;

public class Report {

    private PatientDto patient;
    private int age;
    private RiskLevel riskLevel;

    public Report() {
    }

    public Report(PatientDto patient, int age, RiskLevel riskLevel) {
        this.patient = patient;
        this.age = age;
        this.riskLevel = riskLevel;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }
}
