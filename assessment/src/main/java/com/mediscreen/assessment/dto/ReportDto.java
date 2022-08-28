package com.mediscreen.assessment.dto;

import com.mediscreen.assessment.enums.Gender;
import com.mediscreen.assessment.enums.RiskLevel;

public class ReportDto {

    private Long id;
    private String name;
    private int age;
    private Gender gender;
    private String address;
    private String tel;
    private RiskLevel riskLevel;

    public ReportDto() {
    }

    public ReportDto(Long id, String name, int age, Gender gender, String address, String tel, RiskLevel riskLevel) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.tel = tel;
        this.riskLevel=riskLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public RiskLevel getRiskLevel() {

        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {

        this.riskLevel = riskLevel;
    }
}
