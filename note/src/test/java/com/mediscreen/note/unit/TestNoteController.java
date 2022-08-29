package com.mediscreen.note.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.note.controller.NoteController;
import com.mediscreen.note.exception.DataNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
public class TestNoteController {

    Note note = new Note("62d12c25191bcc3f11d08547", 1L, "note test", LocalDate.now());

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    @Test
    void addNoteTest_shouldReturnNoteAdded() throws Exception {
        //Arrange
        when(noteService.addNewNote(any(),any())).thenReturn(note);
        //Act
        mvc.perform(post("/note/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("note", objectMapper.writeValueAsString(note.getNote()))
                        .param("patientId",  objectMapper.writeValueAsString(note.getPatientId())))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void updateNoteTest_shouldReturnNoteUpdated() throws Exception {
        //Arrange
        when(noteService.updateNote(note)).thenReturn(note);
        //Act
        mvc.perform(put("/note/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteNoteTest_shouldReturnNoteDeleted() throws Exception {
        //Arrange
        when(noteService.deleteNote(note.getId())).thenReturn(note);
        //Act
        mvc.perform(delete("/note/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).param("id", "62d12c25191bcc3f11d08547"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getAllNoteTest_shouldReturnListOfNote() throws Exception {
        //Arrange
        when(noteService.findAllByPatientId(note.getPatientId())).thenReturn(List.of(note));
        //Act
        mvc.perform(get("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("patientId", objectMapper.writeValueAsString(note.getPatientId())))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getPatientNoteWithIdTest() throws Exception
    {
        when(noteService.findById(any())).thenReturn(note);
        mvc.perform(get("/note/id")
                        .param("id", objectMapper.writeValueAsString(note.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(note)));
    }
}
