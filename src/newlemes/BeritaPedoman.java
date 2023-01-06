package newlemes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BeritaPedoman {
    String pattern = "yyyy-MM-dd HH:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = simpleDateFormat.format(new Date()); //this time now

    public void createTableBeritaPedoman() {
        String sql = "CREATE TABLE BeritaPedoman (" 
        + "BeritaPedomanID INTEGER NOT NULL UNIQUE, "
        + "Tipe TEXT NOT NULL, " 
        + "Judul TEXT NOT NULL, "
        + "Tanggal TEXT NOT NULL, "
        + "Isi TEXT NOT NULL, "
        + "PRIMARY KEY (BeritaPedomanID AUTOINCREMENT)"
        + ");";

        try (Connection conn = Connector.connect(); Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public void insertBeritaPedoman(String tipe, String judul, String isi) {
        String sql = "INSERT INTO BeritaPedoman (Tipe, Judul, Tanggal, Isi) VALUES(?,?,?,?)";

        try (Connection conn = Connector.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, tipe);
            pstmt.setString(2, judul);
            pstmt.setString(3, date);
            pstmt.setString(4, isi);

            pstmt.executeUpdate();
            
            System.out.println("\nArtikel Berhasil Diinputkan!");
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewBeritaPedoman(String pickBeritaPedoman) {
        String sql = "SELECT Judul, Tanggal, Isi FROM BeritaPedoman WHERE Tipe == ?";

        try (Connection conn = Connector.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, pickBeritaPedoman);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("Judul") + "\t" +
                                rs.getString("Tanggal") + "\t" +
                                rs.getString("isi"));
            }
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        BeritaPedoman beritaPedoman = new BeritaPedoman();
        beritaPedoman.createTableBeritaPedoman();
        // beritaPedoman.insertBeritaPedoman("berita", "Judul", "Tanggal", "Isi", "File");
        // beritaPedoman.viewBeritaPedoman("berita");
    }
}
