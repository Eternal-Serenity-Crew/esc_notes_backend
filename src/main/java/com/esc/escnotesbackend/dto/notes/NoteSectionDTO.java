package com.esc.escnotesbackend.dto.notes;

import com.esc.escnotesbackend.config.enums.NoteTitles;

public record NoteSectionDTO(NoteTitles title, String content) {
}
