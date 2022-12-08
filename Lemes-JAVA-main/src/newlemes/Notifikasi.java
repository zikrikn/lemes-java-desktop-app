package newlemes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Notifikasi {
    //Satukan lewat disini jangan dipanggil satu-satu
    //Untuk Restock masuk ke sini
    //Solved
    Connection conn = Connector.connect();
    String pattern = "yyyy-MM-dd HH:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = simpleDateFormat.format(new Date()); //this time now

    public void createTableNotifikasi() {
        String sql = "CREATE TABLE notifikasiIn (" +
            "notifikasiID	TEXT NOT NULL," +
            "userID	        TEXT NOT NULL," +
            "tipe	        TEXT NOT NULL," +
            "waktuMasuk	    TEXT NOT NULL," +
            "waktuKeluar	TEXT NOT NULL," +
            "waktuKeluar2	TEXT NOT NULL," +
            "messages       TEXT NOT NULL)";

        try (Connection conn = Connector.connect(); 
        Statement stmt = conn.createStatement()){
            stmt.executeUpdate(sql);
            System.out.println("Table NotifikasiIn Berhasil Dibuat!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //How about make new notifications
    public void notifikasiIn(String tipe){
        String sql = "INSERT INTO notifikasiIn (notifikasiID, userID, tipe, waktuMasuk, waktuKeluar, waktuKeluar2, messages) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = Connector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "18281");
            pstmt.setString(2, "65ggas");
            pstmt.setString(3, tipe);
            pstmt.setString(4, date);
            pstmt.setString(5, "2023-01-07 14:02");
            pstmt.setString(6, "2021-01-07 14:02");
            
            if (tipe == "Harian"){
                pstmt.setString(7, "Notifikasi harian aktif! Kamu akan mendapatkan notifikasi 3 kali sehari sampai tanggal 23 September 2022.");
            }else if (tipe == "Panen"){
                pstmt.setString(7, "Notifikasi panen aktif! Kamu akan mendapatkan notifikasi mulai dari H-3 panen.");
            }else if (tipe == "Restock"){
                pstmt.setString(7, "Notifikasi restock aktif! Kamu akan mendapatkan notifikasi H-2 sebelum perkiraan restock.");
            }

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void notifikasiList() {
        //We have to create a new table to store the notification data that the final one
        //This one for all of notifications
        String sql = "SELECT * FROM notifikasiIn WHERE waktuMasuk >= ?";

        try (Connection conn = Connector.connect();
        PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, date);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("tipe"));
                System.out.println(rs.getString("waktuKeluar"));
                System.out.println(rs.getString("messages"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Notifikasi notifikasi = new Notifikasi();
        notifikasi.createTableNotifikasi();
    }
}
