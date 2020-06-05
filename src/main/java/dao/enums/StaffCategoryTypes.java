package dao.enums;

public enum StaffCategoryTypes {
    NONE, ADMIN, CLEANER, STOREROOM_CLERK, STAFF_MANAGER, STALL_MANAGER;

    public static StaffCategoryTypes getByIndex(int id) {
        for (StaffCategoryTypes e : StaffCategoryTypes.values()) {
            if (e.ordinal() == id) return e;
        }
        return null;
    }

    public String getPosition() {
        switch (this) {
            case ADMIN: return "admin";
            case STAFF_MANAGER: return "staffManager";
            case STALL_MANAGER: return "stallManager";
            case STOREROOM_CLERK: return "clerk";
            default: return "none";
        }
    }
}
