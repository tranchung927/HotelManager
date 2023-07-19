module vn.edu.aptech.hotelmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires VirtualizedFX;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires fr.brouillard.oss.cssfx;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    exports vn.edu.aptech.hotelmanager.controllers;
    opens vn.edu.aptech.hotelmanager.controllers to javafx.fxml;
    exports vn.edu.aptech.hotelmanager;
    opens vn.edu.aptech.hotelmanager to javafx.fxml;
    exports vn.edu.aptech.hotelmanager.utils;
    opens vn.edu.aptech.hotelmanager.utils to javafx.fxml;
}