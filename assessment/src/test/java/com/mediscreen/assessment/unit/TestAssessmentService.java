package com.mediscreen.assessment.unit;

import com.mediscreen.assessment.dto.NoteDto;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.dto.ReportDto;
import com.mediscreen.assessment.enums.Gender;
import com.mediscreen.assessment.enums.RiskLevel;
import com.mediscreen.assessment.model.Report;
import com.mediscreen.assessment.proxy.NoteProxy;
import com.mediscreen.assessment.proxy.PatientProxy;
import com.mediscreen.assessment.service.AssessmentService;
import com.mediscreen.assessment.utils.Calculator;
import com.mediscreen.assessment.utils.ReportConversion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TestAssessmentService {

    @InjectMocks
    private AssessmentService assessmentService;
    private PatientDto        patient = new PatientDto(1L, "john", "doe", Date.valueOf("1985-02-03"), Gender.Masculin, "rue des nations", "08900090");
    private NoteDto           note    = new NoteDto("62d12c25191bcc3f11d08547", 1L, Date.valueOf("1985-02-03"), "a few notes for a test with Hémoglobine A1C and Taille ");
    private NoteDto           note2   = new NoteDto("62d12c25191bcc3f11d08548", 1L, Date.valueOf("1985-02-03"), "a few notes for a test with Rechute and vertige");
    @Mock
    private NoteProxy         noteProxy;
    @Mock
    private PatientProxy      patientProxy;
    @Mock
    private Calculator        calculator;
    @Mock
    private ReportConversion reportMapper;

    @ParameterizedTest
    @CsvSource({
            "true,8,Feminine", "true,8,Masculin", "false,7,Feminine", "false,8,Feminine", "false,5,Masculin",
            "false,8,Masculin"
    })
    public void riskAssess_shouldReturnRisk_EARLY_ONSET(boolean older, int count, Gender gender) {
        //Arrange
        PatientDto patient = new PatientDto(1L, "john", "doe",  Date.valueOf("1985-02-03"), gender, "0890009", "rue des nations");
        when(noteProxy.getAllNotesByPatientId(any())).thenReturn(List.of(note, note2));

        when(calculator.isOlderThanThirty(any())).thenReturn(older);
        when(calculator.calculateTriggersNumber(any())).thenReturn(count);
        //Act
        RiskLevel level = assessmentService.riskAssess(patient);
        //Assert
        assertThat(level).isEqualTo(RiskLevel.EARLY_ONSET);
    }
    @ParameterizedTest
    @CsvSource({
            "true,6,Feminine", "true,6,Masculin", "false,4,Feminine", "false,5,Feminine", "false,3,Masculin",
            "false,4,Masculin"
    })
    public void riskAssess_shouldReturnRiskIN_DANGER(boolean older, int count, Gender gender) {
        //Arrange
        PatientDto patient = new PatientDto(1L, "john", "doe",  Date.valueOf("1985-02-03"), gender, "0890009", "rue des nations");
        when(noteProxy.getAllNotesByPatientId(any())).thenReturn(List.of(note, note2));

        when(calculator.isOlderThanThirty(any())).thenReturn(older);
        when(calculator.calculateTriggersNumber(any())).thenReturn(count);
        //Act
        RiskLevel level = assessmentService.riskAssess(patient);
        //Assert
        assertThat(level).isEqualTo(RiskLevel.IN_DANGER);
    }

    @ParameterizedTest
    @CsvSource({"true,2,Masculin", "true,5,Masculin", "true,2,Feminine", "true,5,Feminine"})
    public void riskAssess_shouldReturnRisk_BORDERLINE(boolean older, int count, Gender gender) {
        //Arrange
        PatientDto patient = new PatientDto(1L, "john", "doe",Date.valueOf("1985-02-03"), gender, "0890009", "rue des nations");
        when(noteProxy.getAllNotesByPatientId(any())).thenReturn(List.of(note, note2));
        when(calculator.isOlderThanThirty(any())).thenReturn(older);
        when(calculator.calculateTriggersNumber(any())).thenReturn(count);
        //Act
        RiskLevel level = assessmentService.riskAssess(patient);
        //Assert
        assertThat(level).isEqualTo(RiskLevel.BORDERLINE);
    }

    @ParameterizedTest
    @CsvSource({
            "true,1,Feminine", "true,0,Feminine", "true,0,Masculin", "false,3,Feminine", "false,2,Feminine",
            "false,2,Masculin", "false,1,Masculin"
    })
    public void riskAssess_shouldReturnRisk_NONE(boolean older, int count, Gender gender) {
        //Arrange
        PatientDto patient = new PatientDto(1L, "john", "doe", Date.valueOf("1985-02-03"), gender, "08900099", "rue des nations");
        when(noteProxy.getAllNotesByPatientId(any())).thenReturn(List.of(note, note2));

        when(calculator.isOlderThanThirty(any())).thenReturn(older);
        when(calculator.calculateTriggersNumber(any())).thenReturn(count);
        //Act
        RiskLevel level = assessmentService.riskAssess(patient);
        //Assert
        assertThat(level).isEqualTo(RiskLevel.NONE);
    }

    @Test
    public void generateReportById_shouldReturnReport() {
        //Arrange
        ReportDto reportDto = new ReportDto(1L, "john doe", 32, Gender.Masculin, "rue des nations", "08900099", RiskLevel.BORDERLINE);
        when(patientProxy.getPatientById(any())).thenReturn(patient);
        when(calculator.computeAge(any())).thenReturn(32);
        when(calculator.isOlderThanThirty(any())).thenReturn(true);
        when(calculator.calculateTriggersNumber(any())).thenReturn(2);
        when(reportMapper.toReportDto(patient, RiskLevel.BORDERLINE, 32)).thenReturn(reportDto);
        //Act
        Report result = assessmentService.generateReportById(1L);
        //Assert
        assertThat(result).isEqualTo(reportDto);

    }

    @Test
    public void generateReportByFamilyName_shouldListOfReturnReport() {
        //Arrange
        ReportDto reportDto = new ReportDto(1L, "john doe", 32, Gender.Masculin, "rue des nations", "08900099", RiskLevel.BORDERLINE);
        when(patientProxy.getPatientByFamilyName(any())).thenReturn(List.of(patient));
        when(calculator.computeAge(any())).thenReturn(32);
        when(calculator.isOlderThanThirty(any())).thenReturn(true);
        when(calculator.calculateTriggersNumber(any())).thenReturn(2);
        when(reportMapper.toReportDto(patient, RiskLevel.BORDERLINE, 32)).thenReturn(reportDto);
        //Act
        List<ReportDto> result = assessmentService.generateReportByFamilyName("doe");
        //Assert
        assertThat(result.get(0)).isEqualTo(reportDto);

    }
}
