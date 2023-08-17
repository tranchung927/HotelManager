package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class Document {
    private long id = -1;
    private DOCUMENT_TYPE type = DOCUMENT_TYPE.ID_CARD;
    private String value;
}
