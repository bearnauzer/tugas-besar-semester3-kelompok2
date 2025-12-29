/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.view;

import itenas.is.perscents.model.Parfum;
import itenas.is.perscents.model.ParfumCollection;
import itenas.is.perscents.model.TingkatKetahanan;
import itenas.is.perscents.model.Transaksi;
import itenas.is.perscents.service.ParfumService;
import itenas.is.perscents.service.TransaksiService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */


public class PembeliMenu {
    static Scanner input = new Scanner(System.in);
    static ParfumService service = new ParfumService();
    static List<ItemTransaksi> keranjang = new ArrayList<>();
    
    static class ItemTransaksi {
        String namaItem;
        String spek;
        double harga;

        public ItemTransaksi(String namaItem, String spek, double harga) {
            this.namaItem = namaItem;
            this.spek = spek;
            this.harga = harga;
        }
    }

    public void showMenuPembeli() {
        try {
            System.out.println("\n=== SELAMAT DATANG DI PERSCENTS ===");
            System.out.println("1. Parfum Pria");
            System.out.println("2. Parfum Wanita");
            System.out.println("3. Parfum Anak-anak");
            System.out.println("4. Keluar");
            System.out.print("Pilih Kategori (1-4): ");

            int pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    menuPria();
                    break; 
                case 2:
                    menuWanita();
                    break;
                case 3:
                    menuAnak();
                    break;
                case 4:
                    System.out.println("Terima kasih!");
                    System.exit(0);
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan input!");
        }
    }


    static void menuPria() {
        System.out.println("\n=== PARFUM PRIA ===");
        System.out.println("1. Perfume Collection");
        System.out.println("2. Custom Parfum");
        System.out.print("Pilih: ");
        int pilih = input.nextInt();

        if (pilih == 1) { //COLLECTION INI BANG
            List<Parfum> listPria = service.ambilListParfum("Pria");

            if (listPria.isEmpty()) {
                System.out.println("Stok kosong!");
                return;
            }

            System.out.println("\n--- PILIH AROMA ---");
            for (int i = 0; i < listPria.size(); i++) {
                Parfum p = listPria.get(i);
                if (p instanceof ParfumCollection) {
                    ParfumCollection pc = (ParfumCollection) p;
                    System.out.println((i + 1) + ". " + pc.getNamaProduk() + " [" + pc.getKodeKarakter() + "]");
                    System.out.println("   Tema        : " + pc.getTemaKarakter());
                    System.out.println("   Deskripsi   : " + pc.getDeskripsi());
                    System.out.println("   Base Price  : Rp" + pc.getHarga());
                    System.out.println("   -----------------------------------");
                }
            }

            System.out.print("Pilih nomor parfum: ");
            int index = input.nextInt();

            if (index < 1 || index > listPria.size()) {
                System.out.println("Nomor salah!");
                return;
            }

            ParfumCollection parfumDipilih = (ParfumCollection) listPria.get(index - 1);

            System.out.println("\n--- PILIH TINGKAT KETAHANAN ---");
            System.out.println("1. Light (Harga Tetap)");
            System.out.println("2. Balance (+Rp 30.000)");
            System.out.println("3. Signature (+Rp 40.000)");
            System.out.print("Pilih: ");
            int tahan = input.nextInt();

            double hargaDasar = parfumDipilih.getHarga();
            TingkatKetahanan ketahananDipilih = TingkatKetahanan.LIGHT;

            switch (tahan) {
                case 1: ketahananDipilih = TingkatKetahanan.LIGHT; break;
                case 2: ketahananDipilih = TingkatKetahanan.BALANCE; break;
                case 3: ketahananDipilih = TingkatKetahanan.SIGNATURE; break;
                default:
                    System.out.println("Pilihan tidak valid, default ke Light.");
                    ketahananDipilih = TingkatKetahanan.LIGHT;
            }

            parfumDipilih.setKetahanan(ketahananDipilih);
            double totalHarga = hargaDasar + ketahananDipilih.getBiayaTambahan();
            parfumDipilih.setHarga(totalHarga);

            System.out.println("Ketahanan: " + ketahananDipilih.getDeskripsi());
            System.out.println("Total Harga: " + totalHarga);

            System.out.println("\n=== ITEM DITAMBAHKAN ===");
            System.out.println("Produk : " + parfumDipilih.getNamaProduk());
            System.out.println("Spek   : " + parfumDipilih.getKetahanan());
            System.out.println("Total  : Rp " + parfumDipilih.getHarga());

            ItemTransaksi item = new ItemTransaksi(parfumDipilih.getNamaProduk(), ketahananDipilih.name(), totalHarga);
            keranjang.add(item);
            
            checkout();

        } else if (pilih == 2) { //CUSTOM INI BANG
            System.out.println("\n--- PARFUM CUSTOM ---");
            System.out.println("Harga Flat: Rp 160.000 (Max 3 Aroma)");

            List<Parfum> listCustomP = service.ambilListParfum("CustomP");

            if (listCustomP.isEmpty()) {
                System.out.println("Stok kosong!");
                return;
            }

            System.out.println("\n--- PILIH AROMA CUSTOM (MAX 3) ---");
            List<ParfumCollection> daftarAroma = new ArrayList<>();

            int nomor = 1;
            for (Parfum p : listCustomP) {
                if (p instanceof ParfumCollection) {
                    ParfumCollection pc = (ParfumCollection) p;
                    daftarAroma.add(pc);
                    System.out.println((daftarAroma.size()) + ". " + pc.getNamaProduk());
                    System.out.println("   Tema        : " + pc.getTemaKarakter());
                    System.out.println("   Deskripsi   : " + pc.getDeskripsi());
                    System.out.println();
                    nomor++;
                }
            }

            System.out.println("---------------------------------");

            List<ParfumCollection> racikanUser = new ArrayList<>();
            input.nextLine(); 

            while (racikanUser.size() < 3) {
                System.out.print("Masukkan nomor aroma ke-" +(racikanUser.size() + 1) +" (ketik 'stop' untuk selesai): ");
                String inputUser = input.nextLine();

                if (inputUser.equalsIgnoreCase("stop")) break;

                int pilihanAroma;
                try {
                    pilihanAroma = Integer.parseInt(inputUser);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (pilihanAroma < 1 || pilihanAroma > daftarAroma.size()) {
                    System.out.println("Nomor aroma tidak tersedia!");
                    continue;
                }

                ParfumCollection aromaDipilih = daftarAroma.get(pilihanAroma - 1);
                if (racikanUser.contains(aromaDipilih)) {
                    System.out.println("Aroma sudah dipilih sebelumnya!");
                    continue;
                }

                racikanUser.add(aromaDipilih);
                System.out.println(aromaDipilih.getNamaProduk() + " ditambahkan.");
            }

            if (racikanUser.isEmpty()) {
                System.out.println("Anda belum memilih aroma!");
                return;
            }

            System.out.println("\nAroma yang dipilih:");
            for (ParfumCollection pc : racikanUser) {
                System.out.println("- " + pc.getNamaProduk());
            }

            System.out.println("\n--- PILIH TINGKAT KETAHANAN ---");
            System.out.println("1. Light");
            System.out.println("2. Balance");
            System.out.println("3. Signature");
            System.out.print("Pilih: ");
            int tahan = input.nextInt();

            TingkatKetahanan ketahananDipilih;
            switch (tahan) {
                case 1: ketahananDipilih = TingkatKetahanan.LIGHT; break;
                case 2: ketahananDipilih = TingkatKetahanan.BALANCE; break;
                case 3: ketahananDipilih = TingkatKetahanan.SIGNATURE; break;
                default: ketahananDipilih = TingkatKetahanan.LIGHT;
            }

            double hargaBaseCustom = 160000;
            double biayaTambahan = ketahananDipilih.getBiayaTambahan();
            double totalHarga = hargaBaseCustom + biayaTambahan;

            System.out.println("\n=== ITEM CUSTOM DITAMBAHKAN ===");
            String namaRacikan = "Custom Mix: ";
            for (int i = 0; i < racikanUser.size(); i++) {
                namaRacikan += racikanUser.get(i).getNamaProduk();
                if (i < racikanUser.size() - 1) namaRacikan += " + ";
            }
            System.out.println("Racikan : " + namaRacikan);
            System.out.println("Spek    : " + ketahananDipilih.getDeskripsi());
            System.out.println("Total   : Rp " + totalHarga);

            ItemTransaksi item = new ItemTransaksi(namaRacikan, ketahananDipilih.name(), totalHarga);
            keranjang.add(item);

            checkout();
        }
    }
    
    static void menuWanita() {
        System.out.println("\n=== PARFUM WANITA ===");
        System.out.println("1. Perfume Collection");
        System.out.println("2. Custom Parfum");
        System.out.print("Pilih: ");
        int pilih = input.nextInt();

        if (pilih == 1) { //COLLECTION IN BANG
            List<Parfum> listWanita = service.ambilListParfum("Wanita");

            if (listWanita.isEmpty()) {
                System.out.println("Stok kosong!");
                return;
            }

            System.out.println("\n--- PILIH AROMA ---");
            for (int i = 0; i < listWanita.size(); i++) {
                Parfum p = listWanita.get(i);
                if (p instanceof ParfumCollection) {
                    ParfumCollection pc = (ParfumCollection) p;
                    System.out.println((i + 1) + ". " + pc.getNamaProduk() + " [" + pc.getKodeKarakter() + "]");
                    System.out.println("   Tema        : " + pc.getTemaKarakter());
                    System.out.println("   Deskripsi   : " + pc.getDeskripsi());
                    System.out.println("   Base Price  : Rp" + pc.getHarga());
                    System.out.println("   -----------------------------------");
                }
            }

            System.out.print("Pilih nomor parfum: ");
            int index = input.nextInt();

            if (index < 1 || index > listWanita.size()) {
                System.out.println("Nomor salah!");
                return;
            }

            ParfumCollection parfumDipilih = (ParfumCollection) listWanita.get(index - 1);

            System.out.println("\n--- PILIH TINGKAT KETAHANAN ---");
            System.out.println("1. Light (Harga Tetap)");
            System.out.println("2. Balance (+Rp 30.000)");
            System.out.println("3. Signature (+Rp 40.000)");
            System.out.print("Pilih: ");
            int tahan = input.nextInt();

            double hargaDasar = parfumDipilih.getHarga();
            TingkatKetahanan ketahananDipilih = TingkatKetahanan.LIGHT;

            switch (tahan) {
                case 1: ketahananDipilih = TingkatKetahanan.LIGHT; break;
                case 2: ketahananDipilih = TingkatKetahanan.BALANCE; break;
                case 3: ketahananDipilih = TingkatKetahanan.SIGNATURE; break;
                default:
                    System.out.println("Pilihan tidak valid, default ke Light.");
                    ketahananDipilih = TingkatKetahanan.LIGHT;
            }

            parfumDipilih.setKetahanan(ketahananDipilih);
            double totalHarga = hargaDasar + ketahananDipilih.getBiayaTambahan();
            parfumDipilih.setHarga(totalHarga);

            System.out.println("Ketahanan: " + ketahananDipilih.getDeskripsi());
            System.out.println("Total Harga: " + totalHarga);

            System.out.println("\n=== ITEM DITAMBAHKAN ===");
            System.out.println("Produk : " + parfumDipilih.getNamaProduk());
            System.out.println("Spek   : " + parfumDipilih.getKetahanan());
            System.out.println("Total  : Rp " + parfumDipilih.getHarga());

            ItemTransaksi item = new ItemTransaksi(parfumDipilih.getNamaProduk(), ketahananDipilih.name(), totalHarga);
            keranjang.add(item);
            
            checkout();

        } else if (pilih == 2) { //CUSTOM INI BANG
            System.out.println("\n--- PARFUM CUSTOM ---");
            System.out.println("Harga Flat: Rp 160.000 (Max 3 Aroma)");

            List<Parfum> listCustomW = service.ambilListParfum("CustomW");

            if (listCustomW.isEmpty()) {
                System.out.println("Stok kosong!");
                return;
            }

            System.out.println("\n--- PILIH AROMA CUSTOM (MAX 3) ---");
            List<ParfumCollection> daftarAroma = new ArrayList<>();

            int nomor = 1;
            for (Parfum p : listCustomW) {
                if (p instanceof ParfumCollection) {
                    ParfumCollection pc = (ParfumCollection) p;
                    daftarAroma.add(pc);
                    System.out.println((daftarAroma.size()) + ". " + pc.getNamaProduk());
                    System.out.println("   Tema        : " + pc.getTemaKarakter());
                    System.out.println("   Deskripsi   : " + pc.getDeskripsi());
                    System.out.println();
                    nomor++;
                }
            }

            System.out.println("---------------------------------");

            List<ParfumCollection> racikanUser = new ArrayList<>();
            input.nextLine(); 

            while (racikanUser.size() < 3) {
                System.out.print("Masukkan nomor aroma ke-" +(racikanUser.size() + 1) +" (ketik 'stop' untuk selesai): ");
                String inputUser = input.nextLine();

                if (inputUser.equalsIgnoreCase("stop")) break;

                int pilihanAroma;
                try {
                    pilihanAroma = Integer.parseInt(inputUser);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (pilihanAroma < 1 || pilihanAroma > daftarAroma.size()) {
                    System.out.println("Nomor aroma tidak tersedia!");
                    continue;
                }

                ParfumCollection aromaDipilih = daftarAroma.get(pilihanAroma - 1);
                if (racikanUser.contains(aromaDipilih)) {
                    System.out.println("Aroma sudah dipilih sebelumnya!");
                    continue;
                }

                racikanUser.add(aromaDipilih);
                System.out.println(aromaDipilih.getNamaProduk() + " ditambahkan.");
            }

            if (racikanUser.isEmpty()) {
                System.out.println("Anda belum memilih aroma!");
                return;
            }

            System.out.println("\nAroma yang dipilih:");
            for (ParfumCollection pc : racikanUser) {
                System.out.println("- " + pc.getNamaProduk());
            }

            System.out.println("\n--- PILIH TINGKAT KETAHANAN ---");
            System.out.println("1. Light");
            System.out.println("2. Balance");
            System.out.println("3. Signature");
            System.out.print("Pilih: ");
            int tahan = input.nextInt();

            TingkatKetahanan ketahananDipilih;
            switch (tahan) {
                case 1: ketahananDipilih = TingkatKetahanan.LIGHT; break;
                case 2: ketahananDipilih = TingkatKetahanan.BALANCE; break;
                case 3: ketahananDipilih = TingkatKetahanan.SIGNATURE; break;
                default: ketahananDipilih = TingkatKetahanan.LIGHT;
            }

            double hargaBaseCustom = 160000;
            double biayaTambahan = ketahananDipilih.getBiayaTambahan();
            double totalHarga = hargaBaseCustom + biayaTambahan;

            System.out.println("\n=== ITEM CUSTOM DITAMBAHKAN ===");

            // Bikin nama gabungan buat struk
            String namaRacikan = "Custom Mix: ";
            for (int i = 0; i < racikanUser.size(); i++) {
                namaRacikan += racikanUser.get(i).getNamaProduk();
                if (i < racikanUser.size() - 1) namaRacikan += " + ";
            }
            System.out.println("Racikan : " + namaRacikan);
            System.out.println("Spek    : " + ketahananDipilih.getDeskripsi());
            System.out.println("Total   : Rp " + totalHarga);

            ItemTransaksi item = new ItemTransaksi(namaRacikan, ketahananDipilih.name(), totalHarga);
            keranjang.add(item);

            checkout();
        }
    }

    static void menuAnak(){
        List<Parfum> listAnak = service.ambilListParfum("Anak-anak");
        if (listAnak.isEmpty()) {
            System.out.println("Stok kosong!");
            return;
        }

        System.out.println("\n--- PILIH AROMA ---");
        for (int i = 0; i < listAnak.size(); i++) {
            Parfum p = listAnak.get(i);
            if (p instanceof ParfumCollection) {
                ParfumCollection pc = (ParfumCollection) p;
                System.out.println((i + 1) + ". " + pc.getNamaProduk() + " [" + pc.getKodeKarakter() + "]");
                System.out.println("   Tema        : " + pc.getTemaKarakter());
                System.out.println("   Deskripsi   : " + pc.getDeskripsi());
                System.out.println("   Base Price  : Rp" + pc.getHarga());
                System.out.println("   -----------------------------------");
            }
        }

        System.out.print("Pilih nomor parfum: ");
        int index = input.nextInt();

        if (index < 1 || index > listAnak.size()) {
            System.out.println("Nomor salah!");
            return;
        }

        ParfumCollection parfumDipilih = (ParfumCollection) listAnak.get(index - 1);

        System.out.println("\n--- PILIH TINGKAT KETAHANAN ---");
        System.out.println("1. Baby Care (+Rp 35.000)");
        System.out.println("2. Kids Daily (+Rp 25.000)");
        System.out.print("Pilih: ");
        int tahan = input.nextInt();

        double hargaDasar = parfumDipilih.getHarga();
        TingkatKetahanan ketahananDipilih = TingkatKetahanan.BABY_CARE;

        switch (tahan) {
            case 1: ketahananDipilih = TingkatKetahanan.BABY_CARE; break;
            case 2: ketahananDipilih = TingkatKetahanan.KIDS_DAILY; break;
            default: System.out.println("Pilihan tidak valid, default ke BABY_CARE."); ketahananDipilih = TingkatKetahanan.BABY_CARE;
        }

        parfumDipilih.setKetahanan(ketahananDipilih);
        double totalHarga = hargaDasar + ketahananDipilih.getBiayaTambahan();
        parfumDipilih.setHarga(totalHarga);

        System.out.println("Ketahanan: " + ketahananDipilih.getDeskripsi());
        System.out.println("Total Harga: " + totalHarga);

        System.out.println("\n=== ITEM DITAMBAHKAN ===");
        System.out.println("Produk : " + parfumDipilih.getNamaProduk());
        System.out.println("Spek   : " + parfumDipilih.getKetahanan());
        System.out.println("Total  : Rp " + parfumDipilih.getHarga());

        ItemTransaksi item = new ItemTransaksi(parfumDipilih.getNamaProduk(), ketahananDipilih.name(), totalHarga);
        keranjang.add(item);
            
        checkout();
    }

    static void checkout() {
        // 1. Cek keranjang
        if (keranjang.isEmpty()) {
            System.out.println("\nKeranjang kosong! Pilih parfum dulu.");
            return;
        }

        // --- STRUK RAPI (Ganteng) ---
        System.out.println("\n==================================================");
        // Menggunakan format center manual agar pas di lebar 50
        System.out.println("                 PERSCENTS STORE                  ");
        System.out.println("==================================================");
        
        // Header Kolom: Item (22 char), Tipe (10 char), Harga (15 char)
        System.out.printf("%-22s   %-10s   %12s\n", "Item", "Tipe", "Harga");
        System.out.println("--------------------------------------------------");

        double totalBayar = 0;
        
        // Loop keranjang
        for (ItemTransaksi item : keranjang) {
            // Kita perlebar limit nama jadi 22 karakter biar lebih lega
            String namaPrint = item.namaItem.length() > 22 ? item.namaItem.substring(0, 22) : item.namaItem;
            
            // %-22s (Rata kiri 22 spasi), %12s (Rata kanan buat harga biar lurus)
            System.out.printf("%-22s   %-10s   Rp%,10.0f\n", namaPrint, item.spek, item.harga);
            
            totalBayar += item.harga;
        }
        
        System.out.println("--------------------------------------------------");
        // Bagian Total dibuat align ke kanan
        System.out.printf("TOTAL TAGIHAN Rp%,10.0f\n", ":", totalBayar);
        System.out.println("==================================================");

        // --- KONFIRMASI ---
        System.out.print("\nLanjut pembayaran? (Y/N): ");
        String konfirmasi = input.next();
        if (!konfirmasi.equalsIgnoreCase("Y")) return;

        boolean lunas = false;
        String metodeBayar = "";

        // --- LOOP PEMBAYARAN ---
        while (!lunas) {
            System.out.println("\n1. Cash");
            System.out.println("2. Cashless");
            System.out.print("Metode: ");
            int met = input.nextInt();

            if (met == 1) { // CASH
                metodeBayar = "Cash";
                System.out.print("Uang: Rp ");
                double uang = input.nextDouble();
                if (uang >= totalBayar) {
                    System.out.printf("Kembalian: Rp%,.0f\n", (uang - totalBayar));
                    lunas = true;
                } else {
                    System.out.printf("Kurang: Rp%,.0f\n", (totalBayar - uang));
                }
            } else if (met == 2) { // CASHLESS
                metodeBayar = "Cashless";
                System.out.println("Scan QRIS...");
                // Simulasi sukses
                lunas = true;
            }
        }

        // --- BAGIAN INI AKU RAPIHIN DIKIT BIAR MASUK DATABASE ---
        if (lunas) {
            
            // 1. BIKIN OBJECT TRANSAKSI (Biar cocok sama Service)
            Transaksi trx = new Transaksi();
            trx.setNamaPembeli("Guest");
            trx.setMetodeBayar(metodeBayar);
            trx.setTotalAkhir(totalBayar);
            
            // 2. CONVERT KERANJANG KE LIST PARFUM (Syarat Service)
            List<Parfum> itemsForDb = new ArrayList<>();
            for (ItemTransaksi it : keranjang) {
                ParfumCollection pc = new ParfumCollection();
                pc.setNamaProduk(it.namaItem);
                
                try {
                    pc.setKetahanan(TingkatKetahanan.valueOf(it.spek.toUpperCase()));
                } catch (Exception e) {
                    pc.setKetahanan(TingkatKetahanan.LIGHT); // Default kalau error
                }
                
                pc.setHarga(it.harga);
                itemsForDb.add(pc);
            }
            trx.setItems(itemsForDb);
            
            // 3. PANGGIL SERVICE
            // Kita pake variabel baru 'trxService' biar ga bentrok sama 'service' di atas
            TransaksiService trxService = new TransaksiService();
            trxService.simpanTransaksi(trx);
            
            System.out.println("\n[SUKSES] Data masuk database & Struk keluar!");
            keranjang.clear();
        }
    }
}