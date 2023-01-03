package newlemes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.SchedulerException;

public class Auth{
    MenuUser menuUser = new MenuUser();
    MenuAdmin menuAdmin = new MenuAdmin();
    private String titipUsername;

    public String getTitipUsername() {
        return titipUsername;
    }

    public void setTitipUsername(String titipUsername) {
        this.titipUsername = titipUsername;
    }

    //Membuat Table User - Useless if we already have the table in database
    public void createTableUsers() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
            "username	TEXT NOT NULL UNIQUE PRIMARY KEY, " +
            "name	    TEXT NOT NULL, " +
            "password	TEXT NOT NULL" + 
            ");";
        
        try (Connection conn = Connector.connect(); 
        Statement stmt = conn.createStatement()){
            stmt.execute(sql);

            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registrasi(String username, String name, String password) {
        String sql = "INSERT INTO users (username, name, password) VALUES(?,?,?)";

        try (Connection conn = Connector.connect(); 
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.executeUpdate();

            System.out.println("Registrasi Berhasil!");
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void validasiLogin(String username, String password) throws SchedulerException{
        setTitipUsername(username);
        if ((username.equals("admin")) && (password.equals("admin"))){
            System.out.println("Admin Berhasil Login!");
            menuAdmin.pilihMenu();
        }else {
            String sql = "SELECT username, name, password FROM users WHERE username == ? AND password == ?";
            
            try {;
                Connection conn = Connector.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();

                if (rs.getString("username") != null && rs.getString("password") != null) {
                    System.out.println(rs.getString("name")+" Berhasil Login!");
                    Connector.disconnect();
                    menuUser.pilihMenu(username);
                }else{
                    Connector.disconnect();
                    System.out.println("User Tidak Terdaftar!");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}