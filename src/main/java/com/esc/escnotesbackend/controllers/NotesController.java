package com.esc.escnotesbackend.controllers;

import com.esc.escnotesbackend.dto.notes.CreateNoteDTO;
import com.esc.escnotesbackend.entities.Notes;
import com.esc.escnotesbackend.services.NotesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/notes")
public class NotesController {
    private final NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/create")
    public Notes createNote(@RequestBody CreateNoteDTO createNoteDTO) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken = authorizationHeader.substring(7);

        return notesService.createNote(createNoteDTO, jwtToken);
    }
}
