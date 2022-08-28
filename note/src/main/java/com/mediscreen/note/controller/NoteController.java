package com.mediscreen.note.controller;

import com.mediscreen.note.exception.DataNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    private static Logger logger = LogManager.getLogger(NoteController.class);
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note/add")
    public ResponseEntity<Note> addNewNote(@RequestParam String note, @RequestParam Long patientId) {
        logger.info("Patient notes add: {}", HttpStatus.CREATED);
        return new ResponseEntity<>(noteService.addNewNote(note,patientId), HttpStatus.CREATED);
    }

    @GetMapping("/note/id")
    public ResponseEntity<Note> getPatientNoteWithId(@RequestParam String id){
        Note patientNotesList = noteService.findById(id);
        logger.info("The patient note is found: {}", HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(patientNotesList);
    }


    @PutMapping("/note/update")
    public ResponseEntity<Note> updateNote(@RequestBody Note note) throws DataNotFoundException {
        logger.info("update note request");
        return new ResponseEntity<>(noteService.updateNote(note), HttpStatus.OK);
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllNotes(@RequestParam Long patientId) {
        logger.info("get All notes request");
        return new ResponseEntity<>(noteService.findAllByPatientId(patientId), HttpStatus.OK);
    }

    @DeleteMapping("/note/delete")
    public ResponseEntity<Note> deleteNote(@RequestParam String id) throws DataNotFoundException {
        logger.info("delete note request");
        return new ResponseEntity<>(noteService.deleteNote(id), HttpStatus.OK);
    }
}
