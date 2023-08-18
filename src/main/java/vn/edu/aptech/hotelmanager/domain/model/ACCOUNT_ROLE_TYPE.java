package vn.edu.aptech.hotelmanager.domain.model;

public enum ACCOUNT_ROLE_TYPE {
    MANAGER, STAFF;
    public static ACCOUNT_ROLE_TYPE valueOfName(String name) {
        if (name == null) {
            return ACCOUNT_ROLE_TYPE.STAFF;
        }
        if (name.equals("manager")) {
            return ACCOUNT_ROLE_TYPE.MANAGER;
        }
        return ACCOUNT_ROLE_TYPE.STAFF;
    }

    public String toName() {
        switch (this) {
            case MANAGER -> {
                return "manager";
            }
            case STAFF -> {
                return "receptionist";
            }
        }
        return null;
    }

    @Override
    public String toString() {
        switch (this) {
            case MANAGER -> {
                return "Manager";
            }
            case STAFF -> {
                return "Receptionist";
            }
        }
        return null;
    }
}
