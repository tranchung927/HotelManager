module vn.edu.aptech.hotelmanager {
    requires javafx.controls;
    requires javafx.fxml;
            
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;
            
    opens vn.edu.aptech.hotelmanager to javafx.fxml;
    exports vn.edu.aptech.hotelmanager;
}