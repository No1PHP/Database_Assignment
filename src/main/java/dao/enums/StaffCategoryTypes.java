package dao.enums;

public enum StaffCategoryTypes {
    NONE, ADMIN, CLEANER, STOREROOM_CLERK, MANAGER;

    public StaffCategoryTypes getByIndex(int id) {
        for (StaffCategoryTypes e : StaffCategoryTypes.values()) {
            if (e.ordinal() == id) return e;
        }
        return null;
    }
}
