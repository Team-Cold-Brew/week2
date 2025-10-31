<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MobileFix - Admin Dashboard</title>
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
            max-width: 1400px;
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
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        .stat-number {
            font-size: 36px;
            font-weight: bold;
            color: #dc3545;
            margin-bottom: 10px;
        }
        .stat-label {
            color: #666;
            font-size: 14px;
            text-transform: uppercase;
        }
        .quick-actions {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .action-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-decoration: none;
            color: #333;
            transition: transform 0.2s, box-shadow 0.2s;
            display: flex;
            align-items: center;
            gap: 15px;
        }
        .action-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }
        .action-icon {
            font-size: 32px;
        }
        .action-text h3 {
            font-size: 18px;
            margin-bottom: 5px;
        }
        .action-text p {
            font-size: 13px;
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
            background: #dc3545;
            color: white;
            padding: 6px 15px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 13px;
            transition: background 0.3s;
        }
        .btn-view:hover {
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
        <!-- Welcome Card -->
        <div class="welcome-card">
            <h2>Panel de Administración 👨‍💼</h2>
            <p>Gestiona todas las órdenes, usuarios, dispositivos y técnicos del sistema.</p>
        </div>

        <!-- Estadísticas -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-number">${allOrders.size()}</div>
                <div class="stat-label">Total Órdenes</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">
                    <c:set var="pendingCount" value="0"/>
                    <c:forEach items="${allOrders}" var="order">
                        <c:if test="${order.status == 'PENDING'}">
                            <c:set var="pendingCount" value="${pendingCount + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${pendingCount}
                </div>
                <div class="stat-label">Pendientes</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${technicians.size()}</div>
                <div class="stat-label">Técnicos</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${allUsers.size()}</div>
                <div class="stat-label">Usuarios</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${allDevices.size()}</div>
                <div class="stat-label">Dispositivos</div>
            </div>
        </div>

        <!-- Acciones Rápidas -->
        <h2 class="section-title">🚀 Acciones Rápidas</h2>
        <div class="quick-actions">
            <a href="<c:url value='/admin/users'/>" class="action-card">
                <div class="action-icon">👥</div>
                <div class="action-text">
                    <h3>Gestionar Usuarios</h3>
                    <p>Crear, editar y eliminar usuarios</p>
                </div>
            </a>
            <a href="<c:url value='/admin/devices'/>" class="action-card">
                <div class="action-icon">📱</div>
                <div class="action-text">
                    <h3>Gestionar Dispositivos</h3>
                    <p>Catálogo de dispositivos</p>
                </div>
            </a>
        </div>

        <!-- Todas las Órdenes -->
        <h2 class="section-title">📋 Todas las Órdenes de Reparación</h2>
        <div class="orders-table">
            <c:choose>
                <c:when test="${empty allOrders}">
                    <div class="empty-state">
                        <h3>📭 No hay órdenes en el sistema</h3>
                        <p>Las órdenes aparecerán aquí cuando los usuarios las creen</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Cliente</th>
                                <th>Dispositivo</th>
                                <th>Problema</th>
                                <th>Estado</th>
                                <th>Técnico</th>
                                <th>Fecha</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${allOrders}" var="order">
                                <tr>
                                    <td>#${order.id}</td>
                                    <td>${order.customer.fullName}</td>
                                    <td>${order.device.brand} ${order.device.model}</td>
                                    <td>${order.issueDescription.substring(0, Math.min(40, order.issueDescription.length()))}...</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.status == 'PENDING'}">
                                                <span class="badge badge-pending">⏳ Pendiente</span>
                                            </c:when>
                                            <c:when test="${order.status == 'IN_PROGRESS'}">
                                                <span class="badge badge-progress">🔧 En Progreso</span>
                                            </c:when>
                                            <c:when test="${order.status == 'READY'}">
                                                <span class="badge badge-ready">✅ Lista</span>
                                            </c:when>
                                            <c:when test="${order.status == 'DELIVERED'}">
                                                <span class="badge badge-delivered">📦 Entregada</span>
                                            </c:when>
                                            <c:when test="${order.status == 'CANCELED'}">
                                                <span class="badge badge-canceled">❌ Cancelada</span>
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
                                        <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>
                                        <a href="<c:url value='/admin/orders/${order.id}'/>" class="btn-view">
                                            Gestionar
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>