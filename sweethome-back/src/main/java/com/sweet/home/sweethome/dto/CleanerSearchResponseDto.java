package com.sweet.home.sweethome.dto;

import com.sweet.home.sweethome.model.Cleaner;

import java.math.BigDecimal;

public class CleanerSearchResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String headline;
    private String bio;
    private BigDecimal pricePerSqm;
    private BigDecimal calculatedTotalPrice;
    private Double averageRating;
    private Integer reviewCount;

    public CleanerSearchResponseDto() {
    }

    public CleanerSearchResponseDto(Cleaner cleaner, Integer surface) {
        this.id = cleaner.getId();
        this.firstName = cleaner.getFirstName();
        this.lastName = cleaner.getLastName();
        this.headline = cleaner.getHeadline();
        this.bio = cleaner.getBio();
        this.pricePerSqm = cleaner.getPricePerSqm();

        if (surface != null && cleaner.getPricePerSqm() != null) {
            this.calculatedTotalPrice = cleaner.getPricePerSqm().multiply(new BigDecimal(surface));
        }

        // Ratings to be aggregated or mapped
        this.averageRating = 5.0; // Mocked default
        this.reviewCount = cleaner.getReviewsReceived() != null ? cleaner.getReviewsReceived().size() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public BigDecimal getPricePerSqm() {
        return pricePerSqm;
    }

    public void setPricePerSqm(BigDecimal pricePerSqm) {
        this.pricePerSqm = pricePerSqm;
    }

    public BigDecimal getCalculatedTotalPrice() {
        return calculatedTotalPrice;
    }

    public void setCalculatedTotalPrice(BigDecimal calculatedTotalPrice) {
        this.calculatedTotalPrice = calculatedTotalPrice;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
}
