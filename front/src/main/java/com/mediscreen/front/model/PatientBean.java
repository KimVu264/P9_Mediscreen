package com.mediscreen.front.model;

import com.mediscreen.front.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientBean {

    private Long id;

    @NotEmpty(message = "First name is not empty")
    private String firstName;

    @NotEmpty(message = "Last name is not empty")
    private String lastName;

    @NotNull(message = "Please choose gender")
    private Gender gender;

    @NotEmpty(message = "Please choose birthday")
    private String birthdate;

    private String address;

    private String tel;

    public PatientBean() {
    }

    public PatientBean(Long id, String firstName, String lastName, Gender gender, String birthdate, String address, String tel) {
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
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
