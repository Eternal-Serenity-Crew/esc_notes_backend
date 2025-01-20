package com.esc.escnotesbackend.dto.notes;

import com.esc.escnotesbackend.entities.NoteSection;

import java.util.List;
import java.util.Optional;

public record UpdateNoteDTO(Long noteId, Optional<String> header, Optional<List<NoteSection>> sections) {
}
