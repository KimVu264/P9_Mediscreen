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
        patientRepository.saveAndFlush(newPatient);
        model.addAttribute("addPatientSucceed", "New patient successfully saved");
        return newPatient;
    }

    public List<Patient> getAllPatients() {
        logger.info("Get list of patients");
        return patientRepository.findAll();
    }

    /*
    public Patient getPatientById(Long id) throws DataNotFoundException {
        logger.info("Get a patient with id: {}", id);
        return patientRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Patient not found"));
    }

     */
    public Patient getPatientById(Long id) throws DataNotFoundException {
        logger.info("Get a patients with id: {}", id);
        Patient patient = patientRepository.getPatientById(id);
        if (patient == null) {
            throw new DataNotFoundException("This id does not exists as patient");
        }
        return patient;
    }

    public List<Patient> getPatientsByLastName(String lastName) {
        logger.info("get patient with lastname: {} ", lastName);
        return patientRepository.findByLastName(lastName);
    }

    public List<Patient> getPatientByFirstName(String firstName) {
        logger.info("fetching patient with firstname: {} ", firstName);
        return patientRepository.findByFirstName(firstName);

    }
    public List<Patient> getByPatientName(Model model, String firstName, String lastName) {
        logger.info("Get a patient by name: {} {}", firstName,lastName);
        List<Patient> patientList = new ArrayList<>();
        List<Patient> firstNameList = patientRepository.findByFirstName(firstName);
        if (firstNameList.size()!=0) {
            for (Patient pfn : firstNameList){
                patientList.add(pfn);
                model.addAttribute("patientFound", patientList);
            }
        }
        List<Patient> lastNameList = patientRepository.findByLastName(lastName);
        if (lastNameList.size() != 0) {
            for (Patient pln : lastNameList){
                patientList.add(pln);
                model.addAttribute("patientFound", patientList);
            }
        }
        else if (firstNameList.size() == 0 && lastNameList.size() == 0){
            model.addAttribute("noPatientFound", "Patient does not exists in database. Try again.");
        }
        /*HashSet duplicationCleanUp=new HashSet<>();
        patientList.removeIf(p-> !duplicationCleanUp.add(p.getId()));
        List<Patient> noDuplicatePatientList = new ArrayList<>(new HashSet<>(duplicationCleanUp));
         return noDuplicatePatientList;
         */
        return patientList;
    }

    public Patient updatePatient(Model model, Patient patient) {
        logger.info("update patient: {} {}", patient.getFirstName(), patient.getLastName());
        patientRepository.save(patient);
        model.addAttribute("patientUpdate", "Patient info successfully update and saved");
        return patient;
    }

    public Patient deletePatientById(Long id) throws DataNotFoundException {
        logger.info("delete patient with id: {} ", id);
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
        return patient;
    }

    public void deletePatient(Model model, Patient patient) {
        patientRepository.delete(patient);
        model.addAttribute("deletePatientSucceed", "Patient successfully deleted");
    }


    public Page<Patient> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.patientRepository.findAll(pageable);
    }

}
