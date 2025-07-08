package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.util.Map;
import java.util.TreeSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import static hexlet.code.Status.ADDED;
import static hexlet.code.Status.REMOVED;
import static hexlet.code.Status.CHANGED;
import static hexlet.code.Status.UNCHANGED;

public class Differ {

    public static String generate(String filepath1, String filepath2) {
        return generate(filepath1, filepath2, FormatType.STYLISH.toString());
    }

    public static String generate(String filepath1, String filepath2, String format) {
        Parser parser1 = new Parser(filepath1);
        Parser parser2 = new Parser(filepath2);
        Map<String, Object> map1 = parser1.parseToMap();
        Map<String, Object> map2 = parser2.parseToMap();
        List<DiffRecord> diffRecordList = getDiffRecordList(map1, map2);

        return generateDiffString(diffRecordList, FormatType.valueFromString(format));
    }

    private static List<DiffRecord> getDiffRecordList(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> keys = new TreeSet<>(map1.keySet());
        keys.addAll(map2.keySet());
        List<DiffRecord> result = new ArrayList<>();

        for (String key : keys) {
            var value1 = map1.get(key);
            var value2 = map2.get(key);

            if (!map1.containsKey(key)) {
                result.add(new DiffRecord(key, ADDED, value1, value2));
            } else if (!map2.containsKey(key)) {
                result.add(new DiffRecord(key, REMOVED, value1, value2));
            } else if (Objects.equals(value1, value2)) {
                result.add(new DiffRecord(key, UNCHANGED, value1, value2));
            } else {
                result.add(new DiffRecord(key, CHANGED, value1, value2));
            }
        }

        return result;
    }

    private static String generateDiffString(List<DiffRecord> diffRecordList, FormatType format) {
        Formatter formatter = FormatterUtils.getFormatter(format);
        return formatter.renderDiff(diffRecordList);
    }
}
