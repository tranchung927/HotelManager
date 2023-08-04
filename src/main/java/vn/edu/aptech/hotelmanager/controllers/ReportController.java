package vn.edu.aptech.hotelmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

//    @FXML
//    private TableColumn<Customer, String> col_checkInDate;
//
//    @FXML
//    private TableColumn<Customer, String> col_checkOutDate;
//
//    @FXML
//    private TableColumn<Customer, String> col_customerID;
//
//    @FXML
//    private TableColumn<Customer, String> col_employeeName;
//
//    @FXML
//    private TableColumn<Customer, String> col_nameCus;
//
//    @FXML
//    private TableColumn<Customer, String> col_nationId;
//
//    @FXML
//    private TableColumn<Customer, String> col_roomName;
//
//    @FXML
//    private TableColumn<Customer, String> col_totalPayment;
//
//    @FXML
//    private TableView<Customer> cus_table_view;
//
//    @FXML
//    private Button searchBtn;
//
//    @FXML
//    private TextField searchTxt;
//
//
//    private ObservableList<Customer> findAll() {
//        ObservableList<Customer> customers = FXCollections.observableArrayList();
//        ResultSet resultSet = null;
//        String url = "SELECT checkin.GuestID, GuestName,IdNumber,RoomName,receipt.CheckinDate,receipt.CheckoutDate,MustPay,FName,LName " +
//                "FROM receipt " +
//                "INNER JOIN checkin ON receipt.CheckinID = checkin.CheckinID " +
//                "INNER JOIN employee ON employee.EmployeeID = receipt.EmployeeID " +
//                "INNER JOIN room ON room.RoomId = checkin.RoomId " +
//                "INNER JOIN guest ON guest.GuestID = checkin.GuestID";
//
//        try {
//            Customer customer;
//            resultSet = CrudUtil.execute(url);
//
//            while (resultSet.next()) {
//                int id = Integer.parseInt(resultSet.getString(1));
//                String nameCus = resultSet.getString(2);
//                String nationId = resultSet.getString(3);
//                String room = resultSet.getString(4);
//                Date checkIn = resultSet.getDate(5);
//                Date checkOut = resultSet.getDate(6);
//                Double totalPay = Double.parseDouble(resultSet.getString(7));
//                String fName = resultSet.getString(8);
//                String lName = resultSet.getString(9);
//                String eName = fName + " " + lName;
//
//
//                customer = new Customer(id, nameCus, nationId, room, checkIn, checkOut, totalPay, eName);
//                customers.add(customer);
//
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return customers;
//    }
//
//    private ObservableList<Customer> customers;
//
//
//    public void showCus() {
//
//        try {
//
//            customers = findAll();
//
//            col_customerID.setCellValueFactory(new PropertyValueFactory<>("id"));
//            col_nameCus.setCellValueFactory(new PropertyValueFactory<>("name"));
//            col_nationId.setCellValueFactory(new PropertyValueFactory<>("nationId"));
//            col_roomName.setCellValueFactory(new PropertyValueFactory<>("room"));
//            col_checkInDate.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
//            col_checkOutDate.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
//            col_totalPayment.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));
//            col_employeeName.setCellValueFactory(new PropertyValueFactory<>("employee"));
//
//            cus_table_view.setItems(customers);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
//    public void searchCus() {
//        FilteredList<Customer> filteredList = new FilteredList<>(customers, e -> true);
//        searchTxt.textProperty().addListener((observableValue, oldValue , newValue) -> {
//            filteredList.setPredicate(customer -> {
//                if (newValue == null && newValue.isEmpty()) {
//                    return true;
//                }
//                String searchKey = newValue.toLowerCase();
//                if (String.valueOf(customer.getId()).toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if ((customer.getName()).toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (customer.getNationId().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (customer.getRoom().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (customer.getCheckIn().toString().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (customer.getCheckOut().toString().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (customer.getTotalPayment().toString().contains(searchKey)) {
//                    return true;
//                } else if (customer.getEmployee().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            });
//        });
//
//        SortedList<Customer> customerSortedList = new SortedList<>(filteredList);
//
//        customerSortedList.comparatorProperty().bind(cus_table_view.comparatorProperty());
//        cus_table_view.setItems(customerSortedList);
//
//    }
//

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        showCus();
//        searchCus();
    }
}


