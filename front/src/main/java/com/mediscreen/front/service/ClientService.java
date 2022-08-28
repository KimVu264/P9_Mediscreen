package com.mediscreen.front.service;

import com.mediscreen.front.controller.ClientController;
import com.mediscreen.front.dto.NoteDto;
import com.mediscreen.front.dto.ReportDto;
import com.mediscreen.front.model.PatientBean;
import com.mediscreen.front.proxy.AssessmentProxy;
import com.mediscreen.front.proxy.NoteProxy;
import com.mediscreen.front.proxy.PatientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ClientService {

    private static Logger logger = LogManager.getLogger(ClientService.class);

    private final PatientProxy patientProxy;
    private final NoteProxy noteProxy;
    private final AssessmentProxy assessmentProxy;

    @Autowired
    public ClientService(PatientProxy patientProxy, NoteProxy noteProxy, AssessmentProxy assessmentProxy) {
        this.patientProxy = patientProxy;
        this.noteProxy = noteProxy;
        this.assessmentProxy = assessmentProxy;
    }

    public PatientBean savePatient(PatientBean patient) {
        logger.info("saving patient: {} {}", patient.getFirstName(),patient.getLastName());
        patient.setGender(patient.getGender());
        return patientProxy.savePatient(patient);
    }

    public List<PatientBean> getAllPatients() {
        logger.info("Get list of patients");
        return patientProxy.getAllPatients();
    }

    public PatientBean getPatientById(Long id)  {
        logger.info("Get a patient with id: {}", id);
        return patientProxy.getPatientById(id);
    }

    public List<PatientBean> getPatientByName(String firstName, String lastName)  {
        return patientProxy.searchPatient(firstName, lastName);
    }

    public PatientBean updatePatient(PatientBean patient) {
        logger.info("update patient: {} {}", patient.getFirstName(),patient.getLastName());
        return patientProxy.updatePatient(patient);
        //model.addAttribute("patientUpdate", "Patient info successfully update and saved");
        //return patient;
    }

    public void deletePatient(Long id) {
        logger.info("delete patient with id: {}", id);
        patientProxy.deletePatient(id);
       // model.addAttribute("deletePatientSucceed", "Patient successfully deleted");
    }

    public List<NoteDto> getAllNotesByPatientId(Long patientId) {
        logger.info("Get all patients notes list");
        List<NoteDto> patientNoteList = noteProxy.getAllPatientNotesWithPatientId(patientId);
        return patientNoteList;
    }

    public ReportDto getReportByPatientId(Long patientId) {
        logger.info("Get all patients notes list");
        ReportDto reportDto = assessmentProxy.getReportByPatientId(patientId);
        return reportDto;
    }

    public NoteDto getPatientNoteById(String id) {
        logger.info("Get all patients notes list");
        NoteDto patientNoteId = noteProxy.getPatientNoteWithId(id);
        return patientNoteId;
    }


    public NoteDto addNewNote(String note, Long patientId) {
        logger.info("Add patient note send to repository with note: {}", note);
        return noteProxy.addNewNote(note,patientId);
    }

    public NoteDto updateNote(NoteDto updateNote) {
        logger.info("Update patient note send to repository with note: {}", updateNote);
        return noteProxy.updateNote(updateNote);
    }

    public void deleteNote(String id) {
        logger.info("Delete patient note send to repository with id: {}", id);
        noteProxy.deleteNote(id);
    }

    public Page<PatientBean> findPaginated(Pageable pageable) {
        final List<PatientBean> patients = new ArrayList<>();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<PatientBean> list;

        if (patients.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, patients.size());
            list = patients.subList(startItem, toIndex);
        }

        Page<PatientBean> patientPage
                = new PageImpl<PatientBean>(list, PageRequest.of(currentPage, pageSize), patients.size());

        return patientPage;
    }

}
