package com.mediscreen.front.model;

import com.mediscreen.front.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class PatientBean {

    private Long id;

    private String firstName;

    private String lastName;

    private Gender gender;

    private Date birthdate;

    private String address;

    private String tel;

    public PatientBean() {
    }

    public PatientBean(Long id, String firstName, String lastName, Gender gender, Date birthdate, String address, String tel) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.tel = tel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
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

}
