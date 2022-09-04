package com.mediscreen.note.unit;

import com.mediscreen.note.dto.NoteDto;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.utils.NoteConversion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestNoteConversion {

    @Autowired
    NoteConversion noteMapper;

    NoteDto noteDto = new NoteDto("1", "a few notes for a test");
    Note note    = new Note("62d12c25191bcc3f11d08547", 1L, "a few notes for a test", LocalDateTime.now());

    @Test
    public void toNoteTest(){
        //Act
        Note result =noteMapper.dtoToNote(noteDto);
        //Assert
        assertThat(result.getPatientId()).isEqualTo(1);
        assertThat(result.getNote()).isEqualTo("a few notes for a test");
    }

    @Test
    public void toNoteDtoTest(){
        //Act
        NoteDto result =noteMapper.toNoteDto(note);
        //Assert
        assertThat(result.getPatId()).isEqualTo("1");
        assertThat(result.getE()).isEqualTo("a few notes for a test");
        //assertThat(result).isEqualTo(noteDto);

    }
}
