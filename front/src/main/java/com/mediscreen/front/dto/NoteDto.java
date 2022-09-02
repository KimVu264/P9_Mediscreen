package com.mediscreen.front.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class NoteDto {

    private String id;
    private Long patientId;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    //private Date createdDate;
    private LocalDate createdDate = LocalDate.now();

    @NotEmpty(message = "Note cannot be empty")
    private String note;

    public NoteDto() {
    }

    public NoteDto(String id, Long patientId, LocalDate createdDate, String note) {
        this.id = id;
        this.patientId = patientId;
        this.createdDate = createdDate;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
