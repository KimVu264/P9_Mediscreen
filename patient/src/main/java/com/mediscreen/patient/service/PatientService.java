package com.mediscreen.patient.service;

import com.mediscreen.patient.exception.DataNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class PatientService {

    private static final Logger logger = LogManager.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
        ;
    }

    public Patient savePatient(Patient patient) {
        logger.info("saving patient: {} {}", patient.getFirstName(), patient.getLastName());
        patient.setGender(patient.getGender());
        return patientRepository.save(patient);
    }

    public Patient addPatient(Model model, Patient newPatient) {
        patientRepository.save(newPatient);
        model.addAttribute("addPatientSucceed", "New patient successfully saved");
        return newPatient;
    }

    public List<Patient> getAllPatients() {
        logger.info("Get list of patients");
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) throws DataNotFoundException {
        logger.info("Get a patient with id: {}", id);
        return patientRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Patient not found"));
    }

    public List<Patient> getPatientsByLastName(String lastName) {
        logger.info("get patient with lastname: {} ", lastName);
        return patientRepository.findByLastName(lastName);
    }

/*
    public List<Patient> getPatientByFirstName(String firstName) {
        logger.info("fetching patient with firstname: {} ", firstName);
        return patientRepository.findByFirstName(firstName);

    }

 */

    public List<Patient> searchPatientsByName(String firstName, String lastName) {
        List<Patient> patientList = patientRepository.findAll();

        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            patientList = patientRepository.findPatientByFirstNameAndLastName(firstName, lastName);
        } else if (!firstName.isEmpty()) {
            patientList = patientRepository.findByFirstName(firstName);
        } else if (!lastName.isEmpty()) {
            patientList = patientRepository.findByLastName(lastName);
        }
        return patientList;
    }

    public Patient updatePatient(Patient patient) {
        logger.info("update patient: {} {}", patient.getFirstName(), patient.getLastName());
        return patientRepository.save(patient);
    }

    public Patient deletePatientById(Long id) throws DataNotFoundException {
        logger.info("delete patient with id: {} ", id);
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
        return patient;
    }

    /*
    public Page<Patient> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.patientRepository.findAll(pageable);
    }

    */

}
