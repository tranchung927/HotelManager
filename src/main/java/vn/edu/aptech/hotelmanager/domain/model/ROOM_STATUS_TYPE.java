package vn.edu.aptech.hotelmanager.domain.model;

public enum ROOM_STATUS_TYPE {
    AVAILABLE, OCCUPIED, REPAIR, DIRTY, RESERVE;

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

    public int toStatus() {
        switch (this) {
            case AVAILABLE -> {
                return 1;
            }
            case OCCUPIED -> {
                return 2;
            }
            case REPAIR -> {
                return 3;
            }
            case DIRTY -> {
                return 4;
            }
            default -> {
                return 5;
            }
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case AVAILABLE -> {
                return "Available";
            }
            case OCCUPIED -> {
                return "Occupied";
            }
            case REPAIR -> {
                return "Repair";
            }
            case DIRTY -> {
                return "Dirty";
            }
            default -> {
                return "Reserve";
            }
        }
    }
    public String toColor() {
        switch (this) {
            case AVAILABLE -> {
                return "#4A9F4F";
            }
            case OCCUPIED -> {
                return "#ED525D";
            }
            case REPAIR -> {
                return "#627D86";
            }
            case DIRTY -> {
                return "#D1B28E";
            }
            default -> {
                return "#FC572F";
            }
        }
    }

    public String toSecondColor() {
        switch (this) {
            case AVAILABLE -> {
                return "#dbecdc";
            }
            case OCCUPIED -> {
                return "#fbdcdf";
            }
            case REPAIR -> {
                return "#e0e5e7";
            }
            case DIRTY -> {
                return "#f6f0e8";
            }
            default -> {
                return "#feddd5";
            }
        }
    }
}
