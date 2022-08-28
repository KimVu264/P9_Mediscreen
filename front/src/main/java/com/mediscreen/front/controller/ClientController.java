package com.mediscreen.front.controller;

import com.mediscreen.front.dto.NoteDto;
import com.mediscreen.front.dto.PatientDto;
import com.mediscreen.front.dto.ReportDto;
import com.mediscreen.front.model.PatientBean;
import com.mediscreen.front.proxy.AssessmentProxy;
import com.mediscreen.front.proxy.NoteProxy;
import com.mediscreen.front.proxy.PatientProxy;
import com.mediscreen.front.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ClientController {

    private static Logger logger = LogManager.getLogger(ClientController.class);

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping("/")
    public String index() {
        logger.info("Get homepage");
        return "index";
    }

    @GetMapping("/patients")
    public String allPatients(@NotNull Model model) {
        model.addAttribute("patients", clientService.getAllPatients());
        logger.info("Show list of patients");
        return "patientsList";

    }

    @GetMapping("/addPatient")
    public String addPatient(@Valid Model model) {
        model.addAttribute("patient", new PatientBean());
        return "addPatient";
    }

    @PostMapping("/savePatient")
    public String savePatient(@Valid PatientBean patient) {
        //model.addAttribute("patient", new PatientBean());
        clientService.savePatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patient/id")
    public String patientDetails(@Valid Model model, Long id)  {
        logger.info("Get patient page with id: {}", id);
        PatientBean patient = clientService.getPatientById(id);
        model.addAttribute("patientFound", patient);
        getPatientNotes(model, id);
        ReportDto reportDto = clientService.getReportByPatientId(id);
        model.addAttribute("patientReport", reportDto);
        return "patientDetails";
    }

    @GetMapping("/searchPatient")
    public  String patientSearch(@Valid Model model, String firstName, String lastName) {
        logger.info("Send search patient named: {} {}", firstName, lastName);
        /*
        if(firstName.isEmpty() && lastName.isEmpty()) {
            model.addAttribute("patients", clientService.getAllPatients());
        } else {
             model.addAttribute("patients", clientService.getPatientByName(firstName,lastName));
        }
        return "patientsList";

         */
        List<PatientBean> patientList = clientService.getPatientByName(firstName,lastName);
        model.addAttribute("patients", patientList);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "patientsList";
    }

    @GetMapping("/updatePatient/")
    public String showUpdateForm(@Valid Model model, Long id) {
        if(id != null) {
            PatientBean patient = clientService.getPatientById(id);
            model.addAttribute("patient", patient);
            logger.info("patient for updating");
            return "updatePatient";
        } else
            return "patientsList";

    }
/*
    @PostMapping("/patient/update")
    public String patientUpdate(Model model, PatientBean patient) {
        logger.info("Send update to patient named: {} {}", patient.getFirstName(), patient.getLastName());
        clientService.updatePatient(model, patient);
        return "redirect:/patients";
    }

 */

    @RequestMapping("/patient/delete")
    public String patientDelete(@Valid Model model, PatientBean patient) {
        logger.info("Send patient to delete named: {} {}", patient.getFirstName(), patient.getLastName());
        clientService.deletePatient(model,patient);
        return "redirect:/patients";
    }

    @GetMapping("/patient/notes")
    public  String getPatientNotes(@Valid Model model, Long patientId) {
        logger.info("Send search notes for patient id: {}", patientId);
        List<NoteDto> patientNoteList = clientService.getAllNotesByPatientId(patientId);
        model.addAttribute("PatientNoteList", patientNoteList);
        model.addAttribute("PatientId", patientId);
        return "patientPageInfo";
    }

    @RequestMapping(value="/patient/note/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addPatientNote(Model model, @Valid NoteDto note, BindingResult result) {
        if (result.hasErrors()) {
            logger.info("Validate failed for adding note!");
            model.addAttribute("hasError", true);
            model.addAttribute("errMsg", "Note cannot be empty");
            return patientDetails(model, note.getPatientId());
        }
        logger.info("Send new note: {} with patient id {}", note.getNote(), note.getPatientId());
        clientService.addNewNote(note.getNote(), note.getPatientId());
        return patientDetails(model, note.getPatientId());
    }

    @PostMapping("/patient/note/update")
    public String updatePatientNote(@Valid Model model, String note, String id) {
        logger.info("Send update note: {} with id {}", note, id);
        NoteDto patientNote = clientService.getPatientNoteById(id);
        patientNote.setNote(note);
        Long patientId = patientNote.getPatientId();
        clientService.updateNote(patientNote);
        return patientDetails(model, patientId);
    }

    @RequestMapping(value="/patient/note/delete", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deletePatientNote(Model model, String id) {
        logger.info("Send delete note id: {}", id);
        NoteDto patientNote = clientService.getPatientNoteById(id);
        Long patientId = patientNote.getPatientId();
        clientService.deleteNote(id);
        return patientDetails(model, patientId);
    }


    /*
    @GetMapping("/patientsList/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<PatientBean> page = clientService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<PatientBean> listPatients = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listPatients", listPatients);
        return "patientsList";
    }

     */

}
