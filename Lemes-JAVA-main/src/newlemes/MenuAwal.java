package newlemes;
import java.util.*;

import org.quartz.SchedulerException;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MenuAwal {
    Auth auth = new Auth();
    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws SchedulerException{
        MenuAwal menuAwal = new MenuAwal();
        
        JobDetail job = JobBuilder.newJob(NotifikasiJob.class).build();

        //Cron Trigger
        Trigger t1 = TriggerBuilder.newTrigger().withIdentity("CronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")).build();
    
        Scheduler sc = StdSchedulerFactory.getDefaultScheduler();
    
        sc.start();
        sc.scheduleJob(job, t1);

        menuAwal.menuAwal();
    }

    public void menuAwal() throws SchedulerException{
        menu();
        int pilih = scan.nextInt();
        
        while (pilih != 0){
            if (pilih == 1){
                System.out.print("\nUsername\t: ");
                String username = scan.next();
                System.out.print("Name\t\t: ");
                String name = scan.next();
                System.out.print("Password\t: ");
                String password = scan.next();
                System.out.println();

                auth.registrasi(username, name, password);

                menu();
                pilih = scan.nextInt();

            }else if(pilih == 2){
                System.out.print("\nUsername\t: ");
                String username = scan.next();
                System.out.print("Password\t: ");
                String password = scan.next();
                System.out.println();

                auth.validasiLogin(username, password);

                menu();
                pilih = scan.nextInt();
            }else{
                System.out.println("\nInput Tidak Valid! Input Ulang");

                menu();
                pilih = scan.nextInt();
            }
        }

        if (pilih == 0){
            System.out.println("\nBerhasil Keluar! Terima kasih");
        }
        scan.close();    
    }

    public void menu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("0. Keluar");
        System.out.print("Pilih Menu : ");
    }
}
