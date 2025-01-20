package com.esc.escnotesbackend.services;

import com.esc.escnotesbackend.dto.notes.CreateNoteDTO;
import com.esc.escnotesbackend.dto.notes.UpdateNoteDTO;
import com.esc.escnotesbackend.entities.NoteSection;
import com.esc.escnotesbackend.entities.Notes;
import com.esc.escnotesbackend.entities.User;
import com.esc.escnotesbackend.exceptions.NoteNotExistException;
import com.esc.escnotesbackend.exceptions.NoteSectionNotExistException;
import com.esc.escnotesbackend.mapper.NotesMapper;
import com.esc.escnotesbackend.repositories.NoteSectionsRepository;
import com.esc.escnotesbackend.repositories.NotesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotesService {
    private final NotesRepository notesRepository;
    private final NoteSectionsRepository noteSectionsRepository;
    private final NotesMapper notesMapper;

    @Autowired
    public NotesService(
            NotesRepository notesRepository,
            NoteSectionsRepository noteSectionsRepository,
            NotesMapper notesMapper
    ) {
        this.notesRepository = notesRepository;
        this.noteSectionsRepository = noteSectionsRepository;
        this.notesMapper = notesMapper;
    }

    @Transactional
    public Notes createNote(CreateNoteDTO note, User user) {
        Notes newNote = notesMapper.createNoteDTOtoNotes(note);
        System.out.println(note);
        newNote.setUser(user);
        newNote.setCreatedAt(new Date());
        newNote.setUpdatedAt(new Date());

        notesRepository.save(newNote);
        return newNote;
    }

    public Notes getNoteById(long id) {
        return notesRepository.findById(id).orElse(null);
    }

    public List<Notes> getAllNotes() {
        return notesRepository.findAll();
    }

    public List<Notes> getNotesByUserId(User user) {
        return notesRepository.findByUserId(user.getId());
    }

    @Transactional
    public Notes updateNote(UpdateNoteDTO updateNoteDTO) {
        Notes note = getNoteById(updateNoteDTO.noteId());

        note.setUpdatedAt(new Date());
        updateNoteDTO.header().ifPresent(note::setHeader);
        updateNoteDTO.sections().ifPresent(sections -> {
            List<Long> deletableSections = new ArrayList<>();
            note.getSections().forEach(noteSection -> deletableSections.add(noteSection.getId()));

            sections.forEach(section -> {
                if (section.getId() != null) {
                    NoteSection noteSection = noteSectionsRepository.findById(section.getId()).orElseThrow(NoteSectionNotExistException::new);

                    noteSection.setTitle(section.getTitle());
                    noteSection.setContent(section.getContent());
                } else {
                    note.getSections().add(section);
                }

                deletableSections.remove(section.getId());
            });

            deletableSections.forEach(noteSectionId -> note.getSections().remove(noteSectionsRepository.findById(noteSectionId).orElse(null)));
        });

        return note;
    }

    @Transactional
    public void deleteNote(long id) {
        if (notesRepository.findById(id).isEmpty()) throw new NoteNotExistException("Note with this ID does not exist");
        notesRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllUserNotes(User user) {
        notesRepository.deleteByUserId(user.getId());
    }
}
