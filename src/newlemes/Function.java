package newlemes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Function {
    // Fungsi untuk menghitung jumlah pakan harian lele
    public static int hitungJumlahPakan(int jumlahLele, double beratLele, double kandunganEnergi, double faktorKonversi, int stockPakan) {
        // Hitung berat total lele
        double beratTotalLele = jumlahLele * beratLele;
        // Hitung jumlah pakan yang dibutuhkan dengan rumus yang disebutkan sebelumnya
        // It's changed for the sake of the project
        double jumlahPakan = ((beratTotalLele / kandunganEnergi) * faktorKonversi);
        // Jika stok pakan kurang dari jumlah pakan yang dibutuhkan, kembalikan stok pakan sebagai jumlah pakan harian
        if (stockPakan < jumlahPakan) {
            return (int) Math.round(stockPakan);
        }
        // Jika stok pakan lebih dari atau sama dengan jumlah pakan yang dibutuhkan, kembalikan jumlah pakan yang dibutuhkan sebagai jumlah pakan harian
        else {
            return (int) Math.round(jumlahPakan);
        }
    }

    public static String waktuRestock(int stockPakan, int jumlahPakanHarian) {
        // Jika sisa stok kurang dari 0, maka waktu reminder restock adalah sekarang
        if (stockPakan < 0) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
            Date waktuReminderRestock = cal.getTime();
            
            // Buat objek SimpleDateFormat dengan format tanggal yang diinginkan
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            
            // Ubah objek Date ke String dengan menggunakan format() method
            String waktuReminderRestockString = formatter.format(waktuReminderRestock);
            
            return waktuReminderRestockString;
        }
        // Jika sisa stok lebih dari 0, hitung berapa hari lagi stok pakan akan habis
        // dan tambahkan waktu sekarang untuk mendapatkan waktu reminder restock
        else {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
            int hariSisa = (int) (stockPakan / jumlahPakanHarian);
            cal.add(Calendar.DATE, hariSisa);
            Date waktuReminderRestock = cal.getTime();
            
            // Buat objek SimpleDateFormat dengan format tanggal yang diinginkan
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            
            // Ubah objek Date ke String dengan menggunakan format() method
            String waktuReminderRestockString = formatter.format(waktuReminderRestock);
            
            return waktuReminderRestockString;
        }
    }
    
    public static String waktuPanen(int jumlahPakanHarian, double lamaPanen, String tanggalInput) throws ParseException {
        // Buat objek SimpleDateFormat dengan format tanggal yang diinginkan
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Ubah String ke objek Date dengan menggunakan parse() method
        Date waktuPanenInput = formatter.parse(tanggalInput);
        
        // Hitung jumlah hari yang diperlukan untuk panen
        double jumlahHariPanen = lamaPanen / jumlahPakanHarian;
        // lama_panen = stok_pakan / (jumlah_lele * berat_lele * jumlah_pakan / 1000 * 30)
        // Hitung waktu reminder panen dengan menambahkan jumlah hari yang diperlukan untuk panen pada waktu panen
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
        cal.setTime(waktuPanenInput);
        cal.add(Calendar.DATE, (int) jumlahHariPanen);
        Date waktuPanen = cal.getTime();
        
        // Buat objek SimpleDateFormat dengan format tanggal yang diinginkan
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Ubah objek Date ke String dengan menggunakan format() method
        String waktuPanenString = formatter2.format(waktuPanen);

        // Kembalikan waktu reminder panen dalam format YYYY-MM-DD HH:MM:SS
        return waktuPanenString;
    }
    
}
