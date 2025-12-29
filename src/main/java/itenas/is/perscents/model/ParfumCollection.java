package itenas.is.perscents.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public class ParfumCollection extends Parfum {
    private String kodeKarakter;
    private String temaKarakter;
    
    public ParfumCollection() {
    }

    // Constructor
    public ParfumCollection(String kodeKarakter, String temaKarakter, String namaProduk, String kategori, double harga, String deskripsi) {
        super(namaProduk, kategori, null, deskripsi);
        this.kodeKarakter = kodeKarakter;
        this.temaKarakter = temaKarakter;
        this.setHarga(harga);
    }

    public String getKodeKarakter() { 
        return kodeKarakter; 
    }
    
    public String getTemaKarakter() {
        return temaKarakter; 
    }
    
    @Override
    public double hitungHarga() {
        if (this.getKategori().equalsIgnoreCase("Anak-anak")) {
            return 110000; 
        } else {
            double hargaDasar = 160000;
            double biayaTambahan = (getKetahanan() != null) ? getKetahanan().getBiayaTambahan() : 0;
            return hargaDasar + biayaTambahan;
        }
    }
    
    @Override
    public void tampilkanInfo() {
        System.out.println("=== " + getNamaProduk().toUpperCase() + " ===");
        System.out.println("Kategori    : " + getKategori());
        System.out.println("Karakter    : " + kodeKarakter + " (" + temaKarakter + ")");
        System.out.println("Deskripsi   : " + getDeskripsi());
        
        // Cek null biar ga error saat print
        String infoKetahanan = (getKetahanan() != null) ? getKetahanan().getDeskripsi() : "Belum dipilih";
        System.out.println("Ketahanan   : " + infoKetahanan);
        System.out.println("----------------------------------------");
    }
}