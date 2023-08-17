package vn.edu.aptech.hotelmanager.domain.model;

public enum UNIT_TYPE {
        Can, Bag, Bottle, Package;

        public static UNIT_TYPE valueOfStatus(String status) {
                switch (status) {
                        case "Lon" -> {
                                return UNIT_TYPE.Can;
                        }
                        case "Bao" -> {
                                return UNIT_TYPE.Bag;
                        }
                        case "Chai" -> {
                                return UNIT_TYPE.Bottle;
                        }
                        default -> {
                                return UNIT_TYPE.Package;
                        }
                }
        }

        public String toStatus() {
                switch (this) {
                        case Can -> {
                                return "Lon";
                        }
                        case Bag -> {
                                return "Bao";
                        }
                        case Bottle -> {
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
                        case Can -> {
                                return "Lon";
                        }
                        case Bag -> {
                                return "Bao";
                        }
                        case Bottle -> {
                                return "Chai";
                        }
                        case Package -> {
                                return "Gói";
                        }
                }
                return null;
        }
}


