/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.service;

import itenas.is.perscents.repository.DBconnection;
import itenas.is.perscents.model.Transaksi;
import itenas.is.perscents.model.Parfum;
import itenas.is.perscents.model.ParfumCollection;
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

        Connection conn = null;

        try {
            conn = DBconnection.connectDb();
            if (conn == null) {
                System.out.println("[ERROR] Gagal koneksi ke Database!");
                return;
            }

            conn.setAutoCommit(false);

            // insert tabel transaksi
            try (PreparedStatement psHeader = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS)) {
                psHeader.setString(1, trx.getNamaPembeli());
                psHeader.setString(2, trx.getMetodeBayar());
                psHeader.setDouble(3, trx.getTotalAkhir());
                psHeader.executeUpdate();

                int newId = 0;
                try (ResultSet rs = psHeader.getGeneratedKeys()) {
                    if (rs.next()) {
                        newId = rs.getInt(1);
                        trx.setIdTransaksi(newId);
                    }
                }

                // insert detail
                try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                    for (Parfum p : trx.getItems()) {
                        psDetail.setInt(1, newId);

                        if (p instanceof ParfumCollection) {
                            ParfumCollection pc = (ParfumCollection) p;
                            psDetail.setString(2, pc.getNamaProduk());
                            psDetail.setString(3, pc.getKetahanan().toString());
                        } else {
                            psDetail.setString(2, "Custom Parfum"); 
                            psDetail.setString(3, "Custom");
                        }

                        psDetail.setInt(4, 1);
                        psDetail.setDouble(5, p.getHarga());
                        psDetail.addBatch();
                    }
                    psDetail.executeBatch();
                }

                conn.commit();
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transaksi dibatalkan karena error.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("[ERROR SQL] " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}