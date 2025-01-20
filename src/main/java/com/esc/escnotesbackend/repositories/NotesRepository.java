package com.esc.escnotesbackend.repositories;

import com.esc.escnotesbackend.entities.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Long> {
    List<Notes> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
