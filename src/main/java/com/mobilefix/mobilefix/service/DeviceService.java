package com.mobilefix.mobilefix.service;

import com.mobilefix.mobilefix.model.Device;
import com.mobilefix.mobilefix.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * Obtener todos los dispositivos
     */
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    /**
     * Obtener dispositivo por ID
     */
    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    /**
     * Crear nuevo dispositivo (ADMIN)
     */
    public Device createDevice(Device device) {
        // Validar que el serial number no exista (si se proporciona)
        if (device.getSerialNumber() != null && !device.getSerialNumber().isBlank()) {
            if (deviceRepository.existsBySerialNumber(device.getSerialNumber())) {
                throw new IllegalArgumentException("Device with that serial number already exists");
            }
        }

        return deviceRepository.save(device);
    }

    /**
     * Actualizar dispositivo (ADMIN)
     */
    public Device updateDevice(Long id, Device newData) {
        Device existing = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        // Validar serial number único si se cambia
        if (newData.getSerialNumber() != null && !newData.getSerialNumber().equals(existing.getSerialNumber())) {
            if (deviceRepository.existsBySerialNumber(newData.getSerialNumber())) {
                throw new IllegalArgumentException("Device with that serial number already exists");
            }
        }

        existing.setBrand(newData.getBrand());
        existing.setModel(newData.getModel());
        existing.setSerialNumber(newData.getSerialNumber());

        return deviceRepository.save(existing);
    }

    /**
     * Eliminar dispositivo (ADMIN)
     */
    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new IllegalArgumentException("Device not found");
        }
        deviceRepository.deleteById(id);
    }
}
