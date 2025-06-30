package hexlet.code;

import java.util.List;

public class Formatter {

    public static String renderDiff(List<DiffRecord> diffRecords, String format) {
        if (format.equalsIgnoreCase("stylish")) {
            return renderStylishDiff(diffRecords);
        }

        throw new IllegalArgumentException("no render method for " + format + " format");
    }

    private static String renderStylishDiff(List<DiffRecord> diffRecords) {
        StringBuilder str = new StringBuilder("{\n");

        for (DiffRecord element : diffRecords) {
            str.append(renderDiffString(element));
        }

        str.append("}");

        return str.toString();
    }

    private static String renderDiffString(DiffRecord element) {
        String key = element.getKey();
        String status = element.getStatus();
        Object oldValue = element.getOldValue();
        Object newValue = element.getNewValue();

        return switch (status) {
            case "added" -> generateLine("+", key, newValue);
            case "removed" -> generateLine("-", key, oldValue);
            case "changed" -> generateLine("-", key, oldValue) + generateLine("+", key, newValue);
            default -> generateLine(" ", key, oldValue);
        };
    }

    private static String generateLine(String prefix, String key, Object value) {
        return " ".repeat(2) + prefix + " " + key + ": " + value + "\n";
    }
}
