package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.CreateDiningTableDTO;
import com.restaurant.rms.dto.request.DiningTableDTO;
import com.restaurant.rms.service.diningTableService.DiningTableService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/dining_table")
@SecurityRequirement(name = "api")
public class DiningTableController {

    private final DiningTableService diningTableService;

    @GetMapping
    public ResponseEntity<List<DiningTableDTO>> getAllDiningTables() {
        return ResponseEntity.ok(diningTableService.getAllDiningTables());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiningTableDTO> getDiningTableById(@PathVariable int id) {
        return ResponseEntity.ok(diningTableService.getDiningTableById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<DiningTableDTO>> getDiningTablesByRestaurantId(@PathVariable int restaurantId) {
        List<DiningTableDTO> diningTables = diningTableService.getDiningTablesByRestaurantId(restaurantId);
        return ResponseEntity.ok(diningTables);
    }

    @PostMapping
    public ResponseEntity<CreateDiningTableDTO> createDiningTable(@RequestBody CreateDiningTableDTO createDiningTableDTO) {
        CreateDiningTableDTO createdDiningTable = diningTableService.createDiningTable(createDiningTableDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiningTable);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DiningTableDTO> updateDiningTable(@PathVariable int id, @RequestBody DiningTableDTO diningTableDTO) {
        return ResponseEntity.ok(diningTableService.updateDiningTable(id, diningTableDTO));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteDiningTable(@PathVariable int id) {
//        diningTableService.deleteDiningTable(id);
//        return ResponseEntity.ok("Dining Table deleted successfully");
//    }
// Xóa mềm
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteDiningTable(@PathVariable int id) {
    diningTableService.deleteDiningTable(id);
    return ResponseEntity.ok("Dining Table soft deleted successfully");
}

    // Lấy danh sách bàn đã xóa mềm
    @GetMapping("/deleted")
    public ResponseEntity<List<DiningTableDTO>> getAllDeletedDiningTables() {
        return ResponseEntity.ok(diningTableService.getAllDeletedDiningTables());
    }

    // Phục hồi bàn đã xóa mềm
    @PutMapping("/restore/{id}")
    public ResponseEntity<DiningTableDTO> restoreDiningTable(@PathVariable int id) {
        return ResponseEntity.ok(diningTableService.restoreDiningTable(id));
    }

    @GetMapping("/qr/{qrCode}")
    public ResponseEntity<DiningTableDTO> getDiningTableByQrCode(@PathVariable String qrCode) {
        return ResponseEntity.ok(diningTableService.findByQrCode(qrCode));
    }
}