/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.view;

import itenas.is.perscents.model.Parfum;
import itenas.is.perscents.model.ParfumCollection;
import itenas.is.perscents.model.TingkatKetahanan;
import itenas.is.perscents.service.ParfumService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**1
 * 1
 * 
 *
 * @author ASUS
 */
public class Main {
    static Scanner input = new Scanner(System.in);
    static ParfumService service = new ParfumService();


    public static void main(String[] args) {

        System.out.println("\n=== MAIN MENU PERSCENTS ===");
        System.out.println("1. Menu Pembeli");
        System.out.println("2. Menu Admin");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("Pilih Menu: ");
            
        int menu = input.nextInt();
        input.nextLine(); 

        switch (menu) {
            case 1:
                PembeliMenu pembeli = new PembeliMenu();
                pembeli.showMenuPembeli();
                break;
            case 2:
                boolean loginGagal = true;
                do {
                    System.out.println("---LOGIN ADMIN---");
                    System.out.print("masukkan username : ");
                    String username = input.nextLine();
                    System.out.print("masukkan password : ");
                    String password = input.nextLine();

                    String usnBenar = "admin";
                    String passwordBenar = "andesoop";

                    if (username.equals(usnBenar) && password.equals(passwordBenar)) {
                        System.out.println("Login Berhasil!");
                        loginGagal = false;

                        AdminMenu admin = new AdminMenu();
                        admin.showMenu();
                    } else {
                        System.out.println("Username atau Password salah. Silakan coba lagi.");
                    }
                } while (loginGagal);
                break;
            case 0:
                System.out.println("Bye bye!");
                System.exit(0);
                break;
            default:
                System.out.println("Menu tidak ada.");
        }
    }
}

    
