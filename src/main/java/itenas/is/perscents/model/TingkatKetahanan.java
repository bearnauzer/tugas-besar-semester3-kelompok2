package itenas.is.perscents.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public enum TingkatKetahanan {
    LIGHT("Kadar alkohol tinggi - aroma lebih ringan", 0),
    BALANCE("Kadar alkohol sedang - seimbang", 30000),
    SIGNATURE("Kadar alkohol rendah - kualitas terbaik", 40000),
    BABY_CARE("Non Alcohol - Sangat Aman", 35000),
    KIDS_DAILY("Very Low Alcohol - Aman Sehari-hari", 25000);
    
    private final String deskripsi;
    private final double biayaTambahan;

    TingkatKetahanan(String deskripsi, double biayaTambahan) {
        this.deskripsi = deskripsi;
        this.biayaTambahan = biayaTambahan;
    }

    public double getBiayaTambahan() {
        return biayaTambahan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
    
}
