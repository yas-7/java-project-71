package hexlet.code.formatters;

import hexlet.code.DiffRecord;

import java.util.List;

public class PlainFormatter implements Formatter {

    /**
     * Формирует простую текстовую версию представления различий между двумя конфигурациями.
     * @param diffRecordList список записей различий
     * @return строку, представляющую отличия в удобочитаемом виде
     */
    @Override
    public String renderDiff(List<DiffRecord> diffRecordList) {
        StringBuilder str = new StringBuilder();

        for (DiffRecord element : diffRecordList) {
            str.append(renderDiffString(element));
        }

        return str.toString().trim();
    }

    private String renderDiffString(DiffRecord element) {
        String key = element.key();
        String status = element.status();
        Object oldValue = getValueRepresentation(element.oldValue());
        Object newValue = getValueRepresentation(element.newValue());

        return switch (status) {
            case "added" -> String.format("Property '%s' was added with value: %s\n", key, newValue);
            case "removed" -> String.format("Property '%s' was removed\n", key);
            case "changed" -> String.format("Property '%s' was updated. From %s to %s\n", key, oldValue, newValue);
            default -> "";
        };
    }

    private Object getValueRepresentation(Object value) {
        if (value == null || value instanceof Number || value instanceof Boolean || value instanceof Character) {
            return value;
        }

        if (value instanceof CharSequence) {
            return String.format("'%s'", value);
        }

        return "[complex value]";
    }
}
