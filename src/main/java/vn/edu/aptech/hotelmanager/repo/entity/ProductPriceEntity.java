package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class ProductPriceEntity implements IEntity {
    private int id;
    private int product_id;
    private int price_policy_id;
    private double value;
}
