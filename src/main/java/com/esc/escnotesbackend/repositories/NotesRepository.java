package com.esc.escnotesbackend.repositories;

import com.esc.escnotesbackend.entities.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Long> {
}
