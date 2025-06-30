package hexlet.code;

import java.util.Map;
import java.util.TreeSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Differ {

    public static List<DiffRecord> getDiffRecordList(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> keys = new TreeSet<>(map1.keySet());
        keys.addAll(map2.keySet());
        List<DiffRecord> result = new ArrayList<>();

        for (String key : keys) {
            var value1 = map1.get(key);
            var value2 = map2.get(key);

            if (!map1.containsKey(key)) {
                result.add(new DiffRecord(key, "added", value1, value2));
            } else if (!map2.containsKey(key)) {
                result.add(new DiffRecord(key, "removed", value1, value2));
            } else if (Objects.equals(value1, value2)) {
                result.add(new DiffRecord(key, "unchanged", value1, value2));
            } else {
                result.add(new DiffRecord(key, "changed", value1, value2));
            }
        }

        return result;
    }

    public static String generateDiffString(List<DiffRecord> diffRecordList, String format) {
        return Formatter.renderDiff(diffRecordList, format);
    }

    public static String generate(String filepath1, String filepath2, String format) {
        Parser parser1 = new Parser(filepath1);
        Parser parser2 = new Parser(filepath2);
        Map<String, Object> map1 = parser1.parseToMap();
        Map<String, Object> map2 = parser2.parseToMap();
        List<DiffRecord> diffRecordList = getDiffRecordList(map1, map2);

        return generateDiffString(diffRecordList, format);
    }
}
