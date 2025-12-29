/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.view;

import itenas.is.perscents.model.Parfum;
import itenas.is.perscents.model.ParfumCollection;
import itenas.is.perscents.model.TingkatKetahanan;
import itenas.is.perscents.repository.ParfumRepository;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class AdminMenu {
    private Scanner scanner = new Scanner(System.in);
    private ParfumRepository repo = new ParfumRepository();

    public void showMenu() {
        while (true) {
            System.out.println("\n=== DASHBOARD ADMIN ===");
            System.out.println("1. Lihat Semua Parfum");
            System.out.println("2. Tambah Parfum Baru");
            System.out.println("3. Update Harga Parfum");
            System.out.println("4. Hapus Parfum");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");
            
            int pilih = scanner.nextInt();
            scanner.nextLine(); 

            switch (pilih) {
                case 1:
                    lihatParfum();
                    break;
                case 2:
                    tambahParfum();
                    break;
                case 3:
                    updateParfum();
                    break;
                case 4:
                    hapusParfum();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan salah!");
            }
        }
    }

    private void lihatParfum() {
        List<Parfum> list = repo.findAll();
        for (Parfum p : list) {
            p.tampilkanInfo();
        }
    }

    private void tambahParfum() {
        String tambah;
        do {
            try {
            System.out.println("\n--- TAMBAH PARFUM ---");
            System.out.print("Nama Produk: ");
            String nama = scanner.nextLine();
            
            System.out.print("Kode Karakter (MBTI/KUCING): ");
            String kode = scanner.nextLine();
            
            System.out.print("Tema Karakter: ");
            String tema = scanner.nextLine();
            
            System.out.print("Kategori (Pria/Wanita/Anak-anak): ");
            String kat = scanner.nextLine();
            
            System.out.print("Deskripsi Wangi: ");
            String desk = scanner.nextLine();
            
            System.out.print("Harga: ");
            double harga = scanner.nextDouble();
            scanner.nextLine();
            
            ParfumCollection pBaru = new ParfumCollection(kode, tema, nama, kat, harga, desk);
            
            repo.insert(pBaru);
            
            } catch (Exception e) {
                System.out.println("Gagal input: " + e.getMessage());
            }
            
            System.out.print("Tambah Parfum? (Y/N)");
            tambah = scanner.nextLine();
        } while (tambah.equalsIgnoreCase("Y"));
    }

    private void updateParfum() {
        System.out.print("Masukkan Nama Produk yang mau diubah harganya: ");
        String nama = scanner.nextLine();
        System.out.print("Harga Baru: ");
        double harga = scanner.nextDouble();
        repo.updateHarga(nama, harga);
    }

    private void hapusParfum() {
        System.out.print("Masukkan Nama Produk yang mau dihapus: ");
        String nama = scanner.nextLine();
        System.out.print("Yakin hapus (y/n)? ");
        String confirm = scanner.nextLine();
        if(confirm.equalsIgnoreCase("y")) {
            repo.delete(nama);
        }
    }
}