<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
  <title>Solicitudes de Tutoría</title>
</head>
<body>
<h2>Solicitudes de Tutoría</h2>

<!-- Tabla para mostrar las solicitudes -->
<table border="1">
  <tr>
    <th>ID Solicitud</th>
    <th>Materia</th>
    <th>Fecha</th>
    <th>Hora Inicio</th>
    <th>Hora Fin</th>
    <th>Tutor</th> <!-- Mostrar el nombre del tutor -->
    <th>Estado</th> <!-- Mostrar el estado (Pendiente, Aceptada, etc.) -->
    <th>Acción</th> <!-- Nueva columna para los botones -->
  </tr>
  <c:forEach var="solicitud" items="${solicitudes}">
    <tr>
      <td>${solicitud.id}</td>
      <td>${solicitud.tutoria.materia.nombre}</td>
      <td>${solicitud.tutoria.fecha}</td>
      <td>${solicitud.tutoria.horaInicio}</td>
      <td>${solicitud.tutoria.horaFin}</td>
      <td>${solicitud.tutor.nombre}</td>
      <td>${solicitud.estado}</td> <!-- Mostrar el estado de la solicitud -->
      <td>
        <!-- Mostrar botones según el estado de la solicitud -->
        <c:choose>
          <c:when test="${solicitud.estado == 'Pendiente' || solicitud.estado == 'Rechazada'}">
            <form action="${pageContext.request.contextPath}/EliminarSolicitudServlet" method="post" style="display:inline;">
              <input type="hidden" name="idSolicitud" value="${solicitud.id}">
              <button type="submit">Eliminar Solicitud</button>
            </form>
            <button type="button" disabled>Responder Encuesta</button>
          </c:when>
          <c:otherwise>
            <form action="${pageContext.request.contextPath}/formularioUserServlet" method="get" style="display:inline;">
              <input type="hidden" name="idSolicitud" value="${solicitud.id}">
              <button type="submit">Responder Encuesta</button>
            </form>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
