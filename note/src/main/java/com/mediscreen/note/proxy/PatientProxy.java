package com.mediscreen.note.proxy;

import com.mediscreen.note.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(value = "patient", url = "${mediscreen.patientUrl}")
public interface PatientProxy {

    @GetMapping("/patient/id")
    public Optional<PatientDto> getPatientById(@RequestParam Long id);
}
