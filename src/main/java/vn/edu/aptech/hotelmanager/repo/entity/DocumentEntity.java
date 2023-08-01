package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class DocumentEntity implements IEntity {
    private int id;
    private int type;//"1: Identity Card, 2: Citizen Identification, 3: Passport"
    private String value;
    private String note;
}
