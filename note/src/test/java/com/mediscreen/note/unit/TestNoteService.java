package com.mediscreen.note.unit;

import com.mediscreen.note.dto.PatientDto;
import com.mediscreen.note.exception.DataNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.proxy.PatientProxy;
import com.mediscreen.note.repository.NoteRepository;
import com.mediscreen.note.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TestNoteService {

    private PatientDto patient = new PatientDto(1L, "john", "doe", Date.valueOf("1990-11-20"), "Masculin", "0890009", "rue des nations");
    Note note = new Note("62d12c25191bcc3f11d08547", 1L,"a few notes for a test", LocalDate.now());
    static List<Note> patientNotesList = new ArrayList<>();
    @Mock
    private NoteRepository noteRepository;

    @Mock
    private PatientProxy patientProxy;

    @InjectMocks
    private NoteService noteService;

    @Test
    public void saveNoteTest_shouldReturnNoteAdded() throws DataNotFoundException {
        //Arrange
        when(noteRepository.insert(note)).thenReturn(note);
        when(patientProxy.getPatientById(1L)).thenReturn(Optional.of(patient));
        //Act
        Note result = noteService.saveNote(note);
        //Assert
        assertThat(result).isEqualTo(note);

    }

    @Test
    public void saveNoteTest_shouldThrowException()  {
        //Arrange
        when(noteRepository.insert(note)).thenReturn(note);
        when(patientProxy.getPatientById(1L)).thenReturn(Optional.empty());
        //Assert
        assertThrows(DataNotFoundException.class, () -> noteService.saveNote(note));

    }

    @Test
    void addNewNoteTest()
    {
        Mockito.when(noteRepository.insert(any(Note.class))).thenReturn(note);

        Note patientNoteTest = noteService.addNewNote(note.getNote(),note.getPatientId());
        assertEquals(note , patientNoteTest);
    }

    @Test
    public void updateNoteTest_shouldReturnNoteUpdate() throws DataNotFoundException {
        //Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(noteRepository.save(note)).thenReturn(note);
        when(patientProxy.getPatientById(1L)).thenReturn(Optional.of(patient));
        //Act
        Note result = noteService.updateNote(note);
        //Assert
        assertThat(result).isEqualTo(note);

    }

    @Test
    public void updateNoteTest_shouldThrowRessourceNotFoundException() throws DataNotFoundException {
        //Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act//Assert
        assertThrows(DataNotFoundException.class, () -> noteService.updateNote(note));

    }
    @Test
    public void updateNoteTest_ForUnRegistrePatient_shouldThrowRessourceNotFoundException() throws DataNotFoundException {
        //Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(patientProxy.getPatientById(1L)).thenReturn(Optional.empty());
        //Act//Assert
        assertThrows(DataNotFoundException.class, () -> noteService.updateNote(note));

    }

    @Test
    public void deleteNoteTest_shouldReturnNoteDeleted() {
        //Arrange
        patientNotesList.add(note);
        //Act
        noteService.deleteNote(note.getId());
        //Assert
        assertTrue(patientNotesList.remove(note));

    }

    @Test
    public void findAllByPatientIdTest_shouldReturn_NotesForGivenPatientId() throws DataNotFoundException {
        //Arrange
        when(noteRepository.findAllByPatientId(2L)).thenReturn(List.of(note));
        //Act
        List<Note> result = noteService.findAllByPatientId(2L);
        //Assert
        assertThat(result).contains(note);
        assertThat(result.size()).isEqualTo(1);
    }
}
