package com.mediscreen.note.dto;

import javax.validation.constraints.NotBlank;

public class NoteDto {

    @NotBlank(message = "required")
    private String patId;
    @NotBlank(message = "required")
    private String e;

    public NoteDto() {

    }

    public NoteDto(String patId, String e) {

        this.patId = patId;
        this.e     = e;
    }

    public String getPatId() {

        return patId;
    }

    public void setPatId(String patId) {

        this.patId = patId;
    }

    public String getE() {

        return e;
    }

    public void setE(String e) {

        this.e = e;
    }
}
