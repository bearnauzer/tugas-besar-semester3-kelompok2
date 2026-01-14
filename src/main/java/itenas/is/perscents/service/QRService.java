/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *
 * @author ASUS
 */
public class QRService {
    public void generatePaymentQR(int idTrx, double total) {
        String content = "PERSCENTS-PAY-" + idTrx + "-TOTAL-" + total;
        String filePath = "QR_Payment_" + idTrx + ".png";
        
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            
            System.out.println("[INFO] QR Code Pembayaran dibuat: " + filePath);
        } catch (Exception e) {
            System.err.println("[ERROR QR] " + e.getMessage());
        }
    }
}
