<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MobileFix - Gestión de Dispositivos</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f7fa;
        }
        .navbar {
            background: linear-gradient(135deg, #dc3545 0%, #c82333 100%);
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .navbar h1 {
            font-size: 24px;
        }
        .navbar-right {
            display: flex;
            gap: 20px;
            align-items: center;
        }
        .btn-logout {
            background: rgba(255,255,255,0.2);
            color: white;
            padding: 8px 20px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            transition: background 0.3s;
        }
        .btn-logout:hover {
            background: rgba(255,255,255,0.3);
        }
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }
        .btn-back {
            background: #dc3545;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            margin-bottom: 20px;
        }
        .btn-back:hover {
            background: #c82333;
        }
        .section-title {
            font-size: 22px;
            color: #333;
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .btn-new {
            background: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-new:hover {
            background: #218838;
        }
        .form-card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
            display: none;
        }
        .form-card.active {
            display: block;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        .form-group input {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
        }
        .form-help {
            font-size: 12px;
            color: #999;
            margin-top: 5px;
        }
        .btn-primary {
            background: #dc3545;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-right: 10px;
        }
        .btn-primary:hover {
            background: #c82333;
        }
        .btn-cancel {
            background: #6c757d;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }
        .btn-cancel:hover {
            background: #5a6268;
        }
        .devices-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        .device-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }
        .device-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }
        .device-header {
            display: flex;
            align-items: center;
            gap: 15px;
            margin-bottom: 15px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }
        .device-icon {
            font-size: 36px;
        }
        .device-info h3 {
            font-size: 18px;
            color: #333;
            margin-bottom: 5px;
        }
        .device-info p {
            font-size: 14px;
            color: #999;
        }
        .device-details {
            margin-bottom: 15px;
        }
        .detail-item {
            display: flex;
            justify-content: space-between;
            padding: 8px 0;
            border-bottom: 1px solid #f0f0f0;
        }
        .detail-label {
            font-size: 13px;
            color: #999;
        }
        .detail-value {
            font-size: 14px;
            color: #333;
            font-weight: 500;
        }
        .device-actions {
            display: flex;
            gap: 10px;
        }
        .btn-edit {
            flex: 1;
            background: #007bff;
            color: white;
            padding: 8px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 13px;
        }
        .btn-edit:hover {
            background: #0056b3;
        }
        .btn-delete {
            flex: 1;
            background: #dc3545;
            color: white;
            padding: 8px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 13px;
        }
        .btn-delete:hover {
            background: #c82333;
        }
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 10px;
            color: #999;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar">
        <h1>⚙️ MobileFix - Administrador</h1>
        <div class="navbar-right">
            <span>Hola, ${currentUser.fullName}</span>
            <form action="<c:url value='/logout'/>" method="post" style="display: inline;">
                <button type="submit" class="btn-logout">Cerrar Sesión</button>
            </form>
        </div>
    </nav>

    <div class="container">
        <a href="<c:url value='/admin/dashboard'/>" class="btn-back">← Volver al Dashboard</a>

        <div class="section-title">
            <span>📱 Gestión de Dispositivos</span>
            <button class="btn-new" onclick="toggleForm()">+ Nuevo Dispositivo</button>
        </div>

        <!-- Formulario Crear/Editar Dispositivo -->
        <div class="form-card" id="deviceForm">
            <h3 style="margin-bottom: 20px;" id="formTitle">Crear Nuevo Dispositivo</h3>
            <form id="createDeviceForm">
                <input type="hidden" id="deviceId" name="deviceId">

                <div class="form-group">
                    <label for="brand">Marca:</label>
                    <input type="text" id="brand" name="brand" required placeholder="Ej: Samsung, Apple, Xiaomi">
                </div>

                <div class="form-group">
                    <label for="model">Modelo:</label>
                    <input type="text" id="model" name="model" required placeholder="Ej: Galaxy S24, iPhone 15">
                </div>

                <div class="form-group">
                    <label for="serialNumber">Número de Serie (opcional):</label>
                    <input type="text" id="serialNumber" name="serialNumber" placeholder="Ej: SN123456789">
                    <div class="form-help">Este campo es opcional pero debe ser único si se proporciona</div>
                </div>

                <button type="submit" class="btn-primary" id="submitBtn">Crear Dispositivo</button>
                <button type="button" class="btn-cancel" onclick="toggleForm()">Cancelar</button>
            </form>
        </div>

        <!-- Grid de Dispositivos -->
        <c:choose>
            <c:when test="${empty devices}">
                <div class="empty-state">
                    <h3>📭 No hay dispositivos registrados</h3>
                    <p>Crea el primer dispositivo usando el botón de arriba</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="devices-grid">
                    <c:forEach items="${devices}" var="device">
                        <div class="device-card">
                            <div class="device-header">
                                <div class="device-icon">📱</div>
                                <div class="device-info">
                                    <h3>${device.brand}</h3>
                                    <p>${device.model}</p>
                                </div>
                            </div>

                            <div class="device-details">
                                <div class="detail-item">
                                    <span class="detail-label">ID:</span>
                                    <span class="detail-value">#${device.id}</span>
                                </div>
                                <div class="detail-item">
                                    <span class="detail-label">Número de Serie:</span>
                                    <span class="detail-value">
                                        <c:choose>
                                            <c:when test="${device.serialNumber != null && !device.serialNumber.isEmpty()}">
                                                ${device.serialNumber}
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: #999;">No especificado</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </div>

                            <div class="device-actions">
                                <button class="btn-edit" onclick="editDevice(${device.id}, '${device.brand}', '${device.model}', '${device.serialNumber}')">
                                    ✏️ Editar
                                </button>
                                <button class="btn-delete" onclick="deleteDevice(${device.id}, '${device.brand} ${device.model}')">
                                    🗑️ Eliminar
                                </button>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        let isEditing = false;

        function toggleForm() {
            const form = document.getElementById('deviceForm');
            form.classList.toggle('active');
            if (!form.classList.contains('active')) {
                resetForm();
            }
        }

        function resetForm() {
            document.getElementById('createDeviceForm').reset();
            document.getElementById('deviceId').value = '';
            document.getElementById('formTitle').textContent = 'Crear Nuevo Dispositivo';
            document.getElementById('submitBtn').textContent = 'Crear Dispositivo';
            isEditing = false;
        }

        function editDevice(id, brand, model, serialNumber) {
            isEditing = true;
            document.getElementById('deviceForm').classList.add('active');
            document.getElementById('formTitle').textContent = 'Editar Dispositivo';
            document.getElementById('submitBtn').textContent = 'Actualizar Dispositivo';

            document.getElementById('deviceId').value = id;
            document.getElementById('brand').value = brand;
            document.getElementById('model').value = model;
            document.getElementById('serialNumber').value = serialNumber === 'null' ? '' : serialNumber;
        }

        // Crear/Actualizar dispositivo
        document.getElementById('createDeviceForm').addEventListener('submit', async (e) => {
            e.preventDefault();

            const deviceId = document.getElementById('deviceId').value;
            const formData = {
                brand: document.getElementById('brand').value,
                model: document.getElementById('model').value,
                serialNumber: document.getElementById('serialNumber').value || null
            };

            try {
                let response;
                if (isEditing && deviceId) {
                    // Actualizar
                    response = await fetch(`/api/devices/${deviceId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(formData)
                    });
                } else {
                    // Crear
                    response = await fetch('/api/devices', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(formData)
                    });
                }

                if (response.ok) {
                    alert(isEditing ? '✅ Dispositivo actualizado exitosamente' : '✅ Dispositivo creado exitosamente');
                    window.location.reload();
                } else {
                    const error = await response.json();
                    alert('❌ Error: ' + error.error);
                }
            } catch (error) {
                alert('❌ Error: ' + error.message);
            }
        });

        // Eliminar dispositivo
        async function deleteDevice(deviceId, deviceName) {
            if (!confirm(`¿Estás seguro de eliminar "${deviceName}"?`)) {
                return;
            }

            try {
                const response = await fetch(`/api/devices/${deviceId}`, {
                    method: 'DELETE'
                });

                if (response.ok) {
                    alert('✅ Dispositivo eliminado exitosamente');
                    window.location.reload();
                } else {
                    const error = await response.json();
                    alert('❌ Error: ' + error.error);
                }
            } catch (error) {
                alert('❌ Error al eliminar dispositivo: ' + error.message);
            }
        }
    </script>
</body>
</html>