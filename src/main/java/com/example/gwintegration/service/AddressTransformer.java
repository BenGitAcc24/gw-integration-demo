package com.example.gwintegration.service; 
 
import com.example.gwintegration.domain.AddressRequest; 
import org.springframework.stereotype.Component; 
 
@Component 
public class AddressTransformer { 
 
    // Minimal transformation: prepare target URL based on country + postal code 
    public String buildZippopotamUrl(AddressRequest req) { 
        // Defaults to US if not provided (common Guidewire pattern: sensible defaults) 
        String country = (req.getCountry() == null || req.getCountry().isBlank()) ? "us" : 
req.getCountry().toLowerCase(); 
        String postal = req.getPostalCode(); 
        if (postal == null || postal.isBlank()) { 
            throw new IllegalArgumentException("PostalCode is required for standardization"); 
        } 
        return "https://api.zippopotam.us/" + country + "/" + postal; 
    } 
}