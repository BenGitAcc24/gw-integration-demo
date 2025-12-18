package com.example.gwintegration.util; 
 
import org.springframework.stereotype.Component; 
 
import java.io.IOException; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 
import java.time.Instant; 
import java.time.format.DateTimeFormatter; 
import java.util.UUID; 
 
@Component 
public class FilePersistence { 
 
    private final Path baseDir = Paths.get("data"); 
    private final Path requestsDir = baseDir.resolve("requests"); 
    private final Path responsesDir = baseDir.resolve("responses"); 
 
    public FilePersistence() { 
        try { 
            Files.createDirectories(requestsDir); 
            Files.createDirectories(responsesDir); 
        } catch (IOException e) { 
            throw new RuntimeException("Failed to initialize persistence directories", e); 
        } 
    } 
 
    public Path persistRequestXml(String xml) { 
        String name = "req_" + timestamp() + "_" + UUID.randomUUID() + ".xml"; 
        Path file = requestsDir.resolve(name); 
        write(file, xml); 
        return file; 
    } 
 
    public Path persistResponseJson(String json) { 
        String name = "resp_" + timestamp() + "_" + UUID.randomUUID() + ".json"; 
        Path file = responsesDir.resolve(name); 
        write(file, json); 
        return file; 
    } 
 
    private String timestamp() { 
        return DateTimeFormatter.ISO_INSTANT.format(Instant.now()); 
    } 
 
    private void write(Path path, String content) { 
        try { 
            Files.writeString(path, content, StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING); 
        } catch (IOException e) { 
            throw new RuntimeException("Failed to write file: " + path, e); 
        } 
    } 
} 