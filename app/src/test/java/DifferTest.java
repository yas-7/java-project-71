import static org.junit.jupiter.api.Assertions.assertEquals;

import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

class DifferTest {
    @Test
    void jsonDiff() {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String filepath1 = "src/test/resources/fixtures/file1.json";
        String filepath2 = "src/test/resources/fixtures/file2.json";
        String actual = Differ.generate(filepath1, filepath2);

        assertEquals(expected, actual);
    }

    @Test
    void yamlDiff() {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String filepath1 = "src/test/resources/fixtures/file1.yaml";
        String filepath2 = "src/test/resources/fixtures/file2.yaml";
        String actual = Differ.generate(filepath1, filepath2);

        assertEquals(expected, actual);
    }
}

