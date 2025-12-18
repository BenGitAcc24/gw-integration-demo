package com.example.gwintegration.config;

import jakarta.xml.bind.JAXBContext;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.gwintegration.domain.AddressRequest;

@Configuration
public class CamelConfig {

    @Bean
    public JaxbDataFormat jaxbDataFormat() throws Exception {
        // Create a JAXBContext for your annotated classes
        JAXBContext jaxbContext = JAXBContext.newInstance(AddressRequest.class);
        return new JaxbDataFormat();
    }
}

