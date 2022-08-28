package com.mediscreen.front.dto;

import com.mediscreen.front.enums.RiskLevel;

public class ReportDto {
    private PatientDto patient;
    private int age;
    private RiskLevel riskLevel;

    public ReportDto() {
    }

    public ReportDto(PatientDto patient, int age, RiskLevel riskLevel) {
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
