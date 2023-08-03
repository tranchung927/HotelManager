package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class CheckInService {
    private long id;
    private long checkInId;
    private long serviceId;
    private int status;
    private double quantity;
    private double price;//"Default set from service"
    private String note;
}
