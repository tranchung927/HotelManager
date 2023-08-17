package vn.edu.aptech.hotelmanager.domain.model;

public enum UNIT {
        Lon, Bao, Chai, Gói;

        public static UNIT valueOfStatus(String status) {
                switch (status) {
                        case "Lon" -> {
                                return UNIT.Lon;
                        }
                        case "Bao" -> {
                                return UNIT.Bao;
                        }
                        case "Chai" -> {
                                return UNIT.Chai;
                        }
                        default -> {
                                return UNIT.Gói;
                        }
                }
        }

        public String toStatus() {
                switch (this) {
                        case Lon -> {
                                return "Lon";
                        }
                        case Bao -> {
                                return "Bao";
                        }
                        case Chai -> {
                                return "Chai";
                        }
                        default -> {
                                return "Gói";
                        }
                }
        }

        @Override
        public String toString() {
                switch (this) {
                        case Lon -> {
                                return "Lon";
                        }
                        case Bao -> {
                                return "Bao";
                        }
                        case Chai -> {
                                return "Chai";
                        }
                        case Gói -> {
                                return "Gói";
                        }
                }
                return null;
        }
}


