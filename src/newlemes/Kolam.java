package newlemes;

import java.sql.*;

public class Kolam {
    private Notifikasi notifikasi = new Notifikasi();

    //Nanti buat login untuk mengklasifikasi hasilnya berdasarkan user
    public void createTableKolam() {
        String sql = "CREATE TABLE IF NOT EXISTS kolam (" +
        "username TEXT NOT NULL, " +
        "namakolam TEXT NOT NULL UNIQUE PRIMARY KEY," +
        "jumlahlele INTEGER NOT NULL, " +
        "beratlele INTEGER NOT NULL, " +
        "tglTebarBenih TEXT NOT NULL," +
        "takaranPakan INTEGER," +
        "jumlahPakan INTEGER," +
        "restockPakan INTEGER," +
        "CONSTRAINT kolam_fk1 FOREIGN KEY (username) REFERENCES users(username));";

        try (Connection conn = Connector.connect(); 
        Statement stmt = conn.createStatement()){
            stmt.execute(sql);

            System.out.println("Sukses Membuat Table Kolam");
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void inputDataKolam(String username, String namakolam, int beratlele, int jumlahlele, String tglTebarBenih) {
        String sql = "INSERT INTO kolam (username, namakolam, beratlele, jumlahlele, tglTebarBenih) VALUES(?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = Connector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, username);
            pstmt.setString(2, namakolam);
            pstmt.setInt(3, beratlele);
            pstmt.setInt(4, jumlahlele);
            pstmt.setString(5, tglTebarBenih);

            // Harus membuat logic untuk menghitung takaran pakan
            pstmt.executeUpdate();

            Connector.disconnect();

            // Untuk menambahkan data ke table db notifikasi, untuk harian dan panen
            notifikasi.notifikasiIn(username, "Harian");
            notifikasi.notifikasiIn(username, "Panen");

            System.out.println("Data Kolam Baru Berhasil Diinputkan!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayAllKolam(String username) {
        String sql = "SELECT * FROM kolam WHERE username == ?";

        try (Connection conn = Connector.connect();
        PreparedStatement pstmt  = conn.prepareStatement(sql)){;
            pstmt.setString(1, username);
            ResultSet rs    = pstmt.executeQuery();

            System.out.println("Nama Kolam " + " Berat Lele" + " Jumlah Lele" + " Tanggal Tebar Benih");
            while (rs.next()){
                System.out.println(rs.getString("namakolam") + "\t\t" + 
                rs.getInt("beratlele") + "\t" +
                rs.getInt("jumlahlele") + "\t\t" + 
                rs.getString("tglTebarBenih"));
            }
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchKolam(String username, String namaKolam) {
        String sql = "SELECT * FROM kolam WHERE namakolam == ? and username == ?";

        try (Connection conn = Connector.connect(); 
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, namaKolam);
            pstmt.setString(2, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                System.out.println(rs.getString("namakolam") + "\t" + 
                rs.getInt("beratlele") + "\t" + 
                rs.getInt("jumlahlele") + "\t" +
                rs.getString("tglTebarBenih"));
            }else {
                System.out.println("Data Kolam Terkait Tidak Ditemukan!");
            }
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public void RestockPakan(String username, String namaKolam, int jumlahPakan) {
        String sql = "UPDATE kolam SET jumlahPakan = ?, restockPakan = ? WHERE namakolam = ? and username = ?";
        // UPDATE kolam SET jumlahPakan = 12121, restockPakan = 12122 WHERE namakolam == "hi";
        try (Connection conn = Connector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            // Seperti IMPAL this is just update the table
            pstmt.setInt(1, jumlahPakan);
            // We have to make a logic to calculate in this program
            pstmt.setInt(2, 7171);
            pstmt.setString(3, namaKolam);
            pstmt.setString(4, username);

            // Masukan logic untuk restock
            // Untuk menambahkan data ke table db notifikasi
            pstmt.executeUpdate();
            notifikasi.notifikasiIn(username, "Restock");
            System.out.println("Restock berhasil diupdate!");
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Kolam newKolam = new Kolam();

        // Masih belum bisa diupdate
        // newKolam.createTableKolam();
        newKolam.RestockPakan("fikri", "kolam2", 28282);
        // newKolam.displayAllKolam("fikri");
        // newKolam.inputDataKolam("fikri", "kolam2", 25, 1000, "2020-12-12");
    }
}
