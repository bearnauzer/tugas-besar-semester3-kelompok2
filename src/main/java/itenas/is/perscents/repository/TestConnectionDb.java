/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.repository;

import java.sql.Connection;

/**
 *
 * @author ASUS
 */
public class TestConnectionDb {
    public static void main(String[] args) {
        DBconnection connMan = new DBconnection();
        Connection conn = connMan.connectDb();
        connMan.disconnectDb(conn);
    }
}