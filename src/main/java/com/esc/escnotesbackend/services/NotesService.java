package com.esc.escnotesbackend.services;

import com.esc.escnotesbackend.dto.notes.CreateNoteDTO;
import com.esc.escnotesbackend.entities.Notes;
import com.esc.escnotesbackend.entities.User;
import com.esc.escnotesbackend.mapper.NotesMapper;
import com.esc.escnotesbackend.repositories.NotesRepository;
import com.esc.escnotesbackend.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotesService {
    private final UserService userService;
    private final NotesRepository notesRepository;
    private final NotesMapper notesMapper;

    @Autowired
    public NotesService(
            UserService userService,
            NotesRepository notesRepository,
            NotesMapper notesMapper
    ) {
        this.userService = userService;
        this.notesRepository = notesRepository;
        this.notesMapper = notesMapper;
    }

    @Transactional
    public Notes createNote(CreateNoteDTO note, String jwtToken) {
        User user = userService.findUserByEmail(JwtUtil.getEmailFromToken(jwtToken));

        Notes newNote = notesMapper.createNoteDTOtoNotes(note);
        System.out.println(note);
        newNote.setUser(user);
        newNote.setCreatedAt(new Date());
        newNote.setUpdatedAt(new Date());

        notesRepository.save(newNote);
        return newNote;
    }
}
