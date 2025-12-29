/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class DBconnection {
    private Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/perscents";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "basdat2025";
    
    public DBconnection() {
        try {
            Class.forName(DRIVER);
            System.out.println("Driver berhasil dimuat.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error saat memuat driver: " + e.getMessage());
        }
    }
        public static Connection connectDb() {
            Connection conn = null;
            try {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Koneksi Gagal: " + e.getMessage());
            }
        return conn;
        }

    public void disconnectDb(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Koneksi berhasil ditutup.");
            } catch (SQLException e) {
                System.err.println("Error saat menutup koneksi: " + e.getMessage());
            }
        }
    }
}
