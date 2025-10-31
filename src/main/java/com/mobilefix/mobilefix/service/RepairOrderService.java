package com.mobilefix.mobilefix.service;

import com.mobilefix.mobilefix.model.RepairOrder;
import com.mobilefix.mobilefix.model.User;
import com.mobilefix.mobilefix.model.Device;
import com.mobilefix.mobilefix.repository.RepairOrderRepository;
import com.mobilefix.mobilefix.repository.UserRepository;
import com.mobilefix.mobilefix.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairOrderService {

    @Autowired
    private RepairOrderRepository repairOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * Obtener todas las órdenes (solo ADMIN)
     */
    public List<RepairOrder> getAllOrders() {
        return repairOrderRepository.findAll();
    }

    /**
     * Obtener órdenes por cliente (rol USER)
     */
    public List<RepairOrder> getOrdersByCustomer(User customer) {
        return repairOrderRepository.findByCustomer(customer);
    }

    /**
     * Obtener órdenes por técnico (rol TECH)
     */
    public List<RepairOrder> getOrdersByTechnician(User technician) {
        return repairOrderRepository.findByAssignedTech(technician);
    }

    /**
     * Obtener órdenes por estado
     */
    public List<RepairOrder> getOrdersByStatus(RepairOrder.OrderStatus status) {
        return repairOrderRepository.findByStatus(status);
    }

    /**
     * Obtener orden por ID
     */
    public RepairOrder getOrderById(Long id) {
        return repairOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    /**
     * Crear nueva orden de reparación (USER)
     */
    public RepairOrder createOrder(Long userId, Long deviceId, String issueDescription) {
        // Validar longitud de la descripción
        if (issueDescription == null || issueDescription.length() < 10) {
            throw new IllegalArgumentException("Issue description must have at least 10 characters");
        }

        // Buscar el cliente
        User customer = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Buscar el dispositivo
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        // Crear la orden
        RepairOrder order = new RepairOrder();
        order.setCustomer(customer);
        order.setDevice(device);
        order.setIssueDescription(issueDescription);
        order.setStatus(RepairOrder.OrderStatus.PENDING);

        return repairOrderRepository.save(order);
    }

    /**
     * Asignar técnico a una orden (ADMIN)
     */
    public RepairOrder assignTech(Long orderId, Long techId) {
        RepairOrder order = repairOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        User technician = userRepository.findById(techId)
                .orElseThrow(() -> new IllegalArgumentException("Technician not found"));

        // Validar que el usuario sea técnico
        if (technician.getRole() != User.Role.TECH) {
            throw new IllegalArgumentException("User is not a technician");
        }

        order.setAssignedTech(technician);
        return repairOrderRepository.save(order);
    }

    /**
     * Cambiar estado de una orden (TECH o ADMIN)
     */
    public RepairOrder changeStatus(Long orderId, RepairOrder.OrderStatus newStatus, String techNotes) {
        RepairOrder order = repairOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Validar transición de estados
        validateStatusTransition(order.getStatus(), newStatus);

        order.setStatus(newStatus);

        // Agregar notas técnicas si vienen
        if (techNotes != null && !techNotes.isBlank()) {
            order.setTechNotes(techNotes);
        }

        return repairOrderRepository.save(order);
    }

    /**
     * Validar transiciones de estado permitidas
     */
    private void validateStatusTransition(RepairOrder.OrderStatus current, RepairOrder.OrderStatus next) {
        // No se puede cambiar el estado si ya está cancelado o entregado
        if (current == RepairOrder.OrderStatus.CANCELED || current == RepairOrder.OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot change status from " + current);
        }

        // Validar transiciones válidas según el estado actual
        switch (current) {
            case PENDING:
                if (next != RepairOrder.OrderStatus.IN_PROGRESS && next != RepairOrder.OrderStatus.CANCELED) {
                    throw new IllegalStateException("From PENDING can only go to IN_PROGRESS or CANCELED");
                }
                break;

            case IN_PROGRESS:
                if (next != RepairOrder.OrderStatus.READY && next != RepairOrder.OrderStatus.CANCELED) {
                    throw new IllegalStateException("From IN_PROGRESS can only go to READY or CANCELED");
                }
                break;

            case READY:
                if (next != RepairOrder.OrderStatus.DELIVERED) {
                    throw new IllegalStateException("From READY can only go to DELIVERED");
                }
                break;

            default:
                throw new IllegalStateException("Invalid status transition");
        }
    }

    /**
     * Eliminar orden (USER si PENDING y es propia, o ADMIN)
     */
    public void deleteOrder(Long orderId, User currentUser) {
        RepairOrder order = repairOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        boolean isAdmin = currentUser.getRole() == User.Role.ADMIN;
        boolean isOwner = order.getCustomer().getId().equals(currentUser.getId());
        boolean isPending = order.getStatus() == RepairOrder.OrderStatus.PENDING;

        if (isAdmin || (isOwner && isPending)) {
            repairOrderRepository.deleteById(orderId);
        } else {
            throw new AccessDeniedException("Not authorized to delete this order");
        }
    }
}
