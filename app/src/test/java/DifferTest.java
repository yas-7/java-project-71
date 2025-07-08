import hexlet.code.Differ;
import hexlet.code.FormatType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {

    @ParameterizedTest
    @ValueSource(strings = {"json", "yaml", "yml"})
    void stylishDiff(String ext) {
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        String filepath1 = "src/test/resources/fixtures/file1." + ext;
        String filepath2 = "src/test/resources/fixtures/file2." + ext;
        String actual = Differ.generate(filepath1, filepath2, FormatType.STYLISH.toString());
        String actualNoFormat = Differ.generate(filepath1, filepath2);

        assertEquals(expected, actual);
        assertEquals(expected, actualNoFormat);

    }

    @ParameterizedTest
    @ValueSource(strings = {"json", "yaml", "yml"})
    void plainDiff(String ext) {
        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";

        String filepath1 = "src/test/resources/fixtures/file1." + ext;
        String filepath2 = "src/test/resources/fixtures/file2." + ext;
        String actual = Differ.generate(filepath1, filepath2, FormatType.PLAIN.toString());

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"json", "yaml", "yml"})
    void jsonDiff(String ext) {
        String expected = "[{\"key\":\"chars1\",\"status\":\"UNCHANGED\",\"oldValue\":[\"a\",\"b\",\"c\"],"
                          + "\"newValue\":[\"a\",\"b\",\"c\"]},{\"key\":\"chars2\",\"status\":\"CHANGED\",\"oldValue\":"
                          + "[\"d\",\"e\",\"f\"],\"newValue\":false},{\"key\":\"checked\",\"status\":\"CHANGED\","
                          + "\"oldValue\":false,\"newValue\":true},{\"key\":\"default\",\"status\":\"CHANGED\","
                          + "\"oldValue\":null,\"newValue\":[\"value1\",\"value2\"]},{\"key\":\"id\",\"status\":"
                          + "\"CHANGED\",\"oldValue\":45,\"newValue\":null},{\"key\":\"key1\",\"status\":\"REMOVED\","
                          + "\"oldValue\":\"value1\",\"newValue\":null},{\"key\":\"key2\",\"status\":\"ADDED\","
                          + "\"oldValue\":null,\"newValue\":\"value2\"},{\"key\":\"numbers1\",\"status\":\"UNCHANGED\","
                          + "\"oldValue\":[1,2,3,4],\"newValue\":[1,2,3,4]},{\"key\":\"numbers2\",\"status\":"
                          + "\"CHANGED\",\"oldValue\":[2,3,4,5],\"newValue\":[22,33,44,55]},{\"key\":\"numbers3\","
                          + "\"status\":\"REMOVED\",\"oldValue\":[3,4,5],\"newValue\":null},{\"key\":\"numbers4\","
                          + "\"status\":\"ADDED\",\"oldValue\":null,\"newValue\":[4,5,6]},{\"key\":\"obj1\",\"status\":"
                          + "\"ADDED\",\"oldValue\":null,\"newValue\":{\"nestedKey\":\"value\",\"isNested\":true}},"
                          + "{\"key\":\"setting1\",\"status\":\"CHANGED\",\"oldValue\":\"Some value\",\"newValue\":"
                          + "\"Another value\"},{\"key\":\"setting2\",\"status\":\"CHANGED\",\"oldValue\":200,"
                          + "\"newValue\":300},{\"key\":\"setting3\",\"status\":\"CHANGED\",\"oldValue\":true,"
                          + "\"newValue\":\"none\"}]";

        String filepath1 = "src/test/resources/fixtures/file1." + ext;
        String filepath2 = "src/test/resources/fixtures/file2." + ext;
        String actual = Differ.generate(filepath1, filepath2, FormatType.JSON.toString());

        assertEquals(expected, actual);
    }

    @Test
    void unsupportedFormatDiff() {
        String filepath1 = "src/test/resources/fixtures/file1.json";
        String filepath2 = "src/test/resources/fixtures/file2.json";

        assertThrows(IllegalArgumentException.class,
                () -> Differ.generate(filepath1, filepath2, "nonexistent"));
    }
}

