/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.service;

import itenas.is.perscents.model.Parfum;
import itenas.is.perscents.repository.ParfumRepository;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ParfumService {
    private ParfumRepository repo = new ParfumRepository();
    
    public void lihatMenuBerdasarkanKategori(String kategoriDipilih) {
        List<Parfum> semuaParfum = repo.findAll();
        
        System.out.println("\n=== KATALOG PARFUM " + kategoriDipilih.toUpperCase() + " ===");
        boolean adaData = false;
        
        for (Parfum p : semuaParfum) {
            if (p.getKategori().equalsIgnoreCase(kategoriDipilih)) {
                p.tampilkanInfo();
                adaData = true;
            }
        }
        
        if (!adaData) {
            System.out.println("Maaf, belum ada produk untuk kategori ini.");
        }
    }
    
    public List<Parfum> ambilListParfum(String kategoriDipilih) {
        List<Parfum> semuaParfum = repo.findAll();
        List<Parfum> hasilFilter = new ArrayList<>();

        for (Parfum p : semuaParfum) {
            if (p.getKategori().equalsIgnoreCase(kategoriDipilih)) {
                hasilFilter.add(p);
            }
        }
        return hasilFilter;
    }

    public void simpanTransaksi(String toString, double totalBayar, String metodeBayar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}