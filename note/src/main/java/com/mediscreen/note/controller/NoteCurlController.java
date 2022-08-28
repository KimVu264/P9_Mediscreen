package com.mediscreen.note.controller;

import com.mediscreen.note.dto.NoteDto;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import com.mediscreen.note.utils.NoteConversion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class NoteCurlController {

    private static Logger logger = LogManager.getLogger(NoteCurlController.class);

    private final NoteService noteService;
    private final NoteConversion noteConversion;

    @Autowired
    public NoteCurlController(NoteService noteService, NoteConversion noteConversion) {
        this.noteService = noteService;
        this.noteConversion = noteConversion;
    }

    @PostMapping(value = "/patHistory/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDto> addNewNote(@Valid NoteDto noteDto) {
        logger.info("Add Note for patientId: {}, and content: {}", noteDto.getPatId(), noteDto.getE());
        Note patientNote = noteConversion.dtoToNote(noteDto);
        noteService.addNewNote(patientNote.getNote(),patientNote.getPatientId());
        return ResponseEntity.status(HttpStatus.CREATED).body(noteDto);
    }
}
