package hexlet.code.formatters;

import hexlet.code.DiffRecord;
import hexlet.code.Status;

import java.util.List;

public class StylishFormatter implements Formatter {

    /**
     * Формирует стильное текстовое представление различий между двумя конфигурациями.
     * @param diffRecordList список записей различий
     * @return строку, представляющую разницу в визуальном стиле
     */
    @Override
    public String renderDiff(List<DiffRecord> diffRecordList) {
        StringBuilder str = new StringBuilder("{\n");

        for (DiffRecord element : diffRecordList) {
            str.append(renderDiffString(element));
        }

        str.append("}");

        return str.toString();
    }

    private String renderDiffString(DiffRecord element) {
        String key = element.key();
        Status status = element.status();
        Object oldValue = element.oldValue();
        Object newValue = element.newValue();

        return switch (status) {
            case ADDED -> generateLine("+", key, newValue);
            case REMOVED -> generateLine("-", key, oldValue);
            case CHANGED -> generateLine("-", key, oldValue) + generateLine("+", key, newValue);
            default -> generateLine(" ", key, oldValue);
        };
    }

    private String generateLine(String prefix, String key, Object value) {
        return " ".repeat(2) + prefix + " " + key + ": " + value + "\n";
    }
}
