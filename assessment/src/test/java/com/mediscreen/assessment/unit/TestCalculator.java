package com.mediscreen.assessment.unit;

import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.enums.Gender;
import com.mediscreen.assessment.utils.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestCalculator {

    @Autowired
    Calculator calculator;

    @Test
    public void calculateTest_shouldReturnAge_forGivenPatient() {
        //Arrange
        PatientDto patient = new PatientDto(1L, "john", "doe", Date.valueOf("2002-04-20"), Gender.Masculin, "0890009", "rue des nations");
        //Act
        int age = calculator.computeAge(patient.getBirthdate());
        //Assert
        assertThat(age).isEqualTo(20);

    }

    @ParameterizedTest
    @ValueSource(ints = {20, 29})
    public void isOlderThanThirtyTest_shouldReturnFalse_forYoungPatient(int years) {
        //Arrange
        PatientDto patient = new PatientDto(1L, "john", "doe", Date.valueOf("2005-11-20"), Gender.Masculin, "0890009", "rue des nations");
        //Act
        boolean result = calculator.isOlderThanThirty(patient.getBirthdate());
        //Assert
        assertThat(result).isFalse();

    }


    @ParameterizedTest
    @ValueSource(ints = {42, 30})
    void isOlderThanThirtyTest_shouldReturnTrue(int years) {
        //Arrange
        PatientDto patient = new PatientDto(1L, "john", "doe", Date.valueOf("1980-11-20"), Gender.Masculin, "0890009", "rue des nations");
        //Act
        boolean result = calculator.isOlderThanThirty(patient.getBirthdate());
        //Assert
        assertThat(result).isTrue();

    }

    @Test
    @DisplayName("calculate the number of triggers terminology present notes ")
    public void calculateTriggersNumberTest() {
        //Arrange
        List<String> notes = List.of("a few notes for a test with Hémoglobine A1C and Taille ", "a few notes for a test with Rechute and vertige");
        //Act
        int count = calculator.calculateTriggersNumber(notes);
        //Assert
        assertThat(count).isEqualTo(4);

    }
}
