package com.mediscreen.note.service;

import com.mediscreen.note.dto.PatientDto;
import com.mediscreen.note.exception.DataNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.proxy.PatientProxy;
import com.mediscreen.note.repository.NoteRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class NoteService {

    private static Logger logger = LogManager.getLogger(NoteService.class);

    private final NoteRepository noteRepository;

    private final PatientProxy patientProxy;

    public NoteService(NoteRepository noteRepository, PatientProxy patientProxy) {
        this.noteRepository = noteRepository;
        this.patientProxy = patientProxy;
    }

    public PatientDto patientById(Long id) throws DataNotFoundException {
        logger.info("get patient by id {}",id);
        return patientProxy.getPatientById(id)
                .orElseThrow(()-> new DataNotFoundException("patient with id: "+id+ " not found"));
    }

    public Note findById(String id) {
        logger.info("get note by id {}",id);
        return noteRepository.findById(id);

    }

    public List<Note> findAllByPatientId(Long id) {
        logger.info("get all notes by patient id {}",id);
        return noteRepository.findAllByPatientId(id);

    }

    public Note saveNote(Note note) throws DataNotFoundException {
        logger.info("save the note");
        PatientDto patient = patientById(note.getPatientId());
        return noteRepository.insert(note);
    }

    public Note addNewNote(@Valid String note, Long patientId) {
        logger.info("Add patient note send to repository with note: {}", note);
        Note newNote = new Note();
        newNote.setNote(note);
        newNote.setPatientId(patientId);
        newNote.setCreatedDate(LocalDate.now());
        return noteRepository.insert(newNote);
    }

    public Note updateNote(@Valid Note note) throws DataNotFoundException {
        logger.info("update the note");
        PatientDto patient   = patientById(note.getPatientId());
        Note updateNote = findById(note.getId());
        return noteRepository.save(note);

    }

    public Note deleteNote(String id) {
        logger.info("delete the note");
        Note note = findById(id);
        noteRepository.delete(note);
        return note;
    }
}
