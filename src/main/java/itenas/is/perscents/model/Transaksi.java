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
public class Transaksi {
    private int idTransaksi;
    private String namaPembeli;
    private String metodeBayar;
    private double totalAkhir;
    private List<Parfum> items;

    public Transaksi() { }

    public Transaksi(String namaPembeli, String metodeBayar, double totalAkhir, List<Parfum> items) {
        this.namaPembeli = namaPembeli;
        this.metodeBayar = metodeBayar;
        this.totalAkhir = totalAkhir;
        this.items = items;
    }

    public int getIdTransaksi() { 
        return idTransaksi; 
    }
    
    public void setIdTransaksi(int idTransaksi) { 
        this.idTransaksi = idTransaksi; 
    }

    public String getNamaPembeli() { 
        return namaPembeli; 
    }
    
    public void setNamaPembeli(String namaPembeli) { 
        this.namaPembeli = namaPembeli; 
    }

    public String getMetodeBayar() { 
        return metodeBayar; 
    }
    
    public void setMetodeBayar(String metodeBayar) { 
        this.metodeBayar = metodeBayar; 
    }

    public double getTotalAkhir() { 
        return totalAkhir; 
    }
    
    public void setTotalAkhir(double totalAkhir) { 
        this.totalAkhir = totalAkhir; 
    }

    public List<Parfum> getItems() { 
        return items; 
    }
    
    public void setItems(List<Parfum> items) { 
        this.items = items; 
    }
}