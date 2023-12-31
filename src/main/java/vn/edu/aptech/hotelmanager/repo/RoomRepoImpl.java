package vn.edu.aptech.hotelmanager.repo;

import vn.edu.aptech.hotelmanager.MainApplication;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.model.*;
import vn.edu.aptech.hotelmanager.domain.model.Room;

import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.repo.converter.AddressEntityToAddress;
import vn.edu.aptech.hotelmanager.repo.converter.DocumentEntityToDocument;
import vn.edu.aptech.hotelmanager.repo.converter.RoomEntityToRoom;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;
import vn.edu.aptech.hotelmanager.utils.DateUtils;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomRepoImpl implements IRoomRepo {
    @Override
    public List<RoomStatusSummary> getSummaryForStatus() {
        List<RoomStatusSummary> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            try {
                String query = "SELECT COUNT(id) AS count FROM rooms WHERE status = ?";
                ResultSet resultSet = CrudUtil.execute(query, i);
                while (resultSet.next()) {
                    RoomStatusSummary summary = new RoomStatusSummary();
                    summary.setStatus(ROOM_STATUS_TYPE.valueOfStatus(i));
                    summary.setCount(resultSet.getInt("count"));
                    list.add(summary);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<RoomDTO> getListRoom(ROOM_STATUS_TYPE statusType) {
        List<RoomDTO> roomDTOList = new ArrayList<>();
        String url = "SELECT rooms.id, rooms.name, rooms.status, rooms.number_of_beds, rooms.price, rooms.category_id" +
                ", categories.name AS category_name" +
                ", categories.code AS category_code" +
                " FROM rooms" +
                " INNER JOIN categories ON rooms.category_id = categories.id";
        if (statusType != null) {
            url += " WHERE rooms.status = " + statusType.toStatus();
        }
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
    public Room creatOrUpdate(Room room) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM rooms WHERE id = ?",room.getId());
        String sql;
        if (resultSet.next()){
            sql = "UPDATE rooms SET name = ? , status = ? , number_of_beds = ? , price = ? , category_id = ? WHERE is =?";
        }else {
            sql = "INSERT INTO rooms(name,status,number_of_beds,price,category_id) VALUES (?,?,?,?,?)";
            ResultSet set = CrudUtil.execute("SELECT * FROM rooms ORDER BY id DESC LIMIT 1");
            room.setId(set.getLong("id"));

        }
        CrudUtil.execute(sql,room.getName(),room.getStatus(),room.getNumberOfBeds(),room.getPrice(),room.getId());
            return room;
    }

    @Override
    public void checkIn(RoomDTO roomDTO) throws Exception {
        Address address = roomDTO.getAddress();
        String insertAddressSQL = "INSERT INTO addresses(country_id) VALUES (?)";
        CrudUtil.execute(insertAddressSQL, address.getCountry().getId());
        ResultSet newAddressRst = CrudUtil.execute("SELECT * FROM addresses ORDER BY id DESC LIMIT 1");
        if (newAddressRst.next()) {
            roomDTO.getCustomer().setAddressId(newAddressRst.getLong("id"));
        }
        Document document = roomDTO.getDocument();
        String insertDocumentSQL = "INSERT INTO documents(type,value) VALUES (?,?)";
        CrudUtil.execute(insertDocumentSQL, document.getType().toStatus(), document.getValue());
        ResultSet newDocumentRst = CrudUtil.execute("SELECT * FROM documents ORDER BY id DESC LIMIT 1");
        if (newDocumentRst.next()) {
            roomDTO.getCustomer().setDocumentId(newDocumentRst.getLong("id"));
        }
//        ResultSet customerRst = CrudUtil.execute("SELECT * FROM customers WHERE id=?", roomDTO.getCustomer().getId());
//        if (customerRst.next()) {
//
//        } else {
//
//        }
        String createCustomerSQL = "INSERT INTO customers(first_name,last_name,phone_number,sex,document_id,address_id)" +
                " VALUES (?,?,?,?,?,?)";
        CrudUtil.execute(createCustomerSQL, roomDTO.getCustomer().getFirstName(),
                roomDTO.getCustomer().getLastName(),
                roomDTO.getCustomer().getPhoneNumber(),
                roomDTO.getCustomer().getGender().toStatus(),
                roomDTO.getCustomer().getDocumentId(),
                roomDTO.getCustomer().getAddressId());
        ResultSet newCustomerRst = CrudUtil.execute("SELECT * FROM customers ORDER BY id DESC LIMIT 1");
        if (newCustomerRst.next()) {
            roomDTO.getCustomer().setId(newCustomerRst.getLong("id"));
        }
        java.util.Date dt = roomDTO.getCheckIn().getCheckInAt();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        String createCheckInSQL = "INSERT INTO check_in(customer_id,room_id,check_in_at,status)" +
                " VALUES (?,?,?,?)";
        CrudUtil.execute(createCheckInSQL, roomDTO.getCustomer().getId(),
                roomDTO.getRoom().getId(),
                currentTime,
                1);
        String updateRoomStatusQuery = "UPDATE rooms SET rooms.status = ? WHERE rooms.id = ?";
        CrudUtil.execute(updateRoomStatusQuery, ROOM_STATUS_TYPE.OCCUPIED.toStatus(), roomDTO.getRoom().getId());
    }

    @Override
    public void checkOut(RoomDTO roomDTO) throws Exception {
        long assigneeId = MainApplication.getApplicationInstance().getAccount().getId();
        String createReceiptsSQL = "INSERT INTO receipts(assignee_id,check_in_id,created_at,price) VALUES (?,?,?,?)";
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        double totalPrice = roomDTO.getRoom().getPrice() * (double) roomDTO.getCheckIn().totalHours();
        CrudUtil.execute(createReceiptsSQL, assigneeId, roomDTO.getCheckIn().getId(), currentTime, totalPrice);

        String updateCheckInQuery = "UPDATE check_in SET check_in.status = ? WHERE check_in.id = ?";
        CrudUtil.execute(updateCheckInQuery, 2, roomDTO.getCheckIn().getId());
        String updateRoomStatusQuery = "UPDATE rooms SET rooms.status = ? WHERE rooms.id = ?";
        CrudUtil.execute(updateRoomStatusQuery, ROOM_STATUS_TYPE.AVAILABLE.toStatus(), roomDTO.getRoom().getId());
    }

    @Override
    public Boolean deleteRoom(Long id) throws Exception {
        String url = "DELETE FROM rooms WHERE id = ?";
        CrudUtil.execute(url, id);
        return true;
    }

    @Override
    public Boolean updateStatus(Long id, ROOM_STATUS_TYPE status) {
        String query = "UPDATE rooms SET rooms.status = ? WHERE rooms.id = ?";
        try {
            CrudUtil.execute(query, status.toStatus(), id);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public RoomDTO getById(long id) {
        String url = "SELECT rooms.id, rooms.name, rooms.status, rooms.number_of_beds, rooms.price, rooms.category_id" +
                ", categories.name AS category_name" +
                ", categories.code AS category_code" +
                " FROM rooms" +
                " INNER JOIN categories ON rooms.category_id = categories.id";
        try {
            ResultSet roomResultSet = CrudUtil.execute(url);
            if (roomResultSet.next()) {
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


                    String documentSql = "SELECT * FROM documents WHERE id = ?";
                    ResultSet documentResultSet = CrudUtil.execute(documentSql, customer.getDocumentId());
                    if (documentResultSet.next()) {
                        roomDTO.setDocument(new DocumentEntityToDocument().convert(documentResultSet));
                    }
                    String addressSql = "SELECT A.id, A.full_address, A.label, A.created_at, A.modified_at," +
                            "B.id AS country_id, B.name AS country_name," +
                            "C.id AS city_id, C.name AS city_name," +
                            "D.id AS district_id, D.name AS district_name" +
                            " FROM addresses AS A" +
                            " INNER JOIN cities AS C ON A.city_id = C.id" +
                            " INNER JOIN countries AS B ON A.country_id = B.id" +
                            " INNER JOIN districts AS D ON A.district_id = D.id" +
                            " WHERE A.id=?";
                    ResultSet addressResultSet = CrudUtil.execute(addressSql, customer.getAddressId());
                    if (addressResultSet.next()) {
                        roomDTO.setAddress(new AddressEntityToAddress().convert(addressResultSet));
                    }
                }
                return roomDTO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
