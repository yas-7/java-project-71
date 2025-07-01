package hexlet.code.formatters;

import hexlet.code.DiffRecord;
import java.util.List;

public interface Formatter {
    String renderDiff(List<DiffRecord> diffRecordList);
}
