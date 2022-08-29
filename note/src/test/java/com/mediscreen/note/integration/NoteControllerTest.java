package com.mediscreen.note.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.repository.NoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class NoteControllerTest {

    Note note = new Note("376dkfdkf7678", 4L, "test note", LocalDate.now() );

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        noteRepository.insert(note);
    }

    @AfterEach
    void tearDown() {

        noteRepository.delete(note);
    }

    @Test
    void addNoteTest_shouldReturnNoteAdded() throws Exception {
        //Act
        mockMvc.perform(post("/note/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(note))
                        .param("note", "note test")
                        .param("patientId", "4"))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void updateNoteTest_shouldReturnNoteUpdated() throws Exception {
        //Act
        mockMvc.perform(put("/note/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(note)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void deleteNoteTest_shouldReturnNoteDeleted() throws Exception {

        //Act
        mockMvc.perform(delete("/note/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).param("id", "376dkfdkf7678"))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    void deleteNoteTest_shouldReturnStatus404() throws Exception {

        noteRepository.delete(note);
        //Act
        mockMvc.perform(delete("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).param("id", "376dkfdkf7678"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllNoteTest_shouldReturnListOfNote() throws Exception {
        //Act
        mockMvc.perform(get("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).param("patientId", "4"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
