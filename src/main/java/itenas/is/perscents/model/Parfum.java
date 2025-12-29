/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.model;

/**
 *
 * @author ASUS
 */
public abstract class Parfum { 
    private String namaProduk; 
    private String kategori;
    private TingkatKetahanan ketahanan;
    private String deskripsi;
    protected double harga; 
    
    public Parfum(){} 

    public Parfum(String namaProduk, String kategori, TingkatKetahanan ketahanan, String deskripsi) {
        this.namaProduk = namaProduk;
        this.kategori = kategori;
        this.ketahanan = ketahanan;
        this.deskripsi = deskripsi;
        this.harga = 0;
    }

    public String getNamaProduk() { 
        return namaProduk; 
    }
    
    public void setNamaProduk(String namaProduk) { 
        this.namaProduk = namaProduk; 
    }

    public String getKategori() { 
        return kategori; 
    }
    
    public void setKategori(String kategori) { 
        this.kategori = kategori; 
    }

    public TingkatKetahanan getKetahanan() { 
        return ketahanan; 
    }
    
    public void setKetahanan(TingkatKetahanan ketahanan) { 
        this.ketahanan = ketahanan; 
    }

    public double getHarga() { 
        return harga; 
    }
    
    public void setHarga(double harga) { 
        this.harga = harga; 
    }
    
    public String getDeskripsi() { 
        return deskripsi; 
    }
    
    public abstract double hitungHarga();
    public void tampilkanInfo(){}
}