package com.mobilefix.mobilefix.controller;

import com.mobilefix.mobilefix.model.RepairOrder;
import com.mobilefix.mobilefix.model.User;
import com.mobilefix.mobilefix.service.RepairOrderService;
import com.mobilefix.mobilefix.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class RepairOrderRestController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private UserRepository userRepository;

    /**
     * GET /api/orders - Obtener órdenes según el rol
     */
    @GetMapping
    public ResponseEntity<?> getOrders(
            @RequestParam(required = false) String status,
            Authentication auth) {
        try {
            User currentUser = userRepository.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<RepairOrder> orders;

            switch (currentUser.getRole()) {
                case ADMIN:
                    orders = status != null
                            ? repairOrderService.getOrdersByStatus(RepairOrder.OrderStatus.valueOf(status))
                            : repairOrderService.getAllOrders();
                    break;
                case TECH:
                    orders = repairOrderService.getOrdersByTechnician(currentUser);
                    break;
                case USER:
                    orders = repairOrderService.getOrdersByCustomer(currentUser);
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(createError("Invalid role"));
            }

            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createError(e.getMessage()));
        }
    }

    /**
     * POST /api/orders - Crear nueva orden (USER)
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid CreateOrderRequest request,
            Authentication auth) {
        try {
            User currentUser = userRepository.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            RepairOrder order = repairOrderService.createOrder(
                    currentUser.getId(),
                    request.getDeviceId(),
                    request.getIssueDescription()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createError(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createError(e.getMessage()));
        }
    }

    /**
     * PUT /api/orders/{id}/assign/{techId} - Asignar técnico (ADMIN)
     */
    @PutMapping("/{id}/assign/{techId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignTech(
            @PathVariable Long id,
            @PathVariable Long techId) {
        try {
            RepairOrder order = repairOrderService.assignTech(id, techId);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createError(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createError(e.getMessage()));
        }
    }

    /**
     * PUT /api/orders/{id}/status - Cambiar estado (TECH/ADMIN)
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('TECH', 'ADMIN')")
    public ResponseEntity<?> changeStatus(
            @PathVariable Long id,
            @RequestBody @Valid ChangeStatusRequest request) {
        try {
            RepairOrder order = repairOrderService.changeStatus(
                    id,
                    request.getStatus(),
                    request.getTechNotes()
            );
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createError(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createError(e.getMessage()));
        }
    }

    /**
     * DELETE /api/orders/{id} - Eliminar orden (USER si PENDING, o ADMIN)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(
            @PathVariable Long id,
            Authentication auth) {
        try {
            User currentUser = userRepository.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            repairOrderService.deleteOrder(id, currentUser);
            return ResponseEntity.ok(createSuccess("Order deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createError(e.getMessage()));
        } catch (org.springframework.security.access.AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(createError("Not authorized to delete this order"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createError(e.getMessage()));
        }
    }

    // DTOs internos
    static class CreateOrderRequest {
        private Long deviceId;
        private String issueDescription;

        public Long getDeviceId() { return deviceId; }
        public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
        public String getIssueDescription() { return issueDescription; }
        public void setIssueDescription(String issueDescription) { this.issueDescription = issueDescription; }
    }

    static class ChangeStatusRequest {
        private RepairOrder.OrderStatus status;
        private String techNotes;

        public RepairOrder.OrderStatus getStatus() { return status; }
        public void setStatus(RepairOrder.OrderStatus status) { this.status = status; }
        public String getTechNotes() { return techNotes; }
        public void setTechNotes(String techNotes) { this.techNotes = techNotes; }
    }

    // Helpers
    private Map<String, String> createError(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }

    private Map<String, String> createSuccess(String message) {
        Map<String, String> success = new HashMap<>();
        success.put("message", message);
        return success;
    }
}