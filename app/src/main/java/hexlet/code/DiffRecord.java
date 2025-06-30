package hexlet.code;

public class DiffRecord {
    private final String key;
    private final String status;
    private final Object oldValue;
    private final Object newValue;

    public DiffRecord(String key, String status, Object oldValue, Object newValue) {
        this.key = key;
        this.status = status;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getKey() {
        return key;
    }

    public String getStatus() {
        return status;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }
}
