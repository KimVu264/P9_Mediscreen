package com.mediscreen.front.proxy;

import com.mediscreen.front.model.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "patient", url = "localhost:8081")
public interface PatientProxy {

    @GetMapping(value = "/patients")
    List<PatientBean> getAllPatients();

    @GetMapping(value = "/patient/id")
    PatientBean getPatientById(@RequestParam("id") Long patientId);

    @GetMapping(value = "/patients/lastName")
    List<PatientBean> getPatientByLastName(@RequestParam("lastName") String lastName);

    @GetMapping(value = "/patients/firstName")
    List<PatientBean> getPatientByFirstName(@RequestParam("firstName") String firstName);

    @GetMapping(value = "/searchPatient")
    List<PatientBean> searchPatient(@RequestParam String firstName, @RequestParam String lastName);

    @PostMapping("/patient/add")
    PatientBean savePatient(@RequestBody PatientBean patient);

    @PutMapping("/patient/update")
    PatientBean updatePatient(@RequestBody PatientBean patientBean);

    @DeleteMapping("/patient/delete")
    void deletePatient(@RequestBody PatientBean patient);

}
