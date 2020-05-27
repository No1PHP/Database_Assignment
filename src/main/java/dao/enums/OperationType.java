package dao.enums;

public enum OperationType {
    PULL, ORDER, DAY_SHIFT, STALL_CHANGE;

    public static OperationType getByIndex(int id) {
        for (OperationType e : OperationType.values()) {
            if (e.ordinal() == id) return e;
        }
        return null;
    }
}
