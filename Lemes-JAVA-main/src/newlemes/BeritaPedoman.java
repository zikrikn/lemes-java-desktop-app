package newlemes;

import java.sql.*;

public class BeritaPedoman {

    public void createTableBeritaPedoman() {
        String sql = "CREATE TABLE BeritaPedoman (" 
            + "BeritaPedomanID	INTEGER NOT NULL UNIQUE, "
            + "Tipe     TEXT NOT NULL, " //Ini diisi hanya dengan "berita" atau "pedoman"
            + "Judul	TEXT NOT NULL, "
            + "Tanggal	TEXT NOT NULL, "
            + "Isi	TEXT NOT NULL, "
            + "File	TEXT, "
            + "PRIMARY KEY(BeritaPedomanID)"
            + ");";

        try (Connection conn = Connector.connect(); Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public void insertBeritaPedoman(int BeritaPedomanID, String tipe, String judul, String tanggal, String isi, String file) {
        String sql = "INSERT INTO BeritaPedoman (BeritaPedomanID, Tipe, Judul, Tanggal, Isi, File) VALUES(?,?,?,?,?,?)";

        try (Connection conn = Connector.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, BeritaPedomanID);
            pstmt.setString(2, tipe);
            pstmt.setString(3, judul);
            pstmt.setString(4, tanggal);
            pstmt.setString(5, isi);
            pstmt.setString(6, file);

            pstmt.executeUpdate();
            
            System.out.println("\nArtikel Berhasil Diinputkan!");
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewBeritaPedoman(String pickBeritaPedoman) {
        String sql = "SELECT Judul, Tanggal, Isi, File FROM BeritaPedoman WHERE Tipe == ?";

        try (Connection conn = Connector.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, pickBeritaPedoman);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("Judul") + "\t" +
                                rs.getString("Tanggal") + "\t" +
                                rs.getString("isi") + "\t" +
                                rs.getString("File"));
            }
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
}
