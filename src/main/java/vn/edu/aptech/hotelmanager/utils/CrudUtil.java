package vn.edu.aptech.hotelmanager.utils;

import vn.edu.aptech.hotelmanager.repo.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CrudUtil {

    public static <T> T execute(String sql, Object... params) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        int i = 0;
        for (Object param : params) {
            i++;
            pstm.setObject(i, param);
        }
        if (sql.startsWith("SELECT")) {
            return (T) pstm.executeQuery();     // ResultSet
        }
        return (T) ((Boolean) (pstm.executeUpdate() > 0));    // boolean
    }

}
