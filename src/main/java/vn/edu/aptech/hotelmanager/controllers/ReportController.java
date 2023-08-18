package vn.edu.aptech.hotelmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    private int count = 0;
    private double sum = 0;
    private double roomTotal = 0;
    private double cisPrice = 0;
    private double cisQuantity = 0;

    @FXML
    private Label bookToday;

    @FXML
    private AreaChart<?, ?> chartIncome;

    @FXML
    private Label incomeToday;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        displayBookToday();
//        showIncomeToday();
//        showChart();
    }

//    public void countBookToday() {
//        Date date = new Date();
//        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//        String sql = "SELECT COUNT(id) FROM check_in WHERE check_in_at = '" + sqlDate + "'";
//
//        count = 0;
//        try {
//            ResultSet resultSet = CrudUtil.execute(sql);
//            while (resultSet.next()) {
//                count = resultSet.getInt("COUNT(id)");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    public void displayBookToday() {
//        countBookToday();
//        bookToday.setText(String.valueOf(count));
//    }
//
//    public void incomeToday() {
//        Date date = new Date();
//        java.sql.Date sqldate1 = new java.sql.Date(date.getTime());
//        sum = 0;
//        String sql = "SELECT SUM(ro.price),SUM(cis.price),SUM(cis.quantity)" +
//                "FROM check_in ci " +
//                "INNER JOIN rooms ro ON ci.room_id = ro.id " +
//                "INNER JOIN check_in_services cis ON cis.check_in_id = ci.id " +
//                "WHERE ci.check_out_at = '" + sqldate1 + "'";
//
//
//        try {
//            ResultSet resultSet = CrudUtil.execute(sql);
//            while (resultSet.next()) {
//                roomTotal = resultSet.getDouble("SUM(ro.price)");
//                cisPrice = resultSet.getDouble("SUM(cis.price)");
//                cisQuantity = resultSet.getDouble("SUM(cis.quantity)");
//
//
//            }
//            sum = roomTotal + cisPrice * cisQuantity;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public void showIncomeToday(){
//        incomeToday();
//        incomeToday.setText(String.valueOf(sum));
//    }
//
//
//
//
//    public void showChart() {
//        chartIncome.getData().clear();
//        Date date = new Date();
//        java.sql.Date date1 = new java.sql.Date(date.getTime());
//        sum = 0;
//        String sql = "SELECT ci.check_out_at,SUM(ro.price),SUM(cis.price),SUM(cis.quantity) " +
//                "FROM check_in ci INNER JOIN rooms ro ON ci.room_id = ro.id " +
//                "INNER JOIN check_in_services cis ON cis.check_in_id = ci.id " +
//                "WHERE ci.check_out_at = '" + date1 + "'";
//        XYChart.Series chart = new XYChart.Series();
//        try {
//            ResultSet resultSet = CrudUtil.execute(sql);
//            while (resultSet.next()) {
//                roomTotal = resultSet.getDouble("SUM(ro.price)");
//                cisPrice = resultSet.getDouble("SUM(cis.price)");
//                cisQuantity = resultSet.getDouble("SUM(cis.quantity)");
//
//                sum = roomTotal + cisPrice * cisQuantity;
//
//                chart.getData().add(new XYChart.Data<>(resultSet.getString(1), sum));
//
//
//            }
//            chartIncome.getData().add(chart);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}


