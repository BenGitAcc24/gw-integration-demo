package com.example.gwintegration.domain;

import java.util.List;

// Simple DTO to return a standardized address interpretation
public class StandardizedAddress {

    private String postalCode;
    private String country;
    private List<String> places; // e.g., city-state combinations from Zippopotam

    public StandardizedAddress() {}

    public StandardizedAddress(String postalCode, String country, List<String> places) {
        this.postalCode = postalCode;
        this.country = country;
        this.places = places;
    }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public List<String> getPlaces() { return places; }
    public void setPlaces(List<String> places) { this.places = places; }
}