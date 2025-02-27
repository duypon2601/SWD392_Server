package com.restaurant.rms.service.diningTableService;

import com.google.zxing.BarcodeFormat;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
public class QRCodeService {
    private static final String QR_CODE_DIRECTORY = "src/main/resources/qrcodes/";

    public String generateQRCode(String tableId, String url) throws WriterException, IOException {
        String fileName = "table_" + tableId + ".png";
        Path path = FileSystems.getDefault().getPath(QR_CODE_DIRECTORY + fileName);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return fileName;
    }
}
