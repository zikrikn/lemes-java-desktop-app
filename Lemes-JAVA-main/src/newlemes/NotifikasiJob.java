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

// untuk memberikan pemberitahuan notifikasi tepat waktu

public class NotifikasiJob implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        Connection conn = Connector.connect();
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date()); //this time now
        String d = date.substring(0, 10);//divide the date and time into 2 parts

        //So we can make the waktuKeluar jadi date dulu after that we can add the time then makes it to be string again

        try {
            //Can we have multiple execute in jdbc?
            //This can be pop up notification but also put the data to the new table
            //I don't think we need multiple select execute tho, so the easiest way is directly compare them

            //So basically its same as RPL IMPAL
            //Pasang aja triggernya di waktu dan kondisi yang ditentukan
            //Try it tho
            //we have to make same as IMPAL karena untuk keluarnya tepat waktu yang ditentukan kita

            // So just tanggal untuk waktuKeluar
            String sql = "SELECT * FROM notifikasiIn WHERE waktuKeluar == ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, d);

            ResultSet rs = pstmt.executeQuery();

            String dateKeluar = rs.getString("waktuKeluar");

            // DB String Date to 
            Date restockH1 = simpleDateFormat.parse(dateKeluar);
            Date pakanH1 = simpleDateFormat.parse(dateKeluar);
            Date pakanH3 = simpleDateFormat.parse(dateKeluar);

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

            // Convert calendar back to Date
            Date currentRestockH1 = r1.getTime();
            String currentRestockH1new = simpleDateFormat.format(currentRestockH1);

            Date currentPakanH3 = c1.getTime();
            String currentPakanH3new = simpleDateFormat.format(currentPakanH3);

            Date currentPakanH1 = c2.getTime();
            String currentPakanH1new = simpleDateFormat.format(currentPakanH1);

            // Kayanya ini dibuat beda class 
            while(rs.next()){
                if (rs.getString("tipe") == "Harian"){
                    if (dateKeluar == d + " 08:00"){
                        // this will be the pop up thing
                        System.out.println("Reminder untuk memberi pakan pada Pagi hari!");
                    }else if (dateKeluar == d + " 12:00"){   
                        System.out.println("Reminder untuk memberi pakan ");
                    }else if (dateKeluar == d + " 17:00"){
                        System.out.println("Reminder untuk memberi pakan pada Siang hari!");
                    }
                }

                if (rs.getString("tipe") == "Restock"){
                    if (currentRestockH1new == d + " 11:00"){
                        System.out.println("Reminder H-1 Sebelum Restock Pakan!");
                    }else if (dateKeluar == d + "  11:00"){
                        System.out.println("Waktu Restock Pakan!");
                }

                if (rs.getString("tipe") == "Pakan"){
                    if (currentPakanH3new == d + " 09:01"){
                        System.out.println("Reminder H-2 Sebelum Panen");
                    }else if (currentPakanH1new == d + " 09:01"){
                        System.out.println("Reminder H-1 Sebelum ");
                    }else if (dateKeluar == d + " 09:01"){
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
