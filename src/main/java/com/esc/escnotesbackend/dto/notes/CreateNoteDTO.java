package com.esc.escnotesbackend.dto.notes;

import java.util.List;

public record CreateNoteDTO(String header, List<NoteSectionDTO> sections) {
}
