package com.mobilefix.mobilefix.config;

import com.mobilefix.mobilefix.model.Device;
import com.mobilefix.mobilefix.model.RepairOrder;
import com.mobilefix.mobilefix.model.User;
import com.mobilefix.mobilefix.repository.DeviceRepository;
import com.mobilefix.mobilefix.repository.RepairOrderRepository;
import com.mobilefix.mobilefix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RepairOrderRepository repairOrderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Solo insertar datos si la BD está vacía
        if (userRepository.count() == 0) {
            initializeUsers();
            initializeDevices();
            initializeRepairOrders();
            System.out.println("✅ Datos de prueba cargados exitosamente!");
        } else {
            System.out.println("⚠️ La base de datos ya contiene datos. No se cargaron datos de prueba.");
        }
    }

    /**
     * Crear usuarios de prueba
     */
    private void initializeUsers() {
        // ADMIN
        User admin = new User();
        admin.setUsername("admin");
        admin.setFullName("Administrator User");
        admin.setEmail("admin@mobilefix.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(User.Role.ADMIN);
        admin.setEnabled(true);
        userRepository.save(admin);

        // TECH 1
        User tech1 = new User();
        tech1.setUsername("tech");
        tech1.setFullName("John Technician");
        tech1.setEmail("tech@mobilefix.com");
        tech1.setPassword(passwordEncoder.encode("tech123"));
        tech1.setRole(User.Role.TECH);
        tech1.setEnabled(true);
        userRepository.save(tech1);

        // TECH 2
        User tech2 = new User();
        tech2.setUsername("maria");
        tech2.setFullName("Maria Rodriguez");
        tech2.setEmail("maria@mobilefix.com");
        tech2.setPassword(passwordEncoder.encode("maria123"));
        tech2.setRole(User.Role.TECH);
        tech2.setEnabled(true);
        userRepository.save(tech2);

        // USER 1
        User user1 = new User();
        user1.setUsername("user");
        user1.setFullName("Carlos Customer");
        user1.setEmail("user@example.com");
        user1.setPassword(passwordEncoder.encode("user123"));
        user1.setRole(User.Role.USER);
        user1.setEnabled(true);
        userRepository.save(user1);

        // USER 2
        User user2 = new User();
        user2.setUsername("ana");
        user2.setFullName("Ana Garcia");
        user2.setEmail("ana@example.com");
        user2.setPassword(passwordEncoder.encode("ana123"));
        user2.setRole(User.Role.USER);
        user2.setEnabled(true);
        userRepository.save(user2);

        // USER 3
        User user3 = new User();
        user3.setUsername("pedro");
        user3.setFullName("Pedro Martinez");
        user3.setEmail("pedro@example.com");
        user3.setPassword(passwordEncoder.encode("pedro123"));
        user3.setRole(User.Role.USER);
        user3.setEnabled(true);
        userRepository.save(user3);

        System.out.println("✅ Usuarios creados: 1 ADMIN, 2 TECH, 3 USER");
    }

    /**
     * Crear dispositivos de prueba
     */
    private void initializeDevices() {
        // iPhone 15
        Device device1 = new Device();
        device1.setBrand("Apple");
        device1.setModel("iPhone 15 Pro");
        device1.setSerialNumber("IPHONE15PRO001");
        deviceRepository.save(device1);

        // Samsung Galaxy
        Device device2 = new Device();
        device2.setBrand("Samsung");
        device2.setModel("Galaxy S24 Ultra");
        device2.setSerialNumber("GALAXYS24ULTRA001");
        deviceRepository.save(device2);

        // Xiaomi
        Device device3 = new Device();
        device3.setBrand("Xiaomi");
        device3.setModel("Redmi Note 13 Pro");
        device3.setSerialNumber("REDMINOTE13PRO001");
        deviceRepository.save(device3);

        // Google Pixel
        Device device4 = new Device();
        device4.setBrand("Google");
        device4.setModel("Pixel 8");
        device4.setSerialNumber("PIXEL8001");
        deviceRepository.save(device4);

        // Motorola
        Device device5 = new Device();
        device5.setBrand("Motorola");
        device5.setModel("Edge 40 Pro");
        device5.setSerialNumber("EDGE40PRO001");
        deviceRepository.save(device5);

        // OnePlus
        Device device6 = new Device();
        device6.setBrand("OnePlus");
        device6.setModel("OnePlus 12");
        device6.setSerialNumber(null); // Sin serial number
        deviceRepository.save(device6);

        System.out.println("✅ Dispositivos creados: 6 devices");
    }

    /**
     * Crear órdenes de reparación de prueba
     */
    private void initializeRepairOrders() {
        // Obtener usuarios y dispositivos
        User user1 = userRepository.findByUsername("user").orElseThrow();
        User user2 = userRepository.findByUsername("ana").orElseThrow();
        User user3 = userRepository.findByUsername("pedro").orElseThrow();
        User tech1 = userRepository.findByUsername("tech").orElseThrow();
        User tech2 = userRepository.findByUsername("maria").orElseThrow();

        Device device1 = deviceRepository.findById(1L).orElseThrow();
        Device device2 = deviceRepository.findById(2L).orElseThrow();
        Device device3 = deviceRepository.findById(3L).orElseThrow();
        Device device4 = deviceRepository.findById(4L).orElseThrow();
        Device device5 = deviceRepository.findById(5L).orElseThrow();

        // ORDEN 1 - PENDING (sin técnico asignado)
        RepairOrder order1 = new RepairOrder();
        order1.setCustomer(user1);
        order1.setDevice(device1);
        order1.setIssueDescription("La pantalla está rota después de una caída. Necesito reemplazo urgente.");
        order1.setStatus(RepairOrder.OrderStatus.PENDING);
        repairOrderRepository.save(order1);

        // ORDEN 2 - IN_PROGRESS (asignada a tech1)
        RepairOrder order2 = new RepairOrder();
        order2.setCustomer(user2);
        order2.setDevice(device2);
        order2.setIssueDescription("La batería se descarga muy rápido, dura solo 2 horas con uso normal.");
        order2.setStatus(RepairOrder.OrderStatus.IN_PROGRESS);
        order2.setAssignedTech(tech1);
        order2.setTechNotes("Batería hinchada detectada. Reemplazo necesario. Pieza pedida.");
        repairOrderRepository.save(order2);

        // ORDEN 3 - READY (lista para entregar, asignada a tech2)
        RepairOrder order3 = new RepairOrder();
        order3.setCustomer(user3);
        order3.setDevice(device3);
        order3.setIssueDescription("El puerto de carga no funciona correctamente, tengo que mover el cable.");
        order3.setStatus(RepairOrder.OrderStatus.READY);
        order3.setAssignedTech(tech2);
        order3.setTechNotes("Puerto de carga reemplazado. Pruebas completadas exitosamente.");
        repairOrderRepository.save(order3);

        // ORDEN 4 - DELIVERED (entregada)
        RepairOrder order4 = new RepairOrder();
        order4.setCustomer(user1);
        order4.setDevice(device4);
        order4.setIssueDescription("La cámara trasera no enfoca bien, las fotos salen borrosas.");
        order4.setStatus(RepairOrder.OrderStatus.DELIVERED);
        order4.setAssignedTech(tech1);
        order4.setTechNotes("Módulo de cámara limpiado y calibrado. Cliente satisfecho.");
        repairOrderRepository.save(order4);

        // ORDEN 5 - CANCELED (cancelada)
        RepairOrder order5 = new RepairOrder();
        order5.setCustomer(user2);
        order5.setDevice(device5);
        order5.setIssueDescription("El altavoz no suena, solo funciona con auriculares.");
        order5.setStatus(RepairOrder.OrderStatus.CANCELED);
        order5.setTechNotes("Cliente canceló la reparación por costo elevado.");
        repairOrderRepository.save(order5);

        // ORDEN 6 - PENDING (otra orden sin asignar)
        RepairOrder order6 = new RepairOrder();
        order6.setCustomer(user3);
        order6.setDevice(device1);
        order6.setIssueDescription("El teléfono se reinicia solo constantemente, es imposible de usar.");
        order6.setStatus(RepairOrder.OrderStatus.PENDING);
        repairOrderRepository.save(order6);

        // ORDEN 7 - IN_PROGRESS (asignada a tech2)
        RepairOrder order7 = new RepairOrder();
        order7.setCustomer(user1);
        order7.setDevice(device2);
        order7.setIssueDescription("Los botones de volumen no responden al presionarlos.");
        order7.setStatus(RepairOrder.OrderStatus.IN_PROGRESS);
        order7.setAssignedTech(tech2);
        order7.setTechNotes("Botones desgastados. Esperando piezas de reemplazo.");
        repairOrderRepository.save(order7);

        System.out.println("✅ Órdenes de reparación creadas: 7 orders con diferentes estados");
    }
}
