package com.sweet.home.sweethome.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Cleaner entity - represents a cleaning service provider.
 * Extends User with professional profile details, skills, and categories.
 */
@Entity
@Table(name = "cleaners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cleaner extends User {

    @Column(length = 500)
    private String headline;

    @Column(length = 2000)
    private String bio;

    private BigDecimal hourlyRate;

    @Min(0)
    @Max(50)
    private Integer experienceYears;

    private String city;

    private String serviceArea;

    private boolean available = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cleaner_categories", joinColumns = @JoinColumn(name = "cleaner_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cleaner_skills", joinColumns = @JoinColumn(name = "cleaner_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();

    @PrePersist
    public void prePersist() {
        setRole(Role.CLEANER);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }
}
