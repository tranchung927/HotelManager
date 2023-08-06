package vn.edu.aptech.hotelmanager.domain.model;

public enum ROOM_STATUS_TYPE {
    AVAILABLE, OCCUPIED, REPAIR,DIRTY, RESERVE;
    public static ROOM_STATUS_TYPE valueOfStatus(int status) {
        switch (status) {
            case 1 -> {
                return ROOM_STATUS_TYPE.AVAILABLE;
            }
            case 2 -> {
                return ROOM_STATUS_TYPE.OCCUPIED;
            }
            case 3 -> {
                return ROOM_STATUS_TYPE.REPAIR;
            }
            case 4 -> {
                return ROOM_STATUS_TYPE.DIRTY;
            }
            default -> {
                return ROOM_STATUS_TYPE.RESERVE;
            }
        }
    }
}
