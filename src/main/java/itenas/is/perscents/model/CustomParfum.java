/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.model;

import java.util.List;

/**
 *
 * @author ASUS
 */
public class CustomParfum extends Parfum {
    private List<String> daftarWangi; 

    public CustomParfum(String nama, String kategori, TingkatKetahanan ketahanan, List<String> daftarWangi) {
        super(nama, kategori, ketahanan, "Custom Mix: " + daftarWangi.toString());
        //MAKSIMAL 3 WANGI
        if (daftarWangi.size() > 3) {
            System.out.println("Warning: Maksimal 3 wangi! Mengambil 3 pertama saja.");
            this.daftarWangi = daftarWangi.subList(0, 3);
        } else {
            this.daftarWangi = daftarWangi;
        }
    }

    @Override
    public double hitungHarga() {
        return 160000; 
    }
    
    @Override
    public void tampilkanInfo() {
        System.out.println("=== CUSTOM PERFUME ===");
        System.out.println("Mix Aroma    : " + daftarWangi.toString());
        System.out.println("Total Harga  : Rp " + getHarga()); // Pake getHarga() biar update
    }
}