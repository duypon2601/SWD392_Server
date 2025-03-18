package com.restaurant.rms.service.diningTableService;



import com.google.zxing.WriterException;
import com.restaurant.rms.dto.request.CreateDiningTableDTO;
import com.restaurant.rms.dto.request.DiningTableDTO;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.DiningTable;

import com.restaurant.rms.mapper.DiningTableMapper;
import com.restaurant.rms.repository.RestaurantRepository;
import com.restaurant.rms.repository.DiningTableRepository;
import com.restaurant.rms.util.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiningTableService {

    private final DiningTableRepository diningTableRepository;
    private final RestaurantRepository restaurantRepository;
    private final QRCodeService qrCodeService;

    public List<DiningTableDTO> getAllDiningTables() {
        return diningTableRepository.findAll().stream()
                .map(DiningTableMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DiningTableDTO getDiningTableById(int id) {
        DiningTable diningTable = diningTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dining Table not found"));
        return DiningTableMapper.toDTO(diningTable);
    }


//    public CreateDiningTableDTO createDiningTable(CreateDiningTableDTO createDiningTableDTO) {
//        Restaurant restaurant = restaurantRepository.findById(createDiningTableDTO.getRestaurantId())
//                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
//
//        // Generate a temporary QR code using UUID
//        String qrCode = generateQRCode(restaurant.getRestaurantId());
//
//        // Create entity
//        DiningTable diningTable = new DiningTable();
//        diningTable.setStatus(createDiningTableDTO.getStatus());
//        diningTable.setRestaurant(restaurant);
//        diningTable.setQrCode(qrCode); // ✅ Set QR before saving
//
//        // Save table with QR code
//        DiningTable savedDiningTable = diningTableRepository.save(diningTable);
//
//        return new CreateDiningTableDTO(
//                savedDiningTable.getDiningTableId(),
//                savedDiningTable.getStatus(),
//                savedDiningTable.getRestaurant().getRestaurantId()
//        );
//    }
public CreateDiningTableDTO createDiningTable(CreateDiningTableDTO createDiningTableDTO) {
    // Tìm nhà hàng theo ID
    Restaurant restaurant = restaurantRepository.findById(createDiningTableDTO.getRestaurantId())
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));

    // **Tạo mã QR Code trước**
    String qrContent = "https://your-restaurant.com/menu?restaurantId="
            + createDiningTableDTO.getRestaurantId()
            + "&tableId="
            + UUID.randomUUID(); // Tạo mã QR duy nhất

    String qrCodePath = "qrcodes/table_" + UUID.randomUUID() + ".png";

    try {
        QRCodeGenerator.generateQRCode(qrContent, qrCodePath);
    } catch (Exception e) {
        throw new RuntimeException("Failed to generate QR code", e);
    }

    // **Tạo bàn ăn**
    DiningTable diningTable = new DiningTable();
    diningTable.setStatus(createDiningTableDTO.getStatus());
    diningTable.setRestaurant(restaurant);
    diningTable.setQrCode(qrCodePath); // Set QR trước khi lưu

    // **Lưu vào DB**
    DiningTable savedDiningTable = diningTableRepository.save(diningTable);

    return new CreateDiningTableDTO(
            savedDiningTable.getDiningTableId(),
            savedDiningTable.getStatus(),
            savedDiningTable.getRestaurant().getRestaurantId()
    );
}




    public DiningTableDTO updateDiningTable(int id, DiningTableDTO diningTableDTO) {
        DiningTable existingDiningTable = diningTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dining Table not found"));

        existingDiningTable.setQrCode(diningTableDTO.getQrCode());
        existingDiningTable.setStatus(diningTableDTO.getStatus());

        return DiningTableMapper.toDTO(diningTableRepository.save(existingDiningTable));
    }

    public void deleteDiningTable(int id) {
        diningTableRepository.deleteById(id);
    }

    public DiningTableDTO findByQrCode(String qrCode) {
        DiningTable diningTable = diningTableRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new RuntimeException("Dining Table not found"));
        return DiningTableMapper.toDTO(diningTable);
    }

//    public DiningTable createDiningTable(DiningTable diningTable) throws WriterException, IOException {
//        // URL truy cập menu khi quét QR
//        String menuUrl = "https://thanh-san.github.io/Moon-HotPot/menu?table=" + diningTable.getDiningTableId();
//
//        // Tạo mã QR
//        String qrCode = qrCodeService.generateQRCode(String.valueOf(diningTable.getDiningTableId()), menuUrl);
//
//        diningTable.setQrCode(qrCode);
//        return diningTableRepository.save(diningTable);
//    }
}