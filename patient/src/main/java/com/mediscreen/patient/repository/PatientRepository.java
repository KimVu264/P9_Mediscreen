package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient getPatientById(Long id);
    List<Patient> findByLastName(String lastName);
    List<Patient> findByFirstName(String firstName);

    List<Patient> findPatientByFirstNameAndLastName(String firstName, String lastName);
}
