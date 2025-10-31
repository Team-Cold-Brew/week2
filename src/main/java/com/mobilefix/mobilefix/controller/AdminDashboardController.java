package com.mobilefix.mobilefix.controller;

import com.mobilefix.mobilefix.model.Device;
import com.mobilefix.mobilefix.model.RepairOrder;
import com.mobilefix.mobilefix.model.User;
import com.mobilefix.mobilefix.service.DeviceService;
import com.mobilefix.mobilefix.service.RepairOrderService;
import com.mobilefix.mobilefix.service.UserService;
import com.mobilefix.mobilefix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Dashboard del ADMIN - Ver todo
     */
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Obtener todas las órdenes
        List<RepairOrder> allOrders = repairOrderService.getAllOrders();

        // Obtener todos los técnicos
        List<User> technicians = userService.getTechnicians();

        // Obtener todos los usuarios
        List<User> allUsers = userService.getAllUsers();

        // Obtener todos los dispositivos
        List<Device> allDevices = deviceService.getAllDevices();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allOrders", allOrders);
        model.addAttribute("technicians", technicians);
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("allDevices", allDevices);

        return "admin/dashboard";
    }

    /**
     * Gestión de usuarios
     */
    @GetMapping("/users")
    public String manageUsers(Model model, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> allUsers = userService.getAllUsers();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", allUsers);

        return "admin/users";
    }

    /**
     * Gestión de dispositivos
     */
    @GetMapping("/devices")
    public String manageDevices(Model model, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Device> allDevices = deviceService.getAllDevices();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("devices", allDevices);

        return "admin/devices";
    }

    /**
     * Ver detalle de cualquier orden
     */
    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RepairOrder order = repairOrderService.getOrderById(id);
        List<User> technicians = userService.getTechnicians();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("order", order);
        model.addAttribute("technicians", technicians);

        return "admin/order-detail";
    }
}