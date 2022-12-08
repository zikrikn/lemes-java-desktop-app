package newlemes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    public static Connection connect() {
        String url = "jdbc:sqlite:db\\lemes.db";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}

