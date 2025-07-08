package hexlet.code;

public enum FormatType {
    STYLISH,
    PLAIN,
    JSON;

    public static FormatType valueFromString(String format) {
        return FormatType.valueOf(format.toUpperCase());
    }
}
