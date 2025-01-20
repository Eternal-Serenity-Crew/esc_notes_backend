package com.esc.escnotesbackend.mapper;

import com.esc.escnotesbackend.dto.notes.CreateNoteDTO;
import com.esc.escnotesbackend.entities.Notes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotesMapper {
    CreateNoteDTO noteToCreateNoteDTO(Notes note);
    Notes createNoteDTOtoNotes(CreateNoteDTO createNoteDTO);
}
