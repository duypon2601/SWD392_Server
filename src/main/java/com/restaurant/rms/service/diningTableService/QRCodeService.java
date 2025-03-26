package com.restaurant.rms.service.diningTableService;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class QRCodeService {
    private static final String QR_CODE_DIRECTORY = "qrcodes/";
    private static final String BASE_URL = "http://localhost:5173/";

    public String generateQRCode(String qrIdentifier) throws WriterException, IOException {
        String fileName = qrIdentifier + ".png"; // Tên file: qrIdentifier + .png
        String qrContent = BASE_URL + qrIdentifier; // Nội dung QR: BASE_URL + qrIdentifier
        log.info("Generating QR code with content: {}, file: {}", qrContent, fileName);

        // Tạo thư mục nếu chưa tồn tại
        Path directoryPath = Paths.get(QR_CODE_DIRECTORY);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
            log.info("Created QR code directory: {}", QR_CODE_DIRECTORY);
        }

        Path filePath = Paths.get(QR_CODE_DIRECTORY + fileName);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);

        try {
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);
            log.info("QR code generated successfully at: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to write QR code to path: {}", filePath, e);
            throw e;
        }

        return qrIdentifier; // Trả về qrIdentifier (không có .png)
    }
}