package com.restaurant.rms.repository;

import com.restaurant.rms.entity.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DiningTableRepository extends JpaRepository<DiningTable, Integer> {
    Optional<DiningTable> findByQrCode(String qrCode);
}