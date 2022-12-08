package newlemes;

import java.sql.*;

public class Kolam {
    private Notifikasi notifikasi = new Notifikasi();

    public void createTableKolam() {
        String sql = "CREATE TABLE IF NOT EXISTS kolam (" +
        "namakolam	TEXT NOT NULL UNIQUE PRIMARY KEY," +
        "jumlahlele	INTEGER NOT NULL, " +
        "beratlele	INTEGER NOT NULL, " +
        "tglTebarBenih	TEXT NOT NULL," + 
        "takaranPakan	INTEGER," + 
        "jumlahPakan	INTEGER," +
        "restockPakan	INTEGER)";

        try (Connection conn = Connector.connect(); 
        Statement stmt = conn.createStatement()){
            stmt.execute(sql);

            System.out.println("Sukses Membuat Table Kolam");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void inputDataKolam(String namakolam, int beratlele, int jumlahlele, String tglTebarBenih) {
        String sql = "INSERT INTO kolam (namakolam, beratlele, jumlahlele, tglTebarBenih) VALUES(?,?,?,?)";
        Connection conn = null;
        try {
            conn = Connector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, namakolam);
            pstmt.setInt(2, beratlele);
            pstmt.setInt(3, jumlahlele);
            pstmt.setString(4, tglTebarBenih);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            // Untuk menambahkan data ke table db notifikasi, untuk harian dan panen
            notifikasi.notifikasiIn("Harian");
            notifikasi.notifikasiIn("Panen");

            System.out.println("Data Kolam Baru Berhasil Diinputkan!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayAllKolam() {
        String sql = "SELECT * FROM kolam";

        try (Connection conn = Connector.connect();
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql)){;

            while (rs.next()){
                System.out.println(rs.getString("namakolam") + "\t" + 
                rs.getInt("beratlele") + "\t" +
                rs.getInt("jumlahlele") + "\t" + 
                rs.getString("tglTebarBenih"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchKolam(String namaKolam) {
        String sql = "SELECT * FROM kolam WHERE namakolam == ?";

        try (Connection conn = Connector.connect(); 
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, namaKolam);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                System.out.println(rs.getString("namakolam") + "\t" + 
                rs.getInt("beratlele") + "\t" + 
                rs.getInt("jumlahlele") + "\t" +
                rs.getString("tglTebarBenih"));
            }else {
                System.out.println("Data Kolam Terkait Tidak Ditemukan!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public void RestockPakan(int jumlahPakan) {
        String sql = "INSERT INTO kolam (jumlahPakan, restockPakan) VALUES(?,?)";
        try (Connection conn = Connector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            // Seperti IMPAL this is just update the table
            pstmt.setInt(1, jumlahPakan);
            pstmt.setInt(2, 7171);

            // Masukan logic untuk restock
            // Untuk menambahkan data ke table db notifikasi
            notifikasi.notifikasiIn("Restock");;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
