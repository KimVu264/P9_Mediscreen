package com.mediscreen.assessment.utils;

import com.mediscreen.assessment.constant.TriggerTerm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


@Component
public class Calculator {

    private static Logger logger = LogManager.getLogger(Calculator.class);

    public static int computeAge(Date birthDate) {
        LocalDate birthDateLocalDate = new Date(birthDate.getTime()).toLocalDate();
        return Period.between(birthDateLocalDate, LocalDate.now()).getYears();
    }

    public boolean isOlderThanThirty(Date birthdate) {
        logger.debug("check if age is greater than 30: {}",birthdate);
        return computeAge(birthdate) >= 30;

    }

    public int calculateTriggersNumber(List<String> notes) {
        logger.debug("calculate number of triggers");
        Set<String> patientsTerminologyTriggers = new HashSet<>();
        notes.forEach(n -> {
                    TriggerTerm.TERMINOLOGY_TRIGGERS.forEach(t -> {
                        if (n.toLowerCase(Locale.ROOT).contains(t.toLowerCase(Locale.ROOT))) {
                            patientsTerminologyTriggers.add(t);
                        }
                    });
                }
        );
        return patientsTerminologyTriggers.size();
    }

}
