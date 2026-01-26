package com.sweet.home.sweethome.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "homers")
public class Homer extends User {

    private String address;

    private String city;

    private String postalCode;

    private String additionalInfo;

    public Homer() {
    }

    @PrePersist
    public void prePersist() {
        setRole(Role.HOMER);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Homer homer = (Homer) o;
        return Objects.equals(getId(), homer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
