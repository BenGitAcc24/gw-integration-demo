package com.example.gwintegration.domain;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

// Represents incoming Guidewire-style XML
@XmlRootElement(name = "AddressRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressRequest {

    @XmlElement(name = "Street")
    private String street;

    @XmlElement(name = "City")
    private String city;

    @XmlElement(name = "State")
    private String state;

    @XmlElement(name = "PostalCode")
    private String postalCode;

    @XmlElement(name = "Country")
    private String country;

    // getters and setters omitted for brevity
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}