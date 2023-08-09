package vn.edu.aptech.hotelmanager.domain.model;

public enum ACCOUNT_ROLE_TYPE {
    MANAGER, STAFF;

    public static ACCOUNT_ROLE_TYPE valueOfName(String name) {
        switch (name) {
            case "manager" -> {
                return ACCOUNT_ROLE_TYPE.MANAGER;
            }
            default -> {
                return ACCOUNT_ROLE_TYPE.STAFF;
            }
        }
    }

    public String toName() {
        switch (this) {
            case MANAGER -> {
                return "manager";
            }
            case STAFF -> {
                return "staff";
            }
        }
        return null;
    }

    @Override
    public String toString() {
        switch (this) {
            case MANAGER -> {
                return "Quản lý";
            }
            case STAFF -> {
                return "Nhân viên";
            }
        }
        return null;
    }
}
