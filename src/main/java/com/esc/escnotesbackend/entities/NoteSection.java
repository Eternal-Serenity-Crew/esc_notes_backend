package com.esc.escnotesbackend.entities;

import com.esc.escnotesbackend.config.enums.NoteTitles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "note_sections")
public class NoteSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private NoteTitles title;

    @Column(nullable = false)
    private String content;
}
