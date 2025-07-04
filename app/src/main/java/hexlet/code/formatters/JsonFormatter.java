package hexlet.code.formatters;

import hexlet.code.DiffRecord;

import java.io.IOException;
import java.util.List;

import static hexlet.code.Parser.OBJECT_JSON_MAPPER;

public class JsonFormatter implements Formatter {

    @Override
    public String renderDiff(List<DiffRecord> diffRecordList) {
        try {
            return OBJECT_JSON_MAPPER.writeValueAsString(diffRecordList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
