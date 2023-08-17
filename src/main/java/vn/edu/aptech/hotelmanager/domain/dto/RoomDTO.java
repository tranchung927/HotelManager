package vn.edu.aptech.hotelmanager.domain.dto;

import lombok.Data;
import vn.edu.aptech.hotelmanager.domain.model.*;

@Data
public class RoomDTO {
    private Customer customer;
    private Room room;
    private Category category;
    private CheckIn checkIn;
}
