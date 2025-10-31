package com.mobilefix.mobilefix.repository;

import com.mobilefix.mobilefix.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    // Buscar por número de serie
    Optional<Device> findBySerialNumber(String serialNumber);

    // Verificar si existe un número de serie
    boolean existsBySerialNumber(String serialNumber);
}