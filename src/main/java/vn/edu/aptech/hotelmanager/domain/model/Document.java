package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class Document {
    private long id;
    private DOCUMENT_TYPE type;
    private String value;
}
