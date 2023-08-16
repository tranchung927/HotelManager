package vn.edu.aptech.hotelmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
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
        chartIncome.getData().clear();

    }
}


