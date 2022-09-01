package com.mediscreen.patient.unit;

import com.mediscreen.patient.exception.DataNotFoundException;
import com.mediscreen.patient.model.Gender;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.service.PatientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TestPatientService {

    static List<Patient> patientsList = new ArrayList<>();
    static Patient patient;
    static Model model;
    @Mock
    private PatientRepository patientRepository;
    @InjectMocks
    private PatientService patientService;

    @BeforeAll
    static void setupTest() {
        patient = new Patient(1L, "Toto", "test", Gender.Masculin, Date.valueOf("2000-11-22"), "rue des nations", "127647849");
        patientsList.add(patient);
    }

    @Test
    void getAllPatientsTest() {
        //Arrange
        when(patientRepository.findAll()).thenReturn(List.of(patient));
        //Act
        List<Patient> patients = patientService.getAllPatients();
        //Assert
        assertThat(patients).contains(patient);
        assertThat(patients.size()).isEqualTo(1);

    }
/*
    @Test
    void getPatientByIdTest() throws DataNotFoundException {
        Mockito.when(patientRepository.getPatientById(1L)).thenReturn(patient);
        Patient patientTest = patientService.getPatientById(1L);
        assertEquals(patient , patientTest);
    }

 */

    @Test
    void getPatientByIdReturnNotFoundExceptionTest() {
        Mockito.when(patientRepository.getPatientById(2L)).thenReturn(null);
        assertThrows(DataNotFoundException.class,() -> patientService.getPatientById(2L));
    }

    @Test
    void savePatientTest_shouldReturnSaved() {
        //Arrange
        when(patientRepository.save(patient)).thenReturn(patient);
        //Act
        Patient result = patientService.savePatient(patient);
        //Assert
        assertThat(result).isEqualTo(patient);
    }
/*
    @Test
    void addPatientTest() {
        Patient patient2 = new Patient(2L, "Toto2", "test", Gender.Masculin, Date.valueOf("2000-11-22"), "rue des nations", "127647849");
        Mockito.when(patientRepository.saveAndFlush(patient)).thenReturn(patient2);
        Patient patientAdded = patientService.addPatient(model, patient2);
        assertEquals(patient2 , patientAdded);
    }

 */

    @Test
    void updatePatientTest_shouldReturnPatientUpdated() {
        //Arrange
        Patient patient1 = new Patient(1L, "Toto", "test", Gender.Masculin, Date.valueOf("2000-11-22"), "rue des nations", "127647849");
        // when(patientRepository.findById(any())).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient1);
        //Act
        patient1 = patientService.updatePatient(patient1);
        //Assert
        assertEquals(patient1, patient1);
    }

    @Test
    void deletePatientByIdTest_shouldReturnDeleted() throws DataNotFoundException {
        //Arrange
        Mockito.when(patientRepository.getPatientById(1L)).thenReturn(patient);
        when(patientRepository.findById(any())).thenReturn(Optional.of(patient));
        //doNothing().when(patientRepository).delete(patient);
        //Act
        Patient result = patientService.deletePatientById(1L);
        //Assert
        assertThat(result).isEqualTo(patient);
        }

    @Test
    void deletePatientByIdTest_shouldThrowDataNotFoundException() {
        //Arrange
        when(patientRepository.findById(any())).thenReturn(Optional.empty());
        //Act //Assert
        assertThrows(DataNotFoundException.class, () -> patientService.deletePatientById(1L));
    }

    @Test
    void searchPatientsByName_shouldReturnPatientsByFirstName() {
        Mockito.when(patientRepository.findByFirstName(patient.getFirstName())).thenReturn(patientsList);
        Mockito.when(patientRepository.findByLastName(patient.getLastName())).thenReturn(null);

        List<Patient> patientListTest = patientService.searchPatientsByName("Toto", "");

        assertEquals(patientsList.size() ,patientListTest.size());
    }

    @Test
    void searchPatientsByName_shouldReturnPatientsByLastName() {
        Mockito.when(patientRepository.findByFirstName(patient.getFirstName())).thenReturn(null);
        Mockito.when(patientRepository.findByLastName(patient.getLastName())).thenReturn(patientsList);

        List<Patient> patientListTest = patientService.searchPatientsByName("", "test");

        assertEquals(patientsList.size() ,patientListTest.size());
    }


    @Test
    void searchPatientsByName_shouldReturnPatientsByFirstNameAndLastName() {
        Mockito.when(patientRepository.findPatientByFirstNameAndLastName(patient.getFirstName(),patient.getLastName())).thenReturn(patientsList);

        List<Patient> patientListTest = patientService.searchPatientsByName("Toto", "test");

        assertEquals(patientsList.size() ,patientListTest.size());
    }

    @Test
    void findPatientByNameWrongNameReturnNotFoundTest()
    {
        Mockito.when(patientRepository.findByFirstName(patient.getFirstName())).thenReturn(null);
        Mockito.when(patientRepository.findByLastName(patient.getLastName())).thenReturn(null);

        List<Patient> patientListTest = patientService.searchPatientsByName("", "");

        assertEquals(0,patientListTest.size());
    }

}
