<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MobileFix - Login</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .login-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 400px;
        }
        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .login-header h1 {
            color: #667eea;
            font-size: 28px;
            margin-bottom: 10px;
        }
        .login-header p {
            color: #666;
            font-size: 14px;
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
            transition: border-color 0.3s;
        }
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }
        .btn-login {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s;
        }
        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        .alert {
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
            font-size: 14px;
        }
        .alert-danger {
            background-color: #fee;
            color: #c33;
            border: 1px solid #fcc;
        }
        .alert-success {
            background-color: #efe;
            color: #3c3;
            border: 1px solid #cfc;
        }
        .test-users {
            margin-top: 30px;
            padding: 20px;
            background: #f8f9fa;
            border-radius: 5px;
        }
        .test-users h3 {
            font-size: 16px;
            margin-bottom: 10px;
            color: #333;
        }
        .test-users ul {
            list-style: none;
        }
        .test-users li {
            padding: 8px 0;
            color: #666;
            font-size: 13px;
            border-bottom: 1px solid #e0e0e0;
        }
        .test-users li:last-child {
            border-bottom: none;
        }
        .test-users strong {
            color: #667eea;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1>📱 MobileFix</h1>
            <p>Sistema de Gestión de Reparaciones</p>
        </div>

        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                Usuario o contraseña incorrectos
            </div>
        </c:if>

        <c:if test="${param.logout != null}">
            <div class="alert alert-success">
                 Sesión cerrada exitosamente
            </div>
        </c:if>

        <form method="post" action="<c:url value='/login'/>">
            <div class="form-group">
                <label for="username">Usuario:</label>
                <input type="text"
                       id="username"
                       name="username"
                       placeholder="Ingrese su usuario"
                       required
                       autofocus>
            </div>

            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Ingrese su contraseña"
                       required>
            </div>

            <button type="submit" class="btn-login">Iniciar Sesión</button>
        </form>

        <div class="test-users">
            <h3>👤 Usuarios de Prueba:</h3>
            <ul>
                <li><strong>ADMIN:</strong> admin / admin123</li>
                <li><strong>TECH:</strong> tech / tech123</li>
                <li><strong>USER:</strong> user / user123</li>
            </ul>
        </div>
    </div>
</body>
</html>