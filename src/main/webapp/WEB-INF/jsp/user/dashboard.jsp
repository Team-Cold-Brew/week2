<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MobileFix - Mi Dashboard</title>
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
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
            text-decoration: none;
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
        .welcome-card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        .welcome-card h2 {
            color: #333;
            margin-bottom: 10px;
        }
        .welcome-card p {
            color: #666;
        }
        .section-title {
            font-size: 22px;
            color: #333;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .form-card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
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
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
            font-family: inherit;
        }
        .form-group textarea {
            resize: vertical;
            min-height: 100px;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: transform 0.2s;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        .orders-table {
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        thead {
            background: #f8f9fa;
        }
        th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
            color: #333;
            border-bottom: 2px solid #e0e0e0;
        }
        td {
            padding: 15px;
            border-bottom: 1px solid #f0f0f0;
            color: #666;
        }
        tr:hover {
            background: #f8f9fa;
        }
        .badge {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
        }
        .badge-pending {
            background: #fff3cd;
            color: #856404;
        }
        .badge-progress {
            background: #cce5ff;
            color: #004085;
        }
        .badge-ready {
            background: #d4edda;
            color: #155724;
        }
        .badge-delivered {
            background: #d1ecf1;
            color: #0c5460;
        }
        .badge-canceled {
            background: #f8d7da;
            color: #721c24;
        }
        .btn-view {
            background: #667eea;
            color: white;
            padding: 6px 15px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 13px;
            transition: background 0.3s;
        }
        .btn-view:hover {
            background: #5568d3;
        }
        .btn-delete {
            background: #dc3545;
            color: white;
            padding: 6px 15px;
            border: none;
            border-radius: 5px;
            font-size: 13px;
            cursor: pointer;
            transition: background 0.3s;
        }
        .btn-delete:hover {
            background: #c82333;
        }
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #999;
        }
        .empty-state h3 {
            font-size: 20px;
            margin-bottom: 10px;
        }
        .alert {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-danger {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar">
        <h1>📱 MobileFix - Cliente</h1>
        <div class="navbar-right">
            <span>Hola, ${currentUser.fullName}</span>
            <form action="<c:url value='/logout'/>" method="post" style="display: inline;">
                <button type="submit" class="btn-logout">Cerrar Sesión</button>
            </form>
        </div>
    </nav>

    <div class="container">
        <!-- Welcome Card -->
        <div class="welcome-card">
            <h2>Bienvenido, ${currentUser.fullName} 👋</h2>
            <p>Aquí puedes crear nuevas solicitudes de reparación y ver el estado de tus órdenes.</p>
        </div>

        <!-- Crear Nueva Orden -->
        <div class="form-card">
            <h2 class="section-title"> Nueva Solicitud de Reparación</h2>
            <form id="createOrderForm">
                <div class="form-group">
                    <label for="deviceId">Dispositivo:</label>
                    <select id="deviceId" name="deviceId" required>
                        <option value="">Selecciona un dispositivo</option>
                        <c:forEach items="${devices}" var="device">
                            <option value="${device.id}">
                                ${device.brand} ${device.model}
                                <c:if test="${device.serialNumber != null}">
                                    (SN: ${device.serialNumber})
                                </c:if>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="issueDescription">Descripción del problema (mínimo 10 caracteres):</label>
                    <textarea id="issueDescription"
                              name="issueDescription"
                              placeholder="Describe detalladamente el problema con tu dispositivo..."
                              required
                              minlength="10"></textarea>
                </div>

                <button type="submit" class="btn-primary">Crear Solicitud</button>
            </form>
        </div>

        <!-- Mis Órdenes -->
        <h2 class="section-title">📋 Mis Órdenes de Reparación</h2>
        <div class="orders-table">
            <c:choose>
                <c:when test="${empty myOrders}">
                    <div class="empty-state">
                        <h3>📭 No tienes órdenes aún</h3>
                        <p>Crea tu primera solicitud de reparación arriba</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Dispositivo</th>
                                <th>Problema</th>
                                <th>Estado</th>
                                <th>Técnico</th>
                                <th>Fecha</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${myOrders}" var="order">
                                <tr>
                                    <td>#${order.id}</td>
                                    <td>${order.device.brand} ${order.device.model}</td>
                                    <td>${order.issueDescription.substring(0, Math.min(50, order.issueDescription.length()))}...</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.status == 'PENDING'}">
                                                <span class="badge badge-pending"> Pendiente</span>
                                            </c:when>
                                            <c:when test="${order.status == 'IN_PROGRESS'}">
                                                <span class="badge badge-progress"> En Progreso</span>
                                            </c:when>
                                            <c:when test="${order.status == 'READY'}">
                                                <span class="badge badge-ready"> Lista</span>
                                            </c:when>
                                            <c:when test="${order.status == 'DELIVERED'}">
                                                <span class="badge badge-delivered">📦 Entregada</span>
                                            </c:when>
                                            <c:when test="${order.status == 'CANCELED'}">
                                                <span class="badge badge-canceled"> Cancelada</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.assignedTech != null}">
                                                ${order.assignedTech.fullName}
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: #999;">Sin asignar</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                    </td>
                                    <td>
                                        <a href="<c:url value='/user/orders/${order.id}'/>" class="btn-view">Ver</a>
                                        <c:if test="${order.status == 'PENDING'}">
                                            <button onclick="deleteOrder(${order.id})" class="btn-delete">Cancelar</button>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script>
        // Crear nueva orden
        document.getElementById('createOrderForm').addEventListener('submit', async (e) => {
            e.preventDefault();

            const deviceId = document.getElementById('deviceId').value;
            const issueDescription = document.getElementById('issueDescription').value;

            if (issueDescription.length < 10) {
                alert('La descripción debe tener al menos 10 caracteres');
                return;
            }

            try {
                const response = await fetch('/api/orders', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        deviceId: parseInt(deviceId),
                        issueDescription: issueDescription
                    })
                });

                if (response.ok) {
                    alert('Orden creada exitosamente');
                    window.location.reload();
                } else {
                    const error = await response.json();
                    alert(' Error: ' + error.error);
                }
            } catch (error) {
                alert(' Error al crear la orden: ' + error.message);
            }
        });

        // Eliminar orden
        async function deleteOrder(orderId) {
            if (!confirm('¿Estás seguro de cancelar esta orden?')) {
                return;
            }

            try {
                const response = await fetch(`/api/orders/${orderId}`, {
                    method: 'DELETE'
                });

                if (response.ok) {
                    alert(' Orden cancelada exitosamente');
                    window.location.reload();
                } else {
                    const error = await response.json();
                    alert('Error: ' + error.error);
                }
            } catch (error) {
                alert(' Error al cancelar la orden: ' + error.message);
            }
        }
    </script>
</body>
</html>