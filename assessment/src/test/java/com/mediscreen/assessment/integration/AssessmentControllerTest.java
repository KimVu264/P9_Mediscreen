package com.mediscreen.assessment.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AssessmentControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void getReportTest_shouldReturnReport_ForGivenPatient() throws Exception {
        //Act
        mvc.perform(get("/assessment/report/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("patientId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getReportTest_shouldStatus400() throws Exception {
        //Act
        mvc.perform(get("/assessment/report/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("patientId", "id"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void getCurlReportById_shouldReturnReport_ForGivenPatientId() throws Exception {
        //Act
        mvc.perform(post("/assess/id")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("patId", "4"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getCurlReportByFamilyName_shouldReturnReport_ForGivenPatientFamilyName() throws Exception {

        //Act
        mvc.perform(post("/assess/familyName")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("familyName", "Bob"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
