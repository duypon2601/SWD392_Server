package com.restaurant.rms.service.diningTableService;



import com.restaurant.rms.dto.request.DiningTableDTO;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.DiningTable;

import com.restaurant.rms.mapper.DiningTableMapper;
import com.restaurant.rms.repository.RestaurantRepository;
import com.restaurant.rms.repository.DiningTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiningTableService {

    private final DiningTableRepository diningTableRepository;
    private final RestaurantRepository restaurantRepository;

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

    public DiningTableDTO createDiningTable(DiningTableDTO diningTableDTO) {
        Restaurant restaurant = restaurantRepository.findById(diningTableDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        DiningTable diningTable = DiningTableMapper.toEntity(diningTableDTO);
        diningTable.setRestaurant(restaurant);

        return DiningTableMapper.toDTO(diningTableRepository.save(diningTable));
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
}