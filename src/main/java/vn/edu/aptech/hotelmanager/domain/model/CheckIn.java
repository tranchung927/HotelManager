package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

import java.util.Date;

@Data
public class CheckIn {
    private long id;
    private long customerId;
    private long roomId;
    private Date checkInAt;
    private Date checkOutAt;
    private int status;
}
