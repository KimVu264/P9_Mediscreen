package com.mediscreen.note.utils;

import com.mediscreen.note.controller.NoteController;
import com.mediscreen.note.dto.NoteDto;
import com.mediscreen.note.model.Note;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class NoteConversion {

    private static Logger logger = LogManager.getLogger(NoteConversion.class);

    public Note dtoToNote(NoteDto noteDto) {
        Note note = new Note();
        note.setPatientId(Long.valueOf(noteDto.getPatId()));
        note.setNote(noteDto.getE());
        return note;
    }

    public NoteDto toNoteDto(Note note) {
        logger.debug("mapping note of patient id:{} to noteDTO",note.getPatientId());
        return new NoteDto(String.valueOf(note.getPatientId()), note.getNote());
    }
}
