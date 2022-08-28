package com.mediscreen.patient.unit;

import com.mediscreen.patient.controller.PatientCurlController;
import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Gender;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.utils.PatientConversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientCurlController.class)
public class TestPatientCurlController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private PatientConversion patientConversion;

    private Patient patient    = new Patient(1L, "Toto", "Test", Gender.Masculin, Date.valueOf("2000-12-09"), "rue du port", "12345678");

    @BeforeEach
    void setUp() {
        when(patientConversion.dtoToPatient(any())).thenReturn(patient);
    }

    @Test
    void addPatientTest_shouldReturnPatientAdded() throws Exception {
        //Arrange
        when(patientService.savePatient(patient)).thenReturn(patient);
        //Act
        mockMvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "1")
                        .param("family", "doe")
                        .param("given", "john")
                        .param("dob", Date.valueOf("2000-12-09").toString())
                        .param("sex", "M")
                        .param("address", "33 rue des nations")
                        .param("phone", "0890009"))
                .andDo(print())
                .andExpect(status().isCreated());

    }

}
