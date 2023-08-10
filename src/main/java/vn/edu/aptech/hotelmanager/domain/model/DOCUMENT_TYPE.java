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
