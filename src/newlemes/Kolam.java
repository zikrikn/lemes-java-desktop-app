package newlemes;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Kolam {
    private Notifikasi notifikasi = new Notifikasi();

    String pattern = "yyyy-MM-dd HH:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = simpleDateFormat.format(new Date()); //this time now

    //Nanti buat login untuk mengklasifikasi hasilnya berdasarkan user
    public void createTableKolam() {
        String sql = "CREATE TABLE IF NOT EXISTS kolam (" +
        "username TEXT NOT NULL, " +
        "namaKolam TEXT NOT NULL UNIQUE PRIMARY KEY," +
        "jumlahLele INTEGER NOT NULL, " +
        "beratLele INTEGER NOT NULL, " +
	    "stockPakan INTEGER NOT NULL, " +
        "waktuTebar TEXT NOT NULL, " +
        "jumlahPakanHarian INTEGER, " +
	    "waktuPanen TEXT NOT NULL, " +
        "waktuRestock TEXT, " +
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

    public void inputDataKolam(String username, String namaKolam, int jumlahLele, int beratLele, int stockPakan) throws ParseException {
        String sql = "INSERT INTO kolam (username, namaKolam, jumlahLele, beratLele, stockPakan, waktuTebar, jumlahPakanHarian, waktuPanen) VALUES(?,?,?,?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = Connector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, username);
            pstmt.setString(2, namaKolam);
            pstmt.setInt(3, jumlahLele);
            pstmt.setInt(4, beratLele);
            pstmt.setInt(5, stockPakan);
            pstmt.setString(6, date);

            int jumlahPakanHarian = Function.hitungJumlahPakan(jumlahLele, beratLele, 3.5, 0.07, stockPakan);
            pstmt.setInt(7, jumlahPakanHarian);

            String waktuPanenKolam = Function.waktuPanen(jumlahPakanHarian, 60, date);
            pstmt.setString(8, waktuPanenKolam);

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
        String sql = "SELECT * FROM kolam WHERE username = ?";

        try (Connection conn = Connector.connect();
        PreparedStatement pstmt  = conn.prepareStatement(sql)){;
            pstmt.setString(1, username);
            ResultSet rs    = pstmt.executeQuery();

            System.out.println("Nama Kolam " + " Berat Lele" + " Jumlah Lele" + " Tanggal Awal Tebar Benih");
            while (rs.next()){
                System.out.println(rs.getString("namaKolam") + "\t\t" + 
                rs.getInt("beratLele") + "\t" +
                rs.getInt("jumlahLele") + "\t\t" + 
                rs.getString("waktuTebar"));
            }
            Connector.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchKolam(String username, String namaKolam) {
        String sql = "SELECT * FROM kolam WHERE namaKolam = ? and username = ?";

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

    public void RestockPakan(String username, String namaKolam, double stockPakan, int jumlahPakanHarian) {
        String sql = "UPDATE kolam SET waktuRestock = ? WHERE namaKolam = ? and username = ?";

        try (Connection conn = Connector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            // We have to make a logic to calculate in this program
            String waktuRestockPakan = Function.waktuRestock(stockPakan, jumlahPakanHarian);
            pstmt.setString(2, waktuRestockPakan);
            pstmt.setString(3, namaKolam);
            pstmt.setString(4, username);

            // Masukan logic untuk restock
            // Untuk menambahkan data ke table db notifikasi
            pstmt.executeUpdate();
            Connector.disconnect();
            notifikasi.notifikasiIn(username, "Restock");
            System.out.println("Restock berhasil diupdate!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Kolam newKolam = new Kolam();

        // Masih belum bisa diupdate
        // newKolam.createTableKolam();
        // newKolam.displayAllKolam("fikri");
        // newKolam.inputDataKolam("fikri", "kolam2", 25, 1000, "2020-12-12");
    }
}
