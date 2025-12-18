package com.example.gwintegration.routes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.springframework.stereotype.Component;

import com.example.gwintegration.domain.AddressRequest;
import com.example.gwintegration.domain.StandardizedAddress;
import com.example.gwintegration.service.AddressTransformer;
import com.example.gwintegration.util.FilePersistence;

@Component
public class AddressStandardizationRoute extends RouteBuilder {

    private final JaxbDataFormat jaxbDataFormat;
    private final AddressTransformer transformer;
    private final FilePersistence filePersistence;

    public AddressStandardizationRoute(
            JaxbDataFormat jaxbDataFormat,
            AddressTransformer transformer,
            FilePersistence filePersistence) {
        this.jaxbDataFormat = jaxbDataFormat;
        this.transformer = transformer;
        this.filePersistence = filePersistence;
    }

    @Override
    public void configure() {

        // Error handling: return a clear JSON message; persist failures as needed
        onException(Exception.class)
            .handled(true)
            .process(e -> {
                Exception ex = e.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                e.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
                e.getMessage().setHeader(Exchange.CONTENT_TYPE, "application/json");
                e.getMessage().setBody("{\"error\":\"" + ex.getMessage() + "\"}");
            });

        // Direct entry route from REST controller
        from("direct:processAddress")
            .routeId("address-standardization-route")

            // 1) Persist original XML immediately
            .process(exchange -> {
                String xml = exchange.getIn().getBody(String.class);
                filePersistence.persistRequestXml(xml);
            })

            // 2) Unmarshal XML → AddressRequest POJO
            .unmarshal(jaxbDataFormat)

            // 3) Build external URL based on country/postalCode
            .process(exchange -> {
                AddressRequest req = exchange.getMessage().getBody(AddressRequest.class);
                String url = transformer.buildZippopotamUrl(req);
                exchange.getMessage().setHeader(Exchange.HTTP_METHOD, "GET");
                exchange.getMessage().setHeader(Exchange.HTTP_URI, url);
            })

            // 4) Call external API via dynamic endpoint (toD with HTTP component)
            .toD("https://api.zippopotam.us?httpMethod=GET")
            .convertBodyTo(String.class)

            // 5) Persist raw external response JSON
            .process(exchange -> {
                String json = exchange.getMessage().getBody(String.class);
                filePersistence.persistResponseJson(json);
            })

            // 6) Map external JSON to standardized DTO to return
            .process(exchange -> {
                String json = exchange.getMessage().getBody(String.class);
                // Minimal parsing: rely on Camel Jackson or manual Map (simple approach)
                Map<?, ?> parsed = exchange.getContext().getTypeConverter().convertTo(Map.class, json);

                String postCode = (String) parsed.getOrDefault("post code", "");
                String country = (String) parsed.getOrDefault("country", "");
                List<?> placesRaw = (List<?>) parsed.getOrDefault("places", List.of());
                List<String> places = new ArrayList<>();
                for (Object p : placesRaw) {
                    if (p instanceof Map<?, ?> place) {
                        String placeName = (String) place.getOrDefault("place name", "");
                        String state = (String) place.getOrDefault("state", "");
                        places.add(placeName + ", " + state);
                    }
                }
                StandardizedAddress dto = new StandardizedAddress(postCode, country, places);
                exchange.getMessage().setBody(dto);
                exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, "application/json");
            });
    }
}