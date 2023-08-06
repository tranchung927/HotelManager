package vn.edu.aptech.hotelmanager.domain.model;

public enum CATEGORY_TYPE {
    PRODUCT, ROOM;

    public static CATEGORY_TYPE valueOfStatus(int status) {
        if (status == 2) {
            return CATEGORY_TYPE.ROOM;
        }
        return CATEGORY_TYPE.PRODUCT;
    }
}
