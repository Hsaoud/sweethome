package com.sweet.home.sweethome.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Category entity - represents types of cleaning services.
 * E.g., General Cleaning, Deep Clean, Move-in/out, Office Cleaning.
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    private String icon;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
