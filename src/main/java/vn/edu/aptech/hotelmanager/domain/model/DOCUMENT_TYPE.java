package vn.edu.aptech.hotelmanager.domain.model;

public enum DOCUMENT_TYPE {
    ID_CARD, CITIZEN_ID, PASSPORT;

    public static DOCUMENT_TYPE valueOfStatus(int status) {
        switch (status) {
            case 1 -> {
                return DOCUMENT_TYPE.ID_CARD;
            }
            case 2 -> {
                return DOCUMENT_TYPE.CITIZEN_ID;
            }
            default -> {
                return DOCUMENT_TYPE.PASSPORT;
            }
        }
    }

    public int toStatus() {
        switch (this) {
            case ID_CARD -> {
                return 1;
            }
            case CITIZEN_ID -> {
                return 2;
            }
            case PASSPORT -> {
                return 3;
            }
        }
        return 1;
    }

    @Override
    public String toString() {
        switch (this) {
            case ID_CARD -> {
                return "Identity Card";
            }
            case CITIZEN_ID -> {
                return "Citizen Identification";
            }
            case PASSPORT -> {
                return "Passport";
            }
        }
        return "";
    }
}
