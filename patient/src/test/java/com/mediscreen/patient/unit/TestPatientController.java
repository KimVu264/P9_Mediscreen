package com.mediscreen.patient.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.patient.controller.PatientController;
import com.mediscreen.patient.exception.DataNotFoundException;
import com.mediscreen.patient.model.Gender;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.utils.PatientConversion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientController.class)
public class TestPatientController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @MockBean
    private PatientConversion conversion;

    static Model model;
    static Patient patient;
    static List<Patient> patientList;

    @BeforeAll
    static void setupTest(){
        patientList = new ArrayList<>();
        patient = new Patient(1L, "Toto", "Test", Gender.Masculin, Date.valueOf("2000-12-09"), "rue du port", "12345678");
        patientList.add(patient);
    }

    @Test
    void getAllPatientsTest_shouldReturnListOfPatients() throws Exception {
        //Arrange
        when(patientService.getAllPatients()).thenReturn(List.of(patient));
        //Act
        mockMvc.perform(get("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(patient))));
    }

    @Test
    void addPatientTest_shouldReturnPatientAdded() throws Exception {
        //Arrange
        when(patientService.savePatient(patient)).thenReturn(patient);
        //Act
        mockMvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(patient)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void patientUpdateTest() throws Exception {
        when(patientService.updatePatient(any(),any())).thenReturn(patient);
        mockMvc.perform(put("/patient/update")
                        .param("model", String.valueOf(model))
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(patient)));
    }

    @Test
    void deletePatientTest() throws Exception {
        //Arrange
        when(patientService.updatePatient(any(),any())).thenReturn(patient);
        mockMvc.perform(delete("/patient/delete")
                        .param("model", String.valueOf(model))
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }

    @Test
    void deletePatientByIdTest() throws Exception
    {
        mockMvc.perform(delete("/patient/delete/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void getPatientByIdTest_shouldReturnThrowException() throws Exception {
        //Arrange
        when(patientService.getPatientById(1L)).thenThrow(DataNotFoundException.class);
        //Act
        mockMvc.perform(get("/patient/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void getPatientByFirstNameTest_shouldReturnPatient() throws Exception {
        //Arrange
        when(patientService.getPatientByFirstName(any())).thenReturn(List.of(patient));
        //Act
        mockMvc.perform(get("/patient/firstName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("firstName", "Toto"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getPatientByLastNameTest_shouldReturnPatient() throws Exception {
        //Arrange
        when(patientService.getPatientsByLastName(any())).thenReturn(List.of(patient));
        //Act
        mockMvc.perform(get("/patient/lastName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("lastName", "test"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getPatientByNameTest() throws Exception {
        when(patientService.getByPatientName(any(),any(),any())).thenReturn(patientList);
        mockMvc.perform(get("/searchPatient")
                        .param("model", String.valueOf(model))
                        .param("firstName", "Toto")
                        .param("lastName", "test"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(patientList)));
    }

}
