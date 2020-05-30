package dao.enums;

public enum MaterialTypes {
    STAPLE, VEGETABLE, MEAT,FRUIT;

    public static MaterialTypes getByIndex(int id) {
        for (MaterialTypes e : MaterialTypes.values()) {
            if (e.ordinal() == id) return e;
        }
        return null;
    }
}
