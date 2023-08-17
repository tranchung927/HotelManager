package vn.edu.aptech.hotelmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
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

    @FXML
    private AreaChart<?, ?> chartIncome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showChart();
    }

    public void showChart() {
        chartIncome.getData().clear();
        Date date = new Date();
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        String sql = "SELECT ci.check_out_at,ro.price as r_price, cis.price * cis.quantity AS ser_price " +
                "FROM check_in ci INNER JOIN rooms ro ON ci.room_id = ro.id " +
                "INNER JOIN check_in_services cis ON cis.check_in_id = ci.id WHERE ci.check_out_at = '" + date1 + "'";
        XYChart.Series chart = new XYChart.Series();
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                double roomPrice = resultSet.getDouble(2);
                double serPrice = resultSet.getDouble(3);

                double total = roomPrice + serPrice;

                chart.getData().add(new XYChart.Data<>(resultSet.getString(1), total));


            }
            chartIncome.getData().add(chart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}


