package hexlet.code;

import java.util.Map;
import java.util.TreeSet;
import java.util.Set;
import java.util.LinkedHashMap;

public class Differ {

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

    public static String generate(String filepath1, String filepath2) {
        Parser parser1 = new Parser(filepath1);
        Parser parser2 = new Parser(filepath2);
        Map<String, Object> map1 = parser1.parseToMap();
        Map<String, Object> map2 = parser2.parseToMap();
        Map<String, Object> diffMap = getDiffMap(map1, map2);

        return generateDiffString(diffMap);
    }
}
