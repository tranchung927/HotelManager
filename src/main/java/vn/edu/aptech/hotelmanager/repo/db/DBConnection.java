package vn.edu.aptech.hotelmanager.repo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management","root","12345678");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance(){
        return (dbConnection==null)? dbConnection= new DBConnection() : dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }


}

