<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sistema Asistencia Virtual FIS</title>
</head>
<body>
<h2>Bienvenido al Sistema de Asistencia Virtual FIS</h2>
<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>

<form action="seleccionRol" method="post">
    <label for="nombreUsuario">Usuario:</label>
    <input type="text" id="nombreUsuario" name="nombreUsuario" required><br><br>

    <label for="contrasena">Contraseña:</label>
    <input type="password" id="contrasena" name="contrasena" required><br><br>

    <button type="submit">Ingresar</button>
</form>
<!-- Botón para registrarse -->
<br>
<a href="registro.jsp">
    <button type="button">Registrarse</button>
</a>
</body>
</html>
