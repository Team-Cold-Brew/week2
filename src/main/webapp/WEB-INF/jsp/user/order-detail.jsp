<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MobileFix - Detalle de Orden</title>
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
            max-width: 900px;
            margin: 30px auto;
            padding: 0 20px;
        }
        .btn-back {
            background: #667eea;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            margin-bottom: 20px;
        }
        .btn-back:hover {
            background: #5568d3;
        }
        .order-card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #f0f0f0;
        }
        .order-id {
            font-size: 28px;
            color: #333;
            font-weight: 600;
        }
        .badge {
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 14px;
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
        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
            margin-bottom: 30px;
        }
        .info-item {
            padding: 15px;
            background: #f8f9fa;
            border-radius: 8px;
        }
        .info-label {
            font-size: 12px;
            color: #999;
            text-transform: uppercase;
            font-weight: 600;
            margin-bottom: 5px;
        }
        .info-value {
            font-size: 16px;
            color: #333;
            font-weight: 500;
        }
        .description-section {
            margin-bottom: 30px;
        }
        .section-title {
            font-size: 18px;
            color: #333;
            margin-bottom: 15px;
            font-weight: 600;
        }
        .description-box {
            padding: 20px;
            background: #f8f9fa;
            border-radius: 8px;
            color: #666;
            line-height: 1.6;
        }
        .tech-notes {
            padding: 20px;
            background: #e7f3ff;
            border-left: 4px solid #667eea;
            border-radius: 8px;
            color: #004085;
            line-height: 1.6;
        }
        .no-notes {
            color: #999;
            font-style: italic;
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
        <a href="<c:url value='/user/dashboard'/>" class="btn-back">← Volver al Dashboard</a>

        <div class="order-card">
            <!-- Header -->
            <div class="order-header">
                <div class="order-id">Orden #${order.id}</div>
                <div>
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
                            <span class="badge badge-canceled">Cancelada</span>
                        </c:when>
                    </c:choose>
                </div>
            </div>

            <!-- Info Grid -->
            <div class="info-grid">
                <div class="info-item">
                    <div class="info-label">Dispositivo</div>
                    <div class="info-value">
                        ${order.device.brand} ${order.device.model}
                    </div>
                </div>

                <div class="info-item">
                    <div class="info-label">Número de Serie</div>
                    <div class="info-value">
                        <c:choose>
                            <c:when test="${order.device.serialNumber != null}">
                                ${order.device.serialNumber}
                            </c:when>
                            <c:otherwise>
                                <span style="color: #999;">No especificado</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="info-item">
                    <div class="info-label">Técnico Asignado</div>
                    <div class="info-value">
                        <c:choose>
                            <c:when test="${order.assignedTech != null}">
                                ${order.assignedTech.fullName}
                            </c:when>
                            <c:otherwise>
                                <span style="color: #999;">Sin asignar</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="info-item">
                    <div class="info-label">Fecha de Creación</div>
                    <div class="info-value">
                        <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                    </div>
                </div>
            </div>

            <!-- Descripción del Problema -->
            <div class="description-section">
                <div class="section-title"> Descripción del Problema</div>
                <div class="description-box">
                    ${order.issueDescription}
                </div>
            </div>

            <!-- Notas