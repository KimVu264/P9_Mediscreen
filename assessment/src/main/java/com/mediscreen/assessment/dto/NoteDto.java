package com.mediscreen.assessment.dto;

import java.util.Date;

public class NoteDto {

    private String id;
    private Long patientId;
    private Date createdDate;
    private String note;

    public NoteDto() {
    }

    public NoteDto(String id, Long patientId, Date createdDate, String note) {
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
