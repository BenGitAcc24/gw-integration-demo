package com.example.gwintegration.service; 
 
import org.apache.camel.Exchange; 
import org.apache.camel.ProducerTemplate; 
import org.springframework.stereotype.Component; 
 
@Component 
public class ExternalAddressService { 
 
    private final ProducerTemplate producerTemplate; 
 
    public ExternalAddressService(ProducerTemplate producerTemplate) { 
        this.producerTemplate = producerTemplate; 
    } 
 
    // Use Camel HTTP component to call external API; return response body as String 
    public String callExternalApi(String url) { 
        return producerTemplate.requestBodyAndHeader( 
                "http://dummy", // endpoint ignored when using 'toD' in route; here we rely on route dynamic 
                null, 
                Exchange.HTTP_URI, 
                url, 
                String.class 
        ); 
    } 
} 