package com.mobilefix.mobilefix.repository;

import com.mobilefix.mobilefix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuario por username (para login)
    Optional<User> findByUsername(String username);

    // Buscar usuario por email
    Optional<User> findByEmail(String email);

    // Verificar si ya existe un username registrado
    boolean existsByUsername(String username);

    // Verificar si ya existe un email registrado
    boolean existsByEmail(String email);

    // Buscar usuarios por rol
    List<User> findByRole(User.Role role);
}