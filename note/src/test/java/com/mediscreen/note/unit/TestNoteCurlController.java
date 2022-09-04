package com.mediscreen.note.unit;

import com.mediscreen.note.controller.NoteCurlController;
import com.mediscreen.note.dto.NoteDto;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import com.mediscreen.note.utils.NoteConversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteCurlController.class)
public class TestNoteCurlController {

    NoteDto noteDto = new NoteDto("1", "a few notes for a test");
    Note note   = new Note("62d12c25191bcc3f11d08547", 1L, "a few notes for a test", LocalDateTime.now() );
    @Autowired
    private MockMvc mvc;
    @MockBean
    private NoteService noteService;
    @MockBean
    private NoteConversion noteMapper;

    @BeforeEach
    void setUp() {

        when(noteMapper.dtoToNote(noteDto)).thenReturn(note);
    }

    @Test
    void addNoteTest_shouldReturnNoteAdded() throws Exception {
        //Arrange
        when(noteService.saveNote(note)).thenReturn(note);
        //Act
        mvc.perform(post("/patHistory/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("patId", "1").param("e", "a few notes for a test"))
                .andDo(print())
                .andExpect(status().isCreated());

    }

}
