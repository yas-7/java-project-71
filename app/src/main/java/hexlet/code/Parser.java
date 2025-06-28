package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {
    private final String extension;
    private final String filepath;

    private static final ObjectMapper OBJECT_JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper OBJECT_YAML_MAPPER = new YAMLMapper();

    public Parser(String filepath) {
        this.extension = getExtension(filepath);
        this.filepath = filepath;
    }

    public Map<String, Object> parseToMap() {
        String content = readFile();
        ObjectMapper mapper = getMapperObject();

        try {
            return mapper.readValue(content, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper getMapperObject() {
        if (extension.equalsIgnoreCase("yaml")) {
            return OBJECT_YAML_MAPPER;
        } else if (extension.equalsIgnoreCase("json")) {
            return OBJECT_JSON_MAPPER;
        }

        throw new IllegalArgumentException("no parser for " + extension);
    }

    private String readFile() {
        // Формируем абсолютный путь
        Path path = Paths.get(filepath).toAbsolutePath().normalize();

        if (!Files.exists(path)) {
            throw new RuntimeException("File '" + path + "' does not exist");
        }

        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getExtension(String filepath) {
        int dotIndex = filepath.lastIndexOf(".");

        return filepath.substring(dotIndex + 1);
    }
}
