package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeSet;
import java.util.Set;
import java.util.LinkedHashMap;

public class Differ {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Map<String, Object> parseToMap(String content) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

    public static String readFile(String filepath) throws Exception {
        // Формируем абсолютный путь,
        // если filePath будет содержать относительный путь,
        // то мы всегда будет работать с абсолютным
        Path path = Paths.get(filepath).toAbsolutePath().normalize();

        // Проверяем существование файла
        if (!Files.exists(path)) {
            throw new Exception("File '" + path + "' does not exist");
        }

        // Читаем файл
        return Files.readString(path);
    }

    public static Map<String, Object> getDiffMap(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> keys = new TreeSet<>(map1.keySet());
        keys.addAll(map2.keySet());
        Map<String, Object> result = new LinkedHashMap<>();

        for (String key : keys) {
            var value1 = map1.get(key);
            var value2 = map2.get(key);

            if (!map1.containsKey(key)) {
                result.put("+ " + key, value2);
            } else if (!map2.containsKey(key)) {
                result.put("- " + key, value1);
            } else if (value1.equals(value2)) {
                result.put("  " + key, value1);
            } else {
                result.put("- " + key, value1);
                result.put("+ " + key, value2);
            }
        }

        return result;
    }

    public static String generateDiffString(Map<String, Object> diffMap) {
        StringBuilder str = new StringBuilder("{\n");

        for (var entry: diffMap.entrySet()) {
            str
                .append("  ")
                .append(entry.getKey())
                .append(": ")
                .append(entry.getValue())
                .append("\n");
        }

        str.append("}");

        return str.toString();
    }


    public static String generate(String filepath1, String filepath2) throws Exception {
        String content1 = readFile(filepath1);
        String content2 = readFile(filepath2);
        Map<String, Object> map1 = parseToMap(content1);
        Map<String, Object> map2 = parseToMap(content2);
        Map<String, Object> diffMap = getDiffMap(map1, map2);

        return generateDiffString(diffMap);
    }
}
