package newlemes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

// I think we have to create disconnect db too, to make it smooth!
// untuk memberikan pemberitahuan notifikasi tepat waktu

public class NotifikasiJob implements Job {
    Auth auth = new Auth();

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        Connection conn = Connector.connect();
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date()); //this time now
        String d = date.substring(0, 10);//divide the date and time into 2 parts
        String uname = auth.getTitipUsername();

        try {
            String sql = "SELECT * FROM notifikasiIn WHERE waktuKeluar = ? and username = ?"; //ini masih salah karena usernamenya gimana dapetnya?
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, d);
            pstmt.setString(2, uname);
            

            ResultSet rs = pstmt.executeQuery();

            String dateKeluar = rs.getString("waktuKeluar");

            // DB String to Date
            Date restockH1 = simpleDateFormat.parse(dateKeluar);
            Date pakanH1 = simpleDateFormat.parse(dateKeluar);
            Date pakanH3 = simpleDateFormat.parse(dateKeluar);

            Date hari8 = simpleDateFormat.parse(dateKeluar);
            Date hari12 = simpleDateFormat.parse(dateKeluar);
            Date hari17 = simpleDateFormat.parse(dateKeluar);

            // Convert Date to Calendar
            Calendar r1 = Calendar.getInstance();
            r1.setTime(restockH1);
            r1.add(Calendar.DATE, -1);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(pakanH3);
            c1.add(Calendar.DATE, -3);

            Calendar c2 = Calendar.getInstance();
            c2.setTime(pakanH1);
            c2.add(Calendar.DATE, -1);

            // Convert Date to Calendar Harian
            Calendar h8 = Calendar.getInstance();
            h8.setTime(hari8);
            h8.add(Calendar.HOUR, 8);

            Calendar h12 = Calendar.getInstance();
            h12.setTime(hari12);
            h12.add(Calendar.HOUR, 12);

            Calendar h17 = Calendar.getInstance();
            h17.setTime(hari17);
            h17.add(Calendar.HOUR, 17);

            // Convert calendar back to Date
            Date currentRestockH1 = r1.getTime();
            String currentRestockH1new = simpleDateFormat.format(currentRestockH1);

            Date currentPakanH3 = c1.getTime();
            String currentPakanH3new = simpleDateFormat.format(currentPakanH3);

            Date currentPakanH1 = c2.getTime();
            String currentPakanH1new = simpleDateFormat.format(currentPakanH1);

            // Convert calendar back to Date Harian
            Date currentH8 = h8.getTime();
            String currentH8new = simpleDateFormat.format(currentH8);

            Date currentH12 = h12.getTime();
            String currentH12new = simpleDateFormat.format(currentH12);

            Date currentH17 = h17.getTime();
            String currentH17new = simpleDateFormat.format(currentH17);


            // Kayanya ini dibuat beda class 
            while(rs.next()){
                if (rs.getString("tipe") == "Harian"){
                    if (currentH8new == d + " 08:00"){
                        // this will be the pop up thing
                        System.out.println("Reminder untuk memberi pakan pada Pagi hari!");
                    }else if (currentH12new == d + " 12:00"){   
                        System.out.println("Reminder untuk memberi pakan pada Siang hari!");
                    }else if (currentH17new == d + " 17:00"){
                        System.out.println("Reminder untuk memberi pakan pada Sore hari!");
                    }
                }

                if (rs.getString("tipe") == "Restock"){
                    if (currentRestockH1new == d + " 11:00"){
                        System.out.println("Reminder H-1 Sebelum Restock Pakan!");
                    }else if (dateKeluar == d + "  11:00"){
                        System.out.println("Waktu Restock Pakan!");
                }

                if (rs.getString("tipe") == "Pakan"){
                    if (currentPakanH3new == d + " 09:00"){
                        System.out.println("Reminder H-2 Sebelum Panen");
                    }else if (currentPakanH1new == d + " 09:00"){
                        System.out.println("Reminder H-1 Sebelum ");
                    }else if (dateKeluar == d + " 09:00"){ // dateKeluar adalah waktu keluar dari database yang mana berbeda-beda
                        System.out.println("Waktunya Panen!");
                    }
                }
            }
        }
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }

    }
    // I think we can make a double method, one for checking the data and one for masukin data tapi dibikin method baru
}

//Which is its simpler

//Maybe just says "You gonna get 3 notification everyday until 30 September 2022", etc in list of notications

//But actual notifications will be pop-ed up there.
