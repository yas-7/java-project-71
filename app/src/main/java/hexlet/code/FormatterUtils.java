package hexlet.code;

import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

public class FormatterUtils {

    public static Formatter getFormatter(FormatType format) {
        return switch (format) {
            case STYLISH -> new StylishFormatter();
            case PLAIN -> new PlainFormatter();
            case JSON -> new JsonFormatter();
        };
    }
}
