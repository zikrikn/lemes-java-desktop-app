package newlemes;

import java.text.ParseException;
import java.util.*;
import org.quartz.SchedulerException;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MenuUser {
    private int stockPakan;
    private int jumlahPakanHarian;

    public int getStockPakan() {
        return stockPakan;
    }

    public void setStockPakan(int stockPakan) {
        this.stockPakan = stockPakan;
    }

    public int getJumlahPakanHarian() {
        return jumlahPakanHarian;
    }

    public void setJumlahPakanHarian(int jumlahPakanHarian) {
        this.jumlahPakanHarian = jumlahPakanHarian;
    }

    Kolam kolam = new Kolam();
    BeritaPedoman beritaPedoman = new BeritaPedoman();
    Scanner scan = new Scanner(System.in);
    Notifikasi notifikasi = new Notifikasi();

    public void menu(){
        System.out.println(" ");
        System.out.println("=== WELLCOME TO MENU LeMES ===");
        System.out.println("1. Display Info Kolam ");
        System.out.println("2. Create Kolam Baru ");
        System.out.println("3. Show Berita");
        System.out.println("4. Show Pedoman");
        System.out.println("5. Notifikasi ");
        System.out.println("6. Search Kolam ");
        System.out.println("7. Restock Pakan ");
        System.out.println("0. Logout ");
        System.out.print("Pilih Menu : ");
    }

    public void pilihMenu(String username) throws SchedulerException{
        JobDetail job = JobBuilder.newJob(NotifikasiJob.class).build();

        //Cron Trigger
        Trigger t1 = TriggerBuilder.newTrigger().withIdentity("CronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")).build();
    
        Scheduler sc = StdSchedulerFactory.getDefaultScheduler();
    
        sc.start();
        sc.scheduleJob(job, t1);
        menu();
        int pilih = scan.nextInt();
        while  (pilih != 0){
            if (pilih == 1){
                kolam.displayAllKolam(username);
                
                menu();
                pilih = scan.nextInt();
            }else if(pilih == 2){
                System.out.print("Nama Kolam\t: ");
                String newNama = scan.next();
                System.out.print("Jumlah Lele\t: ");
                int newJumlah = scan.nextInt();
                System.out.print("Berat Lele\t: ");
                int newBerat = scan.nextInt();
                System.out.print("Stock Pakan\t: ");
                int newStockPakan = scan.nextInt();
                setStockPakan(newStockPakan);
                System.out.println();

                int jumlahPakanHarian = Function.hitungJumlahPakan(newJumlah, newBerat, 3.5, 0.07, newStockPakan);
                setJumlahPakanHarian(jumlahPakanHarian);

                try {
                    kolam.inputDataKolam(username, newNama, newJumlah, newBerat, newStockPakan);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                
                menu();
                pilih = scan.nextInt();
            }else if(pilih == 3){
                beritaPedoman.viewBeritaPedoman("Berita");
                
                menu();
                pilih = scan.nextInt();
            }else if(pilih == 4){                
                beritaPedoman.viewBeritaPedoman("Pedoman");
                
                menu();
                pilih = scan.nextInt();
            }else if(pilih == 5){

                notifikasi.notifikasiList(username);
                menu();
                pilih = scan.nextInt();
            }else if (pilih == 6) {
                System.out.print("Masukkan Nama Kolam yang ingin dicari : ");
                String cari = scan.next();

                kolam.searchKolam(username, cari);                
                menu();
                pilih = scan.nextInt();
            }else if (pilih == 7){
                System.out.print("Masukkan Nama Kolam : ");
                String namaKolam = scan.next();
                
                int[] numbers = kolam.findUsername(username, namaKolam);
                System.out.println("Stock Pakan : " + numbers[0]);
                System.out.println("Jumlah Pakan Harian : " + numbers[1]);

                try {
                    kolam.RestockPakan(username, namaKolam, numbers[0], numbers[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                
                menu();
                pilih = scan.nextInt();
            }else{
                System.out.println("\nInput Tidak Valid! Input Ulang");

                menu();
                pilih = scan.nextInt();
            }
        }
    }
}
