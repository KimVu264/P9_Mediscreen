package com.mediscreen.assessment.proxy;

import com.mediscreen.assessment.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "note", url = "${mediscreen.noteUrl}")
public interface NoteProxy {

    @GetMapping("/notes")
    public List<NoteDto> getAllNotesByPatientId(@RequestParam Long patientId);

}
