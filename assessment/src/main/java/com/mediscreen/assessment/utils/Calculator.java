package com.mediscreen.assessment.utils;

import com.mediscreen.assessment.constant.TriggerTerm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Component
public class Calculator {

    private static Logger logger = LogManager.getLogger(Calculator.class);

    public static LocalDate convert(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static int computeAge(Date birthDate) {
        return Period.between(convert(birthDate), LocalDate.now()).getYears();
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
