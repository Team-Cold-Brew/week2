package com.mobilefix.mobilefix.controller;

import com.mobilefix.mobilefix.model.Device;
import com.mobilefix.mobilefix.model.RepairOrder;
import com.mobilefix.mobilefix.model.User;
import com.mobilefix.mobilefix.service.DeviceService;
import com.mobilefix.mobilefix.service.RepairOrderService;
import com.mobilefix.mobilefix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserDashboardController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Dashboard del USER - Ver sus órdenes y crear nuevas
     */
    @GetMapping("/dashboard")
    public String userDashboard(Model model, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Obtener órdenes del usuario
        List<RepairOrder> myOrders = repairOrderService.getOrdersByCustomer(currentUser);

        // Obtener dispositivos disponibles para crear orden
        List<Device> devices = deviceService.getAllDevices();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("myOrders", myOrders);
        model.addAttribute("devices", devices);

        return "user/dashboard";
    }

    /**
     * Ver detalle de una orden (solo si es propia)
     */
    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RepairOrder order = repairOrderService.getOrderById(id);

        // Validar que la orden sea del usuario
        if (!order.getCustomer().getId().equals(currentUser.getId())) {
            return "redirect:/user/dashboard?error=unauthorized";
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("order", order);

        return "user/order-detail";
    }
}