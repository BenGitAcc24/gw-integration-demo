package com.example.gwintegration.controller;

import com.example.gwintegration.domain.StandardizedAddress;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gw/address")
public class GuidewireRestController {

    private final ProducerTemplate producerTemplate;

    public GuidewireRestController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    // Accept XML as the request body and return standardized JSON
    @PostMapping(value = "/standardize", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardizedAddress standardize(@RequestBody String xmlPayload) {
        return producerTemplate.requestBody("direct:processAddress", xmlPayload, StandardizedAddress.class);
    }
}