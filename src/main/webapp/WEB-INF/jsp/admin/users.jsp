<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MobileFix - Gestión de Usuarios</title>
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
            transition: background 0.3s;
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
        .form-card h3 {
            color: #333;
            margin-bottom: 20px;
            font-size: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }
        .form-group input,
        .form-group select {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
            font-family: inherit;
            transition: border-color 0.3s;
        }
        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #dc3545;
        }
        .form-actions {
            display: flex;
            gap: 10px;
        }
        .btn-primary {
            background: #dc3545;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s;
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
            transition: background 0.3s;
        }
        .btn-cancel:hover {
            background: #5a6268;
        }
        .users-table {
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
            font-size: 14px;
        }
        td {
            padding: 15px;
            border-bottom: 1px solid #f0f0f0;
            color: #666;
            font-size: 14px;
        }
        tbody tr:hover {
            background: #f8f9fa;
        }
        .badge {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
        }
        .badge-admin {
            background: #f8d7da;
            color: #721c24;
        }
        .badge-tech {
            background: #d4edda;
            color: #155724;
        }
        .badge-user {
            background: #cce5ff;
            color: #004085;
        }
        .badge-active {
            background: #d4edda;
            color: #155724;
        }
        .badge-inactive {
            background: #f8d7da;
            color: #721c24;
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
        .btn-delete:disabled {
            background: #ccc;
            cursor: not-allowed;
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
        <a href="<c:url value='/admin/dashboard'/>" class="btn-back">← Volver al Dashboard</a>

        <div class="section-title">
            <span>👥 Gestión de Usuarios</span>
            <button class="btn-new" onclick="toggleForm()">+ Nuevo Usuario</button>
        </div>

        <!-- Formulario Crear Usuario -->
        <div class="form-card" id="userForm">
            <h3>Crear Nuevo Usuario</h3>
            <form id="createUserForm">
                <div class="form-group">
                    <label for="username">Usuario (username):</label>
                    <input type="text"
                           id="username"
                           name="username"
                           required
                           minlength="3"
                           maxlength="50"
                           placeholder="Nombre de usuario único">
                </div>

                <div class="form-group">
                    <label for="fullName">Nombre Completo:</label>
                    <input type="text"
                           id="fullName"
                           name="fullName"
                           required
                           placeholder="Nombre completo del usuario">
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email"
                           id="email"
                           name="email"
                           required
                           placeholder="correo@ejemplo.com">
                </div>

                <div class="form-group">
                    <label for="password">Contraseña:</label>
                    <input type="password"
                           id="password"
                           name="password"
                           required
                           minlength="6"
                           placeholder="Mínimo 6 caracteres">
                </div>

                <div class="form-group">
                    <label for="role">Rol:</label>
                    <select id="role" name="role" required>
                        <option value="">Seleccione un rol</option>
                        <option value="USER">Usuario (Cliente)</option>
                        <option value="TECH">Técnico</option>
                        <option value="ADMIN">Administrador</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="enabled">Estado:</label>
                    <select id="enabled" name="enabled">
                        <option value="true">Activo</option>
                        <option value="false">Inactivo</option>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-primary">Crear Usuario</button>
                    <button type="button" class="btn-cancel" onclick="toggleForm()">Cancelar</button>
                </div>
            </form>
        </div>

        <!-- Tabla de Usuarios -->
        <div class="users-table">
            <c:choose>
                <c:when test="${empty users}">
                    <div class="empty-state">
                        <h3> No hay usuarios registrados</h3>
                        <p>Crea el primer usuario usando el botón de arriba</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Usuario</th>
                                <th>Nombre Completo</th>
                                <th>Email</th>
                                <th>Rol</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td>#${user.id}</td>
                                    <td><strong>${user.username}</strong></td>
                                    <td>${user.fullName}</td>
                                    <td>${user.email}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.role == 'ADMIN'}">
                                                <span class="badge badge-admin">⚙️ Admin</span>
                                            </c:when>
                                            <c:when test="${user.role == 'TECH'}">
                                                <span class="badge badge-tech">🔧 Técnico</span>
                                            </c:when>
                                            <c:when test="${user.role == 'USER'}">
                                                <span class="badge badge-user">👤 Cliente</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.enabled}">
                                                <span class="badge badge-active"> Activo</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-inactive"> Inactivo</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.id == currentUser.id}">
                                                <button class="btn-delete" disabled title="No puedes eliminar tu propia cuenta">
                                                    Eliminar
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button onclick="deleteUser(${user.id}, '${user.username}')" class="btn-delete">
                                                    Eliminar
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
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
        function toggleForm() {
            const form = document.getElementById('userForm');
            form.classList.toggle('active');
            if (!form.classList.contains('active')) {
                document.getElementById('createUserForm').reset();
            }
        }

        // Crear usuario
        document.getElementById('createUserForm').addEventListener('submit', async (e) => {
            e.preventDefault();

            const formData = {
                username: document.getElementById('username').value.trim(),
                fullName: document.getElementById('fullName').value.trim(),
                email: document.getElementById('email').value.trim(),
                password: document.getElementById('password').value,
                role: document.getElementById('role').value,
                enabled: document.getElementById('enabled').value === 'true'
            };

            // Validaciones básicas
            if (!formData.role) {
                alert(' Por favor selecciona un rol');
                return;
            }

            if (formData.password.length < 6) {
                alert(' La contraseña debe tener al menos 6 caracteres');
                return;
            }

            try {
                const response = await fetch('/api/users', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(formData)
                });

                if (response.ok) {
                    alert(' Usuario creado exitosamente');
                    window.location.reload();
                } else {
                    const error = await response.json();
                    alert(' Error: ' + error.error);
                }
            } catch (error) {
                alert(' Error al crear usuario: ' + error.message);
            }
        });

        // Eliminar usuario
        async function deleteUser(userId, username) {
            if (!confirm(`¿Estás seguro de eliminar al usuario "${username}"?\n\nEsta acción no se puede deshacer.`)) {
                return;
            }

            try {
                const response = await fetch(`/api/users/${userId}`, {
                    method: 'DELETE'
                });

                if (response.ok) {
                    alert(' Usuario eliminado exitosamente');
                    window.location.reload();
                } else {
                    const error = await response.json();
                    alert(' Error: ' + error.error);
                }
            } catch (error) {
                alert(' Error al eliminar usuario: ' + error.message);
            }
        }
    </script>
</body>
</html>