package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format;

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static String readFile(String filepath) throws Exception {
        // Формируем абсолютный путь,
        // если filePath будет содержать относительный путь,
        // то мы всегда будет работать с абсолютным
        Path path1 = Paths.get(filepath).toAbsolutePath().normalize();

        // Проверяем существование файла
        if (!Files.exists(path1)) {
            throw new Exception("File '" + path1 + "' does not exist");
        }

        // Читаем файл
        String content = Files.readString(path1);

        // Выводим содержимое
        System.out.println(content);

        return content;
    }

    public static Map<String, Object> parseToMap(String content) throws JsonProcessingException {
        return objectMapper.readValue(content, new TypeReference<Map<String,Object>>(){});
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("format=" + format + " file1=" + filepath1 + " file2" + filepath2);

        String content1 = readFile(filepath1);
        String content2 = readFile(filepath2);

        Map<String, Object> map1 = parseToMap(content1);
        Map<String, Object> map2 = parseToMap(content2);

        System.out.println("=======map1========");
        for (var entry: map1.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("=======map2========");
        for (var entry: map2.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
