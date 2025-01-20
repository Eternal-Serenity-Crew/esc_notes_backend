package com.esc.escnotesbackend.controllers;

import com.esc.escnotesbackend.dto.notes.CreateNoteDTO;
import com.esc.escnotesbackend.dto.notes.UpdateNoteDTO;
import com.esc.escnotesbackend.entities.Notes;
import com.esc.escnotesbackend.entities.User;
import com.esc.escnotesbackend.services.NotesService;
import com.esc.escnotesbackend.services.UserService;
import com.esc.escnotesbackend.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {
    private final NotesService notesService;
    private final UserService userService;

    @Autowired
    public NotesController(
            NotesService notesService,
            UserService userService
    ) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public Notes createNote(@RequestBody CreateNoteDTO createNoteDTO) {
        return notesService.createNote(createNoteDTO, getUserFromRequest());
    }

    @GetMapping("/getById")
    public Notes getNoteById(@RequestBody int id) {
        return notesService.getNoteById(id);
    }

    @GetMapping("/getAll")
    public List<Notes> getAllNotes() {
        return notesService.getAllNotes();
    }

    @GetMapping("/getByUserId")
    public List<Notes> getNotesByUserId() {
        return notesService.getNotesByUserId(getUserFromRequest());
    }

    @PatchMapping("/updateNote")
    public Notes updateNote(@RequestBody UpdateNoteDTO updateNoteDTO) {
        return notesService.updateNote(updateNoteDTO);
    }

    @DeleteMapping("/deleteNote")
    public void deleteNote(@RequestBody int noteId) {
        notesService.deleteNote(noteId);
    }

    @DeleteMapping("/deleteByUserId")
    public void deleteByUserId() {
        notesService.deleteAllUserNotes(getUserFromRequest());
    }

    private String getTokenFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader.substring(7);
    }

    private User getUserFromRequest() {
        return userService.findUserByEmail(JwtUtil.getEmailFromToken(getTokenFromRequest()));
    }
}
