/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.service;

import itenas.is.perscents.repository.DBconnection;
import itenas.is.perscents.model.Transaksi;
import itenas.is.perscents.model.Parfum; // Pastikan import Parfum
import itenas.is.perscents.model.ParfumCollection; // Jika perlu casting
import itenas.is.perscents.repository.DBconnection;
import itenas.is.perscents.model.CustomParfum;

import java.sql.*;

/**
 *
 * @author ASUS
 */
public class TransaksiService {

    public void simpanTransaksi(Transaksi trx) {
        String sqlHeader = "INSERT INTO transaksi (nama_pembeli, metode_bayar, total_akhir) VALUES (?, ?, ?)";
        String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, nama_item, pilihan_ketahanan, qty, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.connectDb()) {
            
            if (conn == null) {
                System.out.println("[ERROR] Gagal koneksi ke Database!");
                return;
            }

            conn.setAutoCommit(false);

            try (PreparedStatement psHeader = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS)) {
                psHeader.setString(1, trx.getNamaPembeli());
                psHeader.setString(2, trx.getMetodeBayar());
                psHeader.setDouble(3, trx.getTotalAkhir());

                int rowAffected = psHeader.executeUpdate();

                int newId = 0;
                if (rowAffected > 0) {
                    try (ResultSet rs = psHeader.getGeneratedKeys()) {
                        if (rs.next()) {
                            newId = rs.getInt(1);
                            trx.setIdTransaksi(newId);
                            System.out.println("[DEBUG] ID Transaksi Baru: " + newId);
                        }
                    }
                }
                
                try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                    for (Parfum p : trx.getItems()) {
                        psDetail.setInt(1, newId);

                        if (p instanceof ParfumCollection) {
                            ParfumCollection pc = (ParfumCollection) p;
                            psDetail.setString(2, pc.getNamaProduk());
                            psDetail.setString(3, pc.getKetahanan().toString());
                            psDetail.setInt(4, 1); 
                            psDetail.setDouble(5, pc.getHarga());
                        } else {
                            psDetail.setString(2, "Custom Parfum"); 
                            psDetail.setString(3, "Custom");
                            psDetail.setInt(4, 1);
                            psDetail.setDouble(5, p.getHarga());
                        }
                        psDetail.addBatch();
                    }
                    
                    psDetail.executeBatch();
                }
            }

            conn.commit();
            System.out.println("[SUKSES] Transaksi berhasil disimpan ke Database.");

        } catch (SQLException e) {
            System.err.println("[ERROR SQL] " + e.getMessage());
            e.printStackTrace();
        }
    }
}