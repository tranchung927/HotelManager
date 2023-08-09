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

    public int toStatus() {
        switch (this) {
            case MALE -> {
                return 1;
            }
            case FEMALE -> {
                return 2;
            }
            default -> {
                return 3;
            }
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case MALE -> {
                return "Male";
            }
            case FEMALE -> {
                return "Female";
            }
            case OTHER -> {
                return "Other";
            }
        }
        return null;
    }
}
