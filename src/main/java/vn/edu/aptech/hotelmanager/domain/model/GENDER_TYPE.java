package vn.edu.aptech.hotelmanager.domain.model;

public enum GENDER_TYPE {
    MALE, FEMALE, OTHER;

    public static GENDER_TYPE valueOfStatus(int status) {
        switch (status) {
            case 1 -> {
                return GENDER_TYPE.MALE;
            }
            case 2 -> {
                return GENDER_TYPE.FEMALE;
            }
            default -> {
                return GENDER_TYPE.OTHER;
            }
        }
    }

    public String getName() {
        switch (this) {
            case MALE -> {
                return "Nam";
            }
            case FEMALE -> {
                return "Nữ";
            }
            default -> {
                return "Khác";
            }
        }
    }
}
