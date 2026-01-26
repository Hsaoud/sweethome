package com.sweet.home.sweethome.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Homer entity - represents a customer looking for cleaning services.
 * Extends User with location-specific fields.
 */
@Entity
@Table(name = "homers")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Homer extends User {

    private String address;

    private String city;

    private String postalCode;

    private String additionalInfo;

    @PrePersist
    public void prePersist() {
        setRole(Role.HOMER);
    }
}
