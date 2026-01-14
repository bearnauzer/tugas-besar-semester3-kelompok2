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
import itenas.is.perscents.service.QRService;
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
        boolean belanja = true;
        while (belanja){
            try {
            System.out.println("\n=== SELAMAT DATANG DI PERSCENTS ===");
            System.out.println("1. Parfum Pria");
            System.out.println("2. Parfum Wanita");
            System.out.println("3. Parfum Anak-anak");
            System.out.println("4. Checkout");
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
                    checkout();
                    System.exit(0);
                default:
                    System.out.println("Pilihan tidak valid!");
            }
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan input!");
            }
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
            
            System.out.println("\n[BERHASIL] Produk dimasukkan ke keranjang.");
            
            //keranjang
            
            System.out.print("\nTambah parfum lain? (Y/N): ");
            String lagi = input.next();
            if (lagi.equalsIgnoreCase("N")) {
                checkout();
            }

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
            System.out.println("1. Light (Harga Tetap)");
            System.out.println("2. Balance (+Rp 30.000)");
            System.out.println("3. Signature (+Rp 40.000)");
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

            System.out.println("\n[BERHASIL] Produk dimasukkan ke keranjang.");
            
            //keranjang
            
            System.out.print("\nTambah parfum lain? (Y/N): ");
            String lagi = input.next();
            if (lagi.equalsIgnoreCase("N")) {
                checkout();
            }
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
            
            System.out.println("\n[BERHASIL] Produk dimasukkan ke keranjang.");
            
            //keranjang
            
            System.out.print("\nTambah parfum lain? (Y/N): ");
            String lagi = input.next();
            if (lagi.equalsIgnoreCase("N")) {
                checkout();
            }
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
            System.out.println("1. Light (Harga Tetap)");
            System.out.println("2. Balance (+Rp 30.000)");
            System.out.println("3. Signature (+Rp 40.000)");
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
            
            System.out.println("\n[BERHASIL] Produk dimasukkan ke keranjang.");
            
            //keranjang
            
            System.out.print("\nTambah parfum lain? (Y/N): ");
            String lagi = input.next();
            if (lagi.equalsIgnoreCase("N")) {
                checkout();
            }
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
            
        System.out.println("\n[BERHASIL] Produk dimasukkan ke keranjang.");
            
        //keranjang
            
        System.out.print("\nTambah parfum lain? (Y/N): ");
        String lagi = input.next();
        if (lagi.equalsIgnoreCase("N")) {
            checkout();
        }
    }
    
        private static void cetakStrukFinal(Transaksi trx, double uangDibayar) {
        System.out.println("\n\n");
        System.out.println("==================================================");
        System.out.println("                PERSCENTS STORE                   ");
        System.out.println("          Jl.  PH.H. Mustofa No.23                "); 
        System.out.println("==================================================");

        // Info Header Struk
        System.out.printf(" No. Transaksi : #%d\n", trx.getIdTransaksi());
        System.out.printf(" Nama Pembeli  : %s\n", trx.getNamaPembeli());
        System.out.printf(" Metode Bayar  : %s\n", trx.getMetodeBayar());
        System.out.println("--------------------------------------------------");

        // Daftar Item
        System.out.printf("%-22s   %-10s   %12s\n", "Item", "Tipe", "Harga");
        System.out.println("--------------------------------------------------");

        for (Parfum p : trx.getItems()) {
            String namaPrint = p.getNamaProduk();
            if (namaPrint.length() > 22) namaPrint = namaPrint.substring(0, 19) + "...";

            String ketahanan = "Custom";
            if (p instanceof ParfumCollection) {
                ketahanan = ((ParfumCollection) p).getKetahanan().toString();
            }

            System.out.printf("%-22s   %-10s   Rp%,12.0f\n", namaPrint, ketahanan, p.getHarga());
        }

        System.out.println("--------------------------------------------------");
        System.out.printf(" TOTAL BELANJA                 Rp%,12.0f\n", trx.getTotalAkhir());

        if (trx.getMetodeBayar().equalsIgnoreCase("Cash")) {
            System.out.printf(" TUNAI                         Rp%,12.0f\n", uangDibayar);
            System.out.printf(" KEMBALIAN                     Rp%,12.0f\n", (uangDibayar - trx.getTotalAkhir()));
        } else {
            System.out.println(" STATUS                        LUNAS (QRIS/Debit)");
        }

        System.out.println("==================================================");
        System.out.println("        TERIMA KASIH TELAH BERBELANJA             ");
        System.out.println("          STAY FRESH WITH PERSCENTS               ");
        System.out.println("==================================================");
        System.out.println("\n");
    }

    static void checkout() {
        if (keranjang.isEmpty()) {
            System.out.println("\nKeranjang kosong! Pilih parfum dulu.");
            return;
        }

        System.out.println("\n==================================================");
        System.out.println("                 PERSCENTS STORE                  ");
        System.out.println("==================================================");
        System.out.printf("%-22s   %-10s   %12s\n", "Item", "Tipe", "Harga");
        System.out.println("--------------------------------------------------");

        double totalBayar = 0;
        
        // Loop keranjang
        for (ItemTransaksi item : keranjang) {
            String namaPrint = item.namaItem.length() > 22 ? item.namaItem.substring(0, 22) : item.namaItem;
            
            System.out.printf("%-22s   %-10s   Rp%,10.0f\n", namaPrint, item.spek, item.harga);
            
            totalBayar += item.harga;
        }
        
        System.out.println("--------------------------------------------------");
        System.out.printf("TOTAL TAGIHAN Rp%,10.0f\n", totalBayar);
        System.out.println("==================================================");

        System.out.print("\nLanjut pembayaran? (Y/N): ");
        String konfirmasi = input.next();
        if (!konfirmasi.equalsIgnoreCase("Y")) return;

        boolean lunas = false;
        String metodeBayar = "";
        double uangDiterima = 0;

        while (!lunas) {
            System.out.println("\n1. Cash");
            System.out.println("2. Cashless");
            System.out.print("Metode: ");
            int met = input.nextInt();

            if (met == 1) {
                metodeBayar = "Cash";
                System.out.print("Uang: Rp ");
                uangDiterima = input.nextDouble(); 
                if (uangDiterima >= totalBayar) {
                    lunas = true;
                } else {
                    System.out.printf("Kurang: Rp%,.0f\n", (totalBayar - uangDiterima));
                }
            } else if (met == 2) {
                metodeBayar = "Cashless";
                System.out.println("Scan QRIS...");
                lunas = true;
            }
        }

        if (lunas) {
            Transaksi trx = new Transaksi();
            trx.setNamaPembeli("Guest User"); 
            trx.setMetodeBayar(metodeBayar);
            trx.setTotalAkhir(totalBayar);
            
            List<Parfum> itemsForDb = new ArrayList<>();
            for (ItemTransaksi it : keranjang) {
                ParfumCollection pc = new ParfumCollection();
                pc.setNamaProduk(it.namaItem);
                try {
                    pc.setKetahanan(TingkatKetahanan.valueOf(it.spek.toUpperCase()));
                } catch (Exception e) {
                    pc.setKetahanan(TingkatKetahanan.LIGHT);
                }
                pc.setHarga(it.harga);
                itemsForDb.add(pc);
            }
            trx.setItems(itemsForDb);

            TransaksiService trxService = new TransaksiService();
            trxService.simpanTransaksi(trx);
            
            if (metodeBayar.equalsIgnoreCase("Cashless")) {
                QRService qr = new QRService();
                qr.generatePaymentQR(trx.getIdTransaksi(), totalBayar);
                System.out.println("-> [QRIS] File QR_Payment_" + trx.getIdTransaksi() + ".png telah dibuat di folder project.");
            }
            
            cetakStrukFinal(trx, uangDiterima);
            
            keranjang.clear();
        }
    }
}