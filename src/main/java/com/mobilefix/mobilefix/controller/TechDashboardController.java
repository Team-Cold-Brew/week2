package com.mobilefix.mobilefix.controller;

import com.mobilefix.mobilefix.model.RepairOrder;
import com.mobilefix.mobilefix.model.User;
import com.mobilefix.mobilefix.service.RepairOrderService;
import com.mobilefix.mobilefix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tech")
public class TechDashboardController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Dashboard del TECH - Ver órdenes asignadas
     */
    @GetMapping("/dashboard")
    public String techDashboard(Model model, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Obtener órdenes asignadas al técnico
        List<RepairOrder> assignedOrders = repairOrderService.getOrdersByTechnician(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("assignedOrders", assignedOrders);

        return "tech/dashboard";
    }

    /**
     * Ver detalle de una orden asignada
     */
    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RepairOrder order = repairOrderService.getOrderById(id);

        // Validar que la orden esté asignada al técnico
        if (order.getAssignedTech() == null ||
                !order.getAssignedTech().getId().equals(currentUser.getId())) {
            return "redirect:/tech/dashboard?error=unauthorized";
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("order", order);

        return "tech/order-detail";
    }
}