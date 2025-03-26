package com.restaurant.rms.service.diningTableService;




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

public CreateDiningTableDTO createDiningTable(CreateDiningTableDTO createDiningTableDTO) {
    // Tìm nhà hàng theo ID
    Restaurant restaurant = restaurantRepository.findById(createDiningTableDTO.getRestaurantId())
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));

    // **Tạo mã QR Code trước**
    String qrContent = "http://localhost:5173/"
            + createDiningTableDTO.getRestaurantId()
            + "&tableId="
            + UUID.randomUUID(); // Tạo mã QR duy nhất

    String qrCodePath = "qrtable_" + UUID.randomUUID() + ".png";

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

//    public void deleteDiningTable(int id) {
//        diningTableRepository.deleteById(id);
//    }
//
//    public DiningTableDTO findByQrCode(String qrCode) {
//        DiningTable diningTable = diningTableRepository.findByQrCode(qrCode)
//                .orElseThrow(() -> new RuntimeException("Dining Table not found"));
//        return DiningTableMapper.toDTO(diningTable);
//    }
public DiningTableDTO findByQrCode(String qrCode) {
    DiningTable diningTable = diningTableRepository.findByQrCode(qrCode)
            .orElseThrow(() -> new RuntimeException("Dining Table not found or has been deleted"));
    return DiningTableMapper.toDTO(diningTable);
}

    // Xóa mềm
    public void deleteDiningTable(int id) {
        DiningTable diningTable = diningTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dining Table not found or has been deleted"));
        diningTable.setDeleted(true);
        diningTableRepository.save(diningTable);
    }

    // Lấy danh sách bàn đã xóa mềm
    public List<DiningTableDTO> getAllDeletedDiningTables() {
        return diningTableRepository.findAllDeleted().stream()
                .map(DiningTableMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Phục hồi bàn đã xóa mềm
    public DiningTableDTO restoreDiningTable(int id) {
        DiningTable diningTable = diningTableRepository.findDeletedById(id)
                .orElseThrow(() -> new RuntimeException("Deleted Dining Table not found"));
        diningTable.setDeleted(false);
        return DiningTableMapper.toDTO(diningTableRepository.save(diningTable));
    }

    public List<DiningTableDTO> getDiningTablesByRestaurantId(int restaurantId) {
        List<DiningTable> diningTables = diningTableRepository.findByRestaurant_RestaurantId(restaurantId);

        if (diningTables.isEmpty()) {
            throw new RuntimeException("Không có bàn nào trong nhà hàng ID: " + restaurantId);
        }

        return diningTables.stream()
                .map(DiningTableMapper::toDTO)
                .collect(Collectors.toList());
    }


}