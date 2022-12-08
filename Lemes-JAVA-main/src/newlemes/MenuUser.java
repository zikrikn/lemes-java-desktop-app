package newlemes;

import java.util.*;

public class MenuUser {
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
        System.out.println("0. Logout ");
        System.out.print("Pilih Menu : ");
    }

    public void pilihMenu(){
        menu();
        int pilih = scan.nextInt();
        while  (pilih != 0){
            if (pilih == 1){
                kolam.displayAllKolam();
                
                menu();
                pilih = scan.nextInt();
            }else if(pilih == 2){
                System.out.print("Nama Kolam\t: ");
                String newNama = scan.next();
                System.out.print("Berat Lele\t: ");
                int newBerat = scan.nextInt();
                System.out.print("Jumlah Lele\t: ");
                int newJumlah = scan.nextInt();
                System.out.print("Tanggal Awal Tebar Benih : ");
                String newTanggal = scan.next();
                System.out.println();

                kolam.inputDataKolam(newNama, newBerat, newJumlah, newTanggal);
                
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

                notifikasi.notifikasiList();
                menu();
                pilih = scan.nextInt();
            }else if (pilih == 6) {
                System.out.print("Masukkan Nama Kolam yang ingin dicari : ");
                String cari = scan.next();

                kolam.searchKolam(cari);                
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
