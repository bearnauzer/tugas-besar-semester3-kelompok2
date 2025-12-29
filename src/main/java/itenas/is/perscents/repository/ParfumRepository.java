/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.repository;

import itenas.is.perscents.model.Parfum;
import itenas.is.perscents.model.ParfumCollection;
import itenas.is.perscents.model.TingkatKetahanan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ParfumRepository implements CrudRepository<Parfum> {

    //BUAT READ
    @Override
    public List<Parfum> findAll() {
        List<Parfum> listParfum = new ArrayList<>();
        String query = "SELECT * FROM parfum_collection";
        
        try (Connection conn = DBconnection.connectDb(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                // Kita simpan ID juga untuk keperluan Edit/Hapus
                int id = rs.getInt("id"); 
                String kode = rs.getString("kode_karakter");
                String tema = rs.getString("tema_karakter");
                String nama = rs.getString("nama_produk");
                String kat = rs.getString("kategori");
                double harga = rs.getDouble("harga");
                String desk = rs.getString("deskripsi_wangi");
                
                
                ParfumCollection p = new ParfumCollection(kode, tema, nama, kat, harga, desk);
                listParfum.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error Read: " + e.getMessage());
        }
        return listParfum;
    }

    // BUAT CREATE
    public void insert(ParfumCollection p) {
        String query = "INSERT INTO parfum_collection (kode_karakter, tema_karakter, nama_produk, kategori, harga, deskripsi_wangi) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBconnection.connectDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, p.getKodeKarakter());
            ps.setString(2, p.getTemaKarakter());
            ps.setString(3, p.getNamaProduk());
            ps.setString(4, p.getKategori());
            ps.setDouble(5, p.getHarga());
            ps.setString(6, p.getDeskripsi());
            
            ps.executeUpdate();
            System.out.println("Sukses menambahkan parfum: " + p.getNamaProduk());
            
        } catch (SQLException e) {
            System.out.println("Gagal Insert: " + e.getMessage());
        }
    }

    // BUAT UPDATE 
    public void updateHarga(String namaProduk, double hargaBaru) {
        String query = "UPDATE parfum_collection SET harga = ? WHERE nama_produk = ?";
        
        try (Connection conn = DBconnection.connectDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setDouble(1, hargaBaru);
            ps.setString(2, namaProduk);
            
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Harga berhasil diupdate!");
            else System.out.println("Parfum tidak ditemukan.");
            
        } catch (SQLException e) {
            System.out.println("Gagal Update: " + e.getMessage());
        }
    }

    // BUAT DELETE
    public void delete(String namaProduk) {
        String query = "DELETE FROM parfum_collection WHERE nama_produk = ?";
        
        try (Connection conn = DBconnection.connectDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, namaProduk);
            
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Parfum berhasil dihapus.");
            else System.out.println("Parfum tidak ditemukan.");
            
        } catch (SQLException e) {
            System.out.println("Gagal Delete: " + e.getMessage());
        }
    }
}