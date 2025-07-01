package hexlet.code.formatters;

import hexlet.code.DiffRecord;

import java.util.List;

public class StylishFormatter implements Formatter {

    public String renderDiff(List<DiffRecord> diffRecordList) {
        StringBuilder str = new StringBuilder("{\n");

        for (DiffRecord element : diffRecordList) {
            str.append(renderDiffString(element));
        }

        str.append("}");

        return str.toString();
    }

    private String renderDiffString(DiffRecord element) {
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

    private String generateLine(String prefix, String key, Object value) {
        return " ".repeat(2) + prefix + " " + key + ": " + value + "\n";
    }
}
