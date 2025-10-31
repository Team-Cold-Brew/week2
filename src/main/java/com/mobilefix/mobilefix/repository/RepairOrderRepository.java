package com.mobilefix.mobilefix.repository;

import com.mobilefix.mobilefix.model.RepairOrder;
import com.mobilefix.mobilefix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder, Long> {

    // Obtener órdenes por cliente (para el rol USER)
    List<RepairOrder> findByCustomer(User customer);

    // Obtener órdenes por técnico asignado (para el rol TECH)
    List<RepairOrder> findByAssignedTech(User assignedTech);

    // Filtrar por estado
    List<RepairOrder> findByStatus(RepairOrder.OrderStatus status);

    // Órdenes por cliente y estado
    List<RepairOrder> findByCustomerAndStatus(User customer, RepairOrder.OrderStatus status);
}