package com.esc.escnotesbackend.repositories;

import com.esc.escnotesbackend.entities.NoteSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteSectionsRepository extends JpaRepository<NoteSection, Long> {
}
