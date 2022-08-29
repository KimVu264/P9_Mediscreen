package com.mediscreen.note.controller;

import com.mediscreen.note.dto.NoteDto;
import com.mediscreen.note.exception.DataNotFoundException;
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
    public ResponseEntity<NoteDto> saveNote(NoteDto newNote) throws DataNotFoundException {
        logger.info("saving note request");
        return new ResponseEntity<>(noteConversion.toNoteDto(noteService.saveNote(noteConversion.dtoToNote(newNote))), HttpStatus.CREATED);
    }
}
