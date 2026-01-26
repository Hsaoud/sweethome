package com.sweet.home.sweethome.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Skill entity - represents individual cleaning skills.
 * E.g., Window cleaning, Carpet cleaning, Pet-friendly.
 */
@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
