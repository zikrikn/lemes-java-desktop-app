package newlemes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Notifikasi {
    //Satukan lewat disini jangan dipanggil satu-satu
    //Untuk Restock masuk ke sini
    //Solved
    Connection conn = Connector.connect();
    String pattern = "yyyy-MM-dd HH:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = simpleDateFormat.format(new Date()); //this time now

    //randomly generates a UUID  
    UUID uuid = UUID.randomUUID();   
    //converts the randomly generated UUID into String  
    String uuidAsString = uuid.toString();  

    public void createTableNotifikasi() {
        String sql = "CREATE TABLE notifikasiIn (" +
            "notifikasiID	TEXT NOT NULL," +
            // "userID	        TEXT NOT NULL," +
            "tipe	        TEXT NOT NULL," +
            "waktuMasuk	    TEXT NOT NULL," +
            "waktuKeluar	TEXT NOT NULL," +
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
        String sql = "INSERT INTO notifikasiIn (notifikasiID, tipe, waktuMasuk, waktuKeluar, messages) VALUES (?,?,?,?,?)";

        try (Connection conn = Connector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, uuidAsString);
            // pstmt.setString(2, userID);
            pstmt.setString(2, tipe);
            pstmt.setString(3, date);
            
            if (tipe == "Harian"){
                //create instance of the Calendar class and set the date to the given date  
                Calendar cal = Calendar.getInstance();  
                try{  
                cal.setTime(simpleDateFormat.parse(date));  
                }catch(ParseException e){  
                    e.printStackTrace();  
                }  
                    
                // use add() method to add the days to the given date  
                cal.add(Calendar.DAY_OF_MONTH, 1);  
                String dateAfter = simpleDateFormat.format(cal.getTime());   

                pstmt.setString(4, dateAfter);

                pstmt.setString(5, "Notifikasi harian aktif! Kamu akan mendapatkan notifikasi 3 kali sehari sampai tanggal 23 September 2022.");
            }else if (tipe == "Panen"){
                //create instance of the Calendar class and set the date to the given date  
                Calendar cal = Calendar.getInstance();  
                try{  
                cal.setTime(simpleDateFormat.parse(date));  
                }catch(ParseException e){  
                    e.printStackTrace();  
                }  
                    
                // use add() method to add the days to the given date  
                cal.add(Calendar.DAY_OF_MONTH, 30);  
                cal.add(Calendar.HOUR_OF_DAY, 9);
                String dateAfter = simpleDateFormat.format(cal.getTime());   

                pstmt.setString(4, dateAfter);

                pstmt.setString(5, "Notifikasi panen aktif! Kamu akan mendapatkan notifikasi mulai dari H-3 panen.");
            }else if (tipe == "Restock"){
                //create instance of the Calendar class and set the date to the given date  
                Calendar cal = Calendar.getInstance();  
                try{  
                cal.setTime(simpleDateFormat.parse(date));  
                }catch(ParseException e){  
                    e.printStackTrace();  
                }  
                    
                // use add() method to add the days to the given date  
                cal.add(Calendar.DAY_OF_MONTH, 28);  
                cal.add(Calendar.HOUR_OF_DAY, 11);
                String dateAfter = simpleDateFormat.format(cal.getTime());   

                pstmt.setString(4, dateAfter);

                pstmt.setString(5, "Notifikasi restock aktif! Kamu akan mendapatkan notifikasi H-2 sebelum perkiraan restock.");
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
