<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
  <title>Usuario - Sistema Asistencia Virtual FIS</title>
</head>
<body>
<h2>Bienvenido, Usuario</h2>
<p>Desde aquí puedes consultar las tutorías disponibles o solicitar una tutoría.</p>
<a href="consultarTutorias">Consultar Tutorías Disponibles</a><br>
<a href="${pageContext.request.contextPath}/VerSolicitudesServlet">Ver Solicitudes de Tutoría</a>


</body>
</html>

