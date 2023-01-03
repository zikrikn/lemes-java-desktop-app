package newlemes;

import java.util.*;

public class MenuAdmin {
    Scanner scan = new Scanner(System.in);
    BeritaPedoman beritaPedoman = new BeritaPedoman();

    public void menu(){
        System.out.println("\n=== MENU ADMIN ===");
        System.out.println("1. Tambah Berita");
        System.out.println("2. Tambah Pedoman");
        System.out.println("3. Show Berita");
        System.out.println("4. Show Pedoman");
        System.out.println("0. Logout");
        System.out.print("Pilih menu : ");
    }

    public void pilihMenu(){
        menu();
        int pilih = scan.nextInt();
        while  (pilih != 0){
            if (pilih == 1){
                System.out.print("Tambah ID Berita: ");
                int newID = scan.nextInt();
                System.out.print("Tambah Judul berita : ");
                String newJudul= scan.next();
                System.out.println("Isi Berita : ");
                String newIsiBerita = scan.next();
                System.out.print("Tanggal Berita Terbit : ");
                String newTanggalBerita = scan.next();
                
                beritaPedoman.insertBeritaPedoman(newID, "Berita", newJudul, newTanggalBerita, newIsiBerita, "Isi");

                
                menu();
                pilih = scan.nextInt();
            }else if(pilih == 2){
                System.out.print("Tambah ID Pedoman: ");
                int newID = scan.nextInt();
                System.out.print("Tambah Judul Pedoman : ");
                String newJudul = scan.next();
                System.out.println("Isi Pedoman : ");
                String newIsiPedoman = scan.next();
                System.out.print("Tanggal Pedoman Terbit : ");
                String newTanggalPedoman = scan.next();
               

                beritaPedoman.insertBeritaPedoman(newID, "Pedoman", newJudul, newTanggalPedoman, newIsiPedoman, "Isi");
                
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
            }else{
                System.out.println("\nInput Tidak Valid! Input Ulang");

                menu();
                pilih = scan.nextInt();
            }
        } 
    }
}
