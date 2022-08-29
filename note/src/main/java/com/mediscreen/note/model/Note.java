package com.mediscreen.note.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.time.LocalDate;

@Document(collection = "Note")
public class Note {

    @Id
    private String id;

    @Field(value = "patient_id")
    private Long patientId;

    @NotBlank(message = "Notes cannot be blank!")
    private String note;

    @Field(value = "create_date")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    //private Date createdDate;
    private LocalDate date = LocalDate.now();

    public Note() {
    }

    public Note(String id, Long patientId, String note, LocalDate date) {
        this.id = id;
        this.patientId = patientId;
        this.note = note;
        this.date = date;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getCreatedDate() {
        return date;
    }

    public void setCreatedDate(LocalDate date) {
        this.date = date;
    }
}

