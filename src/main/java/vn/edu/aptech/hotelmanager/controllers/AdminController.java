package vn.edu.aptech.hotelmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import vn.edu.aptech.hotelmanager.repo.db.DBConnection;
import vn.edu.aptech.hotelmanager.repo.entity.RoomEntity;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    /**************************************************
     * Tab - room
     **************************************************/

    @FXML
    private Button addBtn;

    @FXML
    private ComboBox<?> bedType;

    @FXML
    private Button clearBtn;

    @FXML
    private TableView<Room> tableView;

    @FXML
    private TableColumn<Room, String> col_bedType;

    @FXML
    private TableColumn<Room, String> col_nob;

    @FXML
    private TableColumn<Room, String> col_price;

    @FXML
    private TableColumn<Room, String> col_roomName;

    @FXML
    private TableColumn<Room, String> col_roomType;

    @FXML
    private TableColumn<Room, String> col_status;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField nob;

    @FXML
    private TextField price;

    @FXML
    private TextField roomName;

    @FXML
    private ComboBox<?> roomType;

    @FXML
    private ComboBox<?> status;

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<RoomEntity, String> col_roomID;

    @FXML
    private TextField roomID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addValueRoomType();
        addValueStatus();
        addValueBedType();

        addValuePositions();

        showRoom();
        showEmployee();

    }

    // Add value to combo box room types
    private String[] roomTypes = {"Standard", "Suite"};

    @FXML
    public void addValueRoomType() {


        List<String> listRoomType = new ArrayList<>();

        for (String item : roomTypes) {
            listRoomType.add(item);

        }
        ObservableList list = FXCollections.observableList(listRoomType);
        roomType.setItems(list);
    }


    // Add value to combo box bed type
    private String[] bedTypes = {"Queen", "King", "FullBed"};

    @FXML
    public void addValueBedType() {
        List<String> listBedType = new ArrayList<>();

        for (String item : bedTypes) {
            listBedType.add(item);

        }
        ObservableList list = FXCollections.observableList(listBedType);
        bedType.setItems(list);
    }

    // Add value to combo box status
    private String[] statuss = {"Available", "Occupied", "Repair", "Dirty", "Reserve"};

    @FXML
    public void addValueStatus() {
        List<String> statusList = new ArrayList<>();

        for (String item : statuss) {
            statusList.add(item);

        }
        ObservableList list = FXCollections.observableList(statusList);
        status.setItems(list);
    }

    //////////////////////////////////////////

    private Connection connection;
    private PreparedStatement prepared;
    private ResultSet result;

    public void addRoom() {
        String url = "INSERT INTO room(RoomId" +
                ", RoomName" +
                ", RoomTypeID" +
                ", BedTypeID" +
                ", Price" +
                ", StatusID," +
                " NumberOfBed) " +
                "VALUES (?,?,?,?,?,?,?)";


        connection = DBConnection.getInstance().getConnection();
        try {
            String idRoom = roomID.getText();

            String nameRoom = roomName.getText();

            String typeRoom = (String) roomType.getSelectionModel().getSelectedItem();
            int typeID = getRoomTypeID(typeRoom);

            String type = (String) bedType.getSelectionModel().getSelectedItem();
            int bedID = getBedTypeID(type);

            String priceRoom = price.getText();

            String statusId = (String) status.getSelectionModel().getSelectedItem();
            int statusID = getStatusID(statusId);


            String numberOfBed = nob.getText();


            Alert alert;
            if (nameRoom.isEmpty()
                    || idRoom.isEmpty()
                    || String.valueOf(typeID).isEmpty()
                    || String.valueOf(bedID).isEmpty()
                    || priceRoom.isEmpty()
                    || String.valueOf(statusID).isEmpty()
                    || numberOfBed.isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                String check = "SELECT RoomID, RoomName FROM room WHERE RoomId = '" + idRoom + "' OR RoomName='" + nameRoom + "'";
                ResultSet result = CrudUtil.execute(check);
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("RoomID or Room Name has been already existed");
                    alert.showAndWait();

                } else {

                    prepared = connection.prepareStatement(url);
                    prepared.setString(1, idRoom);
                    prepared.setString(2, nameRoom);
                    prepared.setString(3, String.valueOf(typeID));
                    prepared.setString(4, String.valueOf(bedID));
                    prepared.setString(5, priceRoom);
                    prepared.setString(6, String.valueOf(statusID));
                    prepared.setString(7, numberOfBed);

                    prepared.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Add Successfully");
                    alert.showAndWait();

                    showRoom();
                    /////////////////////////////////////
                    clearRoom();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    ////////////////////////////////////////////////////////// get room type ID
    public int getRoomTypeID(String item) {

        String url = "SELECT * FROM roomtype WHERE TypeName = '" + item + "'";


        int roomTypeId = 0;
        try {
            ResultSet result = CrudUtil.execute(url);

            while (result.next()) {

                roomTypeId = result.getInt(1);


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return roomTypeId;
    }


    ////////////////////////////////////// get  bed type ID
    public int getBedTypeID(String item) {

        String url = "SELECT * FROM bedtype WHERE TypeName = '" + item + "'";

        int bedTypeId = 0;
        try {
            ResultSet result = CrudUtil.execute(url);

            while (result.next()) {

                bedTypeId = result.getInt(1);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bedTypeId;
    }


    ////////////////////////////////////// get  status ID
    public int getStatusID(String item) {

        String url = "SELECT * FROM statustype WHERE StatusName = '" + item + "'";
        int statusID = 0;

        try {
            ResultSet rst = CrudUtil.execute(url);

            while (rst.next()) {

                statusID = rst.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return statusID;
    }


    public void clearRoom() {

        roomID.setText("");
        roomName.setText("");
        roomType.getSelectionModel().clearSelection();
        bedType.getSelectionModel().clearSelection();
        price.setText("");
        status.getSelectionModel().clearSelection();
        nob.setText("");

    }


    public String getRoomType(String item) {

        String url = "SELECT * FROM roomtype WHERE TypeID = '" + item + "'";


        String roomType = "";
        try {
            ResultSet result = CrudUtil.execute(url);

            while (result.next()) {

                roomType = result.getString(2);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return roomType;
    }

    public String getBedType(String item) {

        String url = "SELECT * FROM bedtype WHERE BedTypeID = '" + item + "'";

        String bedType = "";
        try {
            ResultSet result = CrudUtil.execute(url);

            while (result.next()) {

                bedType = result.getString(2);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bedType;
    }

    public String getStatus(String item) {

        String url = "SELECT * FROM statustype WHERE StatusTypeID = '" + item + "'";
        String status = "";

        try {
            ResultSet rst = CrudUtil.execute(url);

            while (rst.next()) {

                status = rst.getString(2);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public ObservableList<Room> findAll() {
        ObservableList<Room> roomList = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        String url = "SELECT * FROM room";

        try {
            Room room;
            resultSet = CrudUtil.execute(url);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String type = getRoomType(resultSet.getString(3));
                String btype = getBedType(resultSet.getString(4));
                Double price = resultSet.getDouble(5);
                String status = getStatus(resultSet.getString(6));
                int number = resultSet.getInt(7);


                room = new Room(id, name, type, btype, price, status, number);
                roomList.add(room);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return roomList;
    }

    private ObservableList<Room> rooms;

    public void showRoom() {
        try {
            rooms = findAll();

            col_roomID.setCellValueFactory(new PropertyValueFactory<>("roomId"));
            col_roomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
            col_roomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
            col_bedType.setCellValueFactory(new PropertyValueFactory<>("bedType"));
            col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
            col_nob.setCellValueFactory(new PropertyValueFactory<>("numberOfBed"));

            tableView.setItems(rooms);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void selectRowRoom() {
        Room room = tableView.getSelectionModel().getSelectedItem();
        int num = tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        roomID.setText(String.valueOf(room.getRoomId()));
        roomName.setText(String.valueOf(room.getRoomName()));
        price.setText(String.valueOf(room.getPrice()));
        nob.setText(String.valueOf(room.getNumberOfBed()));

    }


    public void updateRoom() {
        String idRoomUpdate = roomID.getText();
        String nameRoomUpdate = roomName.getText();
        String typeRoomUpdate = (String) roomType.getSelectionModel().getSelectedItem();
        int typeRoomUpInt = getRoomTypeID(typeRoomUpdate);
        String typeBedUpdate = (String) bedType.getSelectionModel().getSelectedItem();
        int typeBedUpInt = getBedTypeID(typeBedUpdate);
        String priceRoomUpdate = price.getText();
        String statusIdUpdate = (String) status.getSelectionModel().getSelectedItem();
        int statusIDInt = getStatusID(statusIdUpdate);
        String numberOfBedUpdate = nob.getText();


        String url = "UPDATE room SET  " +
                "RoomName = '" + nameRoomUpdate + "'" +
                ", RoomTypeID = '" + typeRoomUpInt + "'" +
                ", BedTypeID = '" + typeBedUpInt + "'" +
                ", Price = '" + priceRoomUpdate + "'" +
                ", StatusID = '" + statusIDInt + "'" +
                ", NumberOfBed = '" + numberOfBedUpdate + "'" +
                " WHERE RoomId = '" + idRoomUpdate + "' ";

        connection = DBConnection.getInstance().getConnection();

        try {

            Alert alert;
            if (idRoomUpdate.isEmpty()
                    || nameRoomUpdate.isEmpty()
                    || typeRoomUpdate.isEmpty()
                    || typeBedUpdate.isEmpty()
                    || priceRoomUpdate.isEmpty()
                    || statusIdUpdate.isEmpty()
                    || numberOfBedUpdate.isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select room !");
                alert.showAndWait();

            } else {
                prepared = connection.prepareStatement(url);

                prepared.executeUpdate();


                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Update Successfully !");
                alert.showAndWait();
                ///////////////////////
                showRoom();
                /////////////////////
                clearRoom();

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRoom() {
        String idRoomDelete = roomID.getText();
        String url = "DELETE FROM room WHERE RoomId = '" + idRoomDelete + "'";

        connection = DBConnection.getInstance().getConnection();
        try {
            Alert alert;

            if (idRoomDelete.isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select room !");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete room ?");

                Optional<ButtonType> optional = alert.showAndWait();

                if (optional.get().equals(ButtonType.OK)) {
                    prepared = connection.prepareStatement(url);
                    prepared.executeUpdate();

                    showRoom();

                    clearRoom();
                } else {
                    return;
                }


                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Delete Successfully !");
                alert.showAndWait();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**************************************************
     * Tab- Employee
     **************************************************/


    @FXML
    private Button addE;

    @FXML
    private TextField address;

    @FXML
    private TextField age;

    @FXML
    private Button clearE;

    @FXML
    private TableColumn<Employee, String> col_address;

    @FXML
    private TableColumn<Employee, String> col_age;

    @FXML
    private TableColumn<Employee, String> col_Name;

    @FXML
    private TableColumn<Employee, String> col_id;


    @FXML
    private TableColumn<Employee, String> col_pass;

    @FXML
    private TableColumn<Employee, String> col_phone;

    @FXML
    private TableColumn<Employee, String> col_position;

    @FXML
    private TableColumn<Employee, String> col_uName;

    @FXML
    private Button deleteE;

    @FXML
    private TableView<Employee> e_table_view;

    @FXML
    private TextField employeeId;

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField pass;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<?> position;

    @FXML
    private TextField uName;

    @FXML
    private Button updateE;


    private String[] positions = {"Reception", "Housekeeping", "Manager", "Security"};

    @FXML
    public void addValuePositions() {


        List<String> listPositions = new ArrayList<>();

        for (String item : positions) {
            listPositions.add(item);

        }
        ObservableList list = FXCollections.observableList(listPositions);
        position.setItems(list);
    }


    public int getPositionID(String item) {

        String url = "SELECT * FROM positions WHERE PositionName = '" + item + "'";
        int positionI = 0;

        try {
            ResultSet rst = CrudUtil.execute(url);

            while (rst.next()) {

                positionI = rst.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return positionI;
    }

    public String getPosition(String item) {

        String url = "SELECT * FROM positions WHERE PositionID = '" + item + "'";
        String position = "";

        try {
            ResultSet rst = CrudUtil.execute(url);

            while (rst.next()) {

                position = rst.getString(2);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return position;
    }

    public void addEmployee() {
        String url = "INSERT INTO employee(EmployeeID" +
                ", FName" +
                ", LName" +
                ", age" +
                ", Address" +
                ", Phone" +
                ", PositionID" +
                ", username" +
                ", password )" +
                "VALUES (?,?,?,?,?,?,?,?,?)";


        connection = DBConnection.getInstance().getConnection();
        try {
            String idEmployee = employeeId.getText();

            String fname = fName.getText().toLowerCase();
            String lname = lName.getText().toLowerCase();
            String ages = age.getText();
            String addresss = address.getText().toLowerCase();
            String phones = phone.getText();
            String positionss = (String) position.getSelectionModel().getSelectedItem();
            int posi = getPositionID(positionss);
            String uname = uName.getText().toLowerCase();
            String pas = pass.getText().toLowerCase();


            Alert alert;
            if (idEmployee == null
                    || fname == null
                    || lname == null
                    || ages == null
                    || addresss == null
                    || phones == null
                    || positionss == null
                    || uname == null
                    || pas == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                String check = "SELECT username FROM employee WHERE EmployeeID = '" + idEmployee + "'";
                ResultSet result = CrudUtil.execute(check);
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("User Name or ID has been already existed");
                    alert.showAndWait();

                } else {

                    prepared = connection.prepareStatement(url);
                    prepared.setString(1, idEmployee);
                    prepared.setString(2, fname);
                    prepared.setString(3, lname);
                    prepared.setString(4, ages);
                    prepared.setString(5, addresss);
                    prepared.setString(6, phones);
                    prepared.setString(7, String.valueOf(posi));
                    prepared.setString(8, uname);
                    prepared.setString(9, pas);


                    prepared.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Add Successfully");
                    alert.showAndWait();

                    showEmployee();

                    clearEmployee();

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearEmployee() {
        employeeId.setText("");
        fName.setText("");
        lName.setText("");
        age.setText("");
        address.setText("");
        phone.setText("");
        position.getSelectionModel().clearSelection();
        uName.setText("");
        pass.setText("");

    }

    public ObservableList<Employee> findAllEmployee() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        String url = "SELECT * FROM employee";

        try {
            Employee employee;
            resultSet = CrudUtil.execute(url);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String nameFirst = resultSet.getString(2);
                String nameLast = resultSet.getString(3);
                String name = nameFirst +" "+ nameLast;
                int age = resultSet.getInt(4);
                String address = resultSet.getString(5);
                int phone = resultSet.getInt(6);
                String position = getPosition(resultSet.getString(7));
                String username = resultSet.getString(8);
                String password = resultSet.getString(9);


                employee = new Employee(id, name, age, address, phone, position, username, password);
                employees.add(employee);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    private ObservableList<Employee> employees;

    public void showEmployee() {
        try {
            employees = findAllEmployee();

            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
            col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
            col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
            col_uName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            col_pass.setCellValueFactory(new PropertyValueFactory<>("passWord"));

            e_table_view.setItems(employees);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void selectRowEmployee() {
        Employee employee = e_table_view.getSelectionModel().getSelectedItem();
        int num = e_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        employeeId.setText(String.valueOf(employee.getId()));
        fName.setText(String.valueOf(employee.getFirstName(employee.getName())));
        lName.setText(String.valueOf(employee.getLastName(employee.getName())));
        age.setText(String.valueOf(employee.getAge()));
        address.setText(String.valueOf(employee.getAddress()));
        phone.setText(String.valueOf(employee.getPhone()));
        uName.setText(String.valueOf(employee.getUserName()));
        pass.setText(String.valueOf(employee.getPassWord()));

    }

    public void updateEmployee() {
        String idEmployeeUp = employeeId.getText();
        String fNameEmployeeUp = fName.getText();
        String lNameEmployeeUp = lName.getText();
        String ageUp = age.getText();
        String addUp = address.getText();
        String phoneUp = phone.getText();
        String positionUp = (String) position.getSelectionModel().getSelectedItem();
        int positionUpInt = getPositionID(positionUp);
        String uNameUp = uName.getText();
        String passUp = pass.getText();


        String url = "UPDATE employee SET  " +
                "FName = '" + fNameEmployeeUp + "'" +
                ", LName = '" + lNameEmployeeUp + "'" +
                ", age = '" + ageUp + "'" +
                ", Address = '" + addUp + "'" +
                ", Phone = '" + phoneUp + "'" +
                ", PositionId = '" + positionUpInt + "'" +
                ", username = '" + uNameUp + "'" +
                ", password = '" + passUp + "'" +
                " WHERE EmployeeID = '" + idEmployeeUp + "' ";

        connection = DBConnection.getInstance().getConnection();

        try {

            Alert alert;
            if (idEmployeeUp == null
                    || fNameEmployeeUp == null
                    || lNameEmployeeUp == null
                    || ageUp == null
                    || addUp == null
                    || phoneUp == null
                    || positionUpInt == 0
                    || uNameUp == null
                    || passUp == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select Employee !");
                alert.showAndWait();

            } else {
                prepared = connection.prepareStatement(url);

                prepared.executeUpdate();


                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Update Successfully !");
                alert.showAndWait();
                ///////////////////////
                showEmployee();
                /////////////////////
                clearEmployee();

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmployee() {
        String idEmployeeDelete = employeeId.getText();
        String url = "DELETE FROM employee WHERE EmployeeID = '" + idEmployeeDelete + "'";

        connection = DBConnection.getInstance().getConnection();
        try {
            Alert alert;

            if (idEmployeeDelete == null) {


                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select Employee !");
                alert.showAndWait();


            } else {


                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete room ?");

                Optional<ButtonType> optional = alert.showAndWait();

                if (optional.get().equals(ButtonType.OK)) {
                    prepared = connection.prepareStatement(url);
                    prepared.executeUpdate();

                    showEmployee();

                    clearEmployee();
                } else {
                    return;
                }

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Delete Successfully !");
                alert.showAndWait();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
