package com.mediscreen.assessment.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.assessment.controller.AssessmentController;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.dto.ReportDto;
import com.mediscreen.assessment.enums.Gender;
import com.mediscreen.assessment.enums.RiskLevel;
import com.mediscreen.assessment.model.Report;
import com.mediscreen.assessment.service.AssessmentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AssessmentController.class)
public class TestAssessmentController {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AssessmentService assessmentService;

    static Report patientReport;
    static PatientDto patient;
    static ReportDto reportDto;

    @BeforeAll
    static void setupTest(){
        patient = new PatientDto(0L,"Toto","Test", Date.valueOf("2020-12-25"), Gender.Masculin, "Rue du Port", "123459743");
        patientReport = new Report(patient, 1, RiskLevel.NONE);
        reportDto = new ReportDto(1L, "Toto test", 36, Gender.Masculin, "rue des nations", "124485789", RiskLevel.BORDERLINE);
    }


    @Test
    public void getReportTest_shouldStatus400() throws Exception {
        //Arrange
        when(assessmentService.generateReportById(1L)).thenReturn(patientReport);
        //Act
        mvc.perform(get("/assessment/report/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("patientId", "id"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void getReportTest_shouldReturnReport_ForGivenPatient() throws Exception {
        //Arrange
        when(assessmentService.generateReportById(1L)).thenReturn(patientReport);
        //Act
        mvc.perform(get("/assessment/report/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("patientId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((content().string(objectMapper.writeValueAsString(patientReport))));
    }
    @Test
    public void getCurlReportById_shouldReturnReport_ForGivenPatientId() throws Exception {
        //Arrange
        when(assessmentService.generateReportById(1L)).thenReturn(patientReport);
        String report = "Patient: " + patientReport.getPatient().getFirstName() +" "+ patientReport.getPatient().getLastName()+ " (age " + patientReport.getAge() + ") diabetes assessment is: " + patientReport.getRiskLevel()
                .toString();
        //Act
        mvc.perform(post("/assess/id")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("patId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(report));
    }

    @Test
    public void getCurlReportById_shouldReturnReport_ForGivenPatientFamilyName() throws Exception {
        //Arrange
        when(assessmentService.generateReportByFamilyName("test")).thenReturn(List.of(reportDto));
        String report = "Patient : " + reportDto.getName() + " (age " + reportDto.getAge() + ") diabetes assessment is: " + reportDto.getRiskLevel()
                .toString();
        //Act
        mvc.perform(post("/assess/familyName")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("familyName", "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(report))));
    }
}
