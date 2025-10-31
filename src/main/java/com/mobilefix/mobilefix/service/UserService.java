package com.mobilefix.mobilefix.service;

import com.mobilefix.mobilefix.model.User;
import com.mobilefix.mobilefix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtener todos los usuarios (ADMIN)
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Obtener usuario por ID
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Obtener usuario por username
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Obtener usuario por email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Obtener técnicos (usuarios con rol TECH)
     */
    public List<User> getTechnicians() {
        return userRepository.findByRole(User.Role.TECH);
    }

    /**
     * Crear nuevo usuario (ADMIN)
     */
    public User createUser(User user) {
        // Validar que el username no exista
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Validar que el email no exista
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Encriptar password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Asignar rol por defecto si no viene
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }

        // Habilitar por defecto
        if (user.getEnabled() == null) {
            user.setEnabled(true);
        }

        return userRepository.save(user);
    }

    /**
     * Actualizar usuario (ADMIN)
     */
    public User updateUser(Long id, User newData) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Actualizar campos permitidos
        existing.setFullName(newData.getFullName());
        existing.setEmail(newData.getEmail());
        existing.setRole(newData.getRole());
        existing.setEnabled(newData.getEnabled());

        // Solo actualizar password si viene una nueva
        if (newData.getPassword() != null && !newData.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(newData.getPassword()));
        }

        return userRepository.save(existing);
    }

    /**
     * Eliminar usuario (ADMIN)
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }
}