package hexlet.code;

import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

public class FormatterUtils {

    public static Formatter getFormatter(String format) {
        if (format.equalsIgnoreCase("stylish")) {
            return new StylishFormatter();
        } else if (format.equalsIgnoreCase("plain")) {
            return new PlainFormatter();
        } else if (format.equalsIgnoreCase("json")) {
            return new JsonFormatter();
        }

        throw new IllegalArgumentException("no render method for " + format + " format");
    }
}
