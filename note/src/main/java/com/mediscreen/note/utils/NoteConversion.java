package com.mediscreen.note.utils;

import com.mediscreen.note.dto.NoteDto;
import com.mediscreen.note.model.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteConversion {

    public Note dtoToNote(NoteDto noteDto) {
        Note note = new Note();
        note.setPatientId(Long.valueOf(noteDto.getPatId()));
        note.setNote(noteDto.getE());
        return note;
    }
}
