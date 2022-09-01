package com.mediscreen.front.proxy;

import com.mediscreen.front.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value="note" , url="${mediscreen.noteUrl}")
public interface NoteProxy {

    @RequestMapping("/notes")
    List<NoteDto> getAllPatientNotesWithPatientId(@RequestParam("patientId") Long patientId);

    @RequestMapping("/note/id")
    NoteDto getPatientNoteWithId(@RequestParam("id") String id);

    @PostMapping("/note/add")
    NoteDto addNewNote(@RequestParam("note") String note, @RequestParam("patientId") Long patientId);

    @PutMapping("/note/update")
    NoteDto updateNote(@RequestBody NoteDto updateNote);

    @DeleteMapping("/note/delete")
    void deleteNote(@RequestParam("id") String id);

}
