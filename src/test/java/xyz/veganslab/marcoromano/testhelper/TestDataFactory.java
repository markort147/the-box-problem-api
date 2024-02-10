package xyz.veganslab.marcoromano.testhelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.veganslab.marcoromano.models.RequestDto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestDataFactory {

    public static RequestDto createRequestFromFile(String fileName) {
        Path path = Paths.get("src/test/resources/testRequests/" + fileName + ".json");
        System.out.println("TestDataFactory.createRequestFromFile(). path=" + path);
        try (InputStream inputStream = Files.newInputStream(path)) {
            String jsonString = new String(inputStream.readAllBytes());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, RequestDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}