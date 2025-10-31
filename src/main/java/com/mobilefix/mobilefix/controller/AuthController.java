package com.mobilefix.mobilefix.controller;

import com.mobilefix.mobilefix.model.User;
import com.mobilefix.mobilefix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Página de login
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Dashboard principal - redirige según el rol
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("currentUser", currentUser);

        // Redirigir según el rol
        switch (currentUser.getRole()) {
            case ADMIN:
                return "redirect:/admin/dashboard";
            case TECH:
                return "redirect:/tech/dashboard";
            case USER:
                return "redirect:/user/dashboard";
            default:
                return "redirect:/login";
        }
    }
}