package vn.edu.aptech.hotelmanager.repo;

import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.model.*;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.repo.converter.RoomEntityToRoom;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomRepoImpl implements IRoomRepo {
    @Override
    public List<RoomDTO> getListRoom(int page, int pageSize) {
        List<RoomDTO> roomDTOList = new ArrayList<>();
        String url = "SELECT rooms.id, rooms.name, rooms.status, rooms.number_of_beds, rooms.price, rooms.category_id" +
                ", categories.name AS category_name" +
                ", categories.code AS category_code" +
                " FROM rooms" +
                " INNER JOIN categories ON rooms.category_id = categories.id";
        try {
            ResultSet roomResultSet = CrudUtil.execute(url);
            while (roomResultSet.next()){
                RoomDTO roomDTO = new RoomDTO();
                Room room = new RoomEntityToRoom().convert(roomResultSet);
                roomDTO.setRoom(room);

                Category category = new Category();
                category.setId(room.getCategoryId());
                category.setName(roomResultSet.getString("category_name"));
                category.setCode(roomResultSet.getString("category_code"));
                roomDTO.setCategory(category);

                String checkInSql = "SELECT check_in.id, check_in.customer_id, check_in.check_in_at, check_in.check_out_at, check_in.status," +
                        " customers.document_id, customers.first_name, customers.last_name, customers.email, customers.phone_number, customers.dob," +
                        " customers.sex, customers.address_id" +
                        " FROM check_in" +
                        " INNER JOIN customers ON check_in.customer_id = customers.id" +
                        " WHERE check_in.room_id = ? AND check_in.status = 1";
                ResultSet checkInResultSet = CrudUtil.execute(checkInSql, room.getId());
                if (checkInResultSet.next()) {
                    Customer customer = new Customer();
                    customer.setId(checkInResultSet.getLong("customer_id"));
                    customer.setDocumentId(checkInResultSet.getLong("document_id"));
                    customer.setFirstName(checkInResultSet.getString("first_name"));
                    customer.setLastName(checkInResultSet.getString("last_name"));
                    customer.setEmail(checkInResultSet.getString("email"));
                    customer.setPhoneNumber(checkInResultSet.getString("phone_number"));
                    customer.setDob(checkInResultSet.getDate("dob"));
                    customer.setGender(GENDER_TYPE.valueOfStatus(checkInResultSet.getInt("sex")));
                    customer.setAddressId(checkInResultSet.getLong("address_id"));
                    roomDTO.setCustomer(customer);

                    CheckIn checkIn = new CheckIn();
                    checkIn.setCustomerId(checkInResultSet.getLong("customer_id"));
                    checkIn.setId(checkInResultSet.getLong("id"));
                    checkIn.setStatus(checkInResultSet.getInt("status"));
                    checkIn.setCheckInAt(checkInResultSet.getTimestamp("check_in_at"));
                    checkIn.setCheckOutAt(checkInResultSet.getTimestamp("check_out_at"));
                    roomDTO.setCheckIn(checkIn);
                }

                roomDTOList.add(roomDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return roomDTOList;
    }

    @Override
    public RoomDTO createOrUpdate(RoomDTO roomDTO) throws Exception {
//        ResultSet resultSet = CrudUtil.execute("SELECT * FROM rooms WHERE id = ?",room.getId());
//        String sql;
//        if (resultSet.next()){
//            sql = "UPDATE rooms SET name = ? , status = ? , number_of_beds = ? , price = ? , category_id = ? WHERE is =?";
//        }else {
//            sql = "INSERT INTO rooms(name,status,number_of_beds,price,category_id) VALUES (?,?,?,?,?)";
//            ResultSet set = CrudUtil.execute("SELECT * FROM rooms ODER BY id DESC LIMIT 1");
//            room.setId(set.getLong("id"));
//
//        }
//        CrudUtil.execute(sql,room.getName(),room.getStatus(),room.getNumberOfBeds(),room.getPrice(),room.getId());
//            return room;
        return null;
    }
    @Override
    public Boolean deleteRoom(Long id) throws Exception {
        String url = "DELETE FROM rooms WHERE id = ?";
        CrudUtil.execute(url, id);
        return true;
    }
}
