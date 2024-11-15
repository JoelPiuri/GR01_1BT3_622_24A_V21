<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/layouts/header.jsp" %>

<html>
<head>
  <title>Tutorías Creadas y Solicitudes</title>
</head>
<body>
<h2>Tutorías Creadas</h2>

<!-- Tabla para mostrar las tutorías creadas con cupos y cupos aceptados -->
<table border="1">
  <tr>
    <th>ID</th>
    <th>Materia</th>
    <th>Fecha</th>
    <th>Cupos Totales</th>
    <th>Cupos Aceptados</th>
  </tr>
  <c:forEach var="tutoria" items="${tutorias}">
    <tr>
      <td>${tutoria.id}</td>
      <td>${tutoria.materia.nombre}</td>
      <td><fmt:formatDate value="${tutoria.fecha}" pattern="dd-MM-yyyy" /></td>
      <td>${tutoria.cupos}</td>
      <td>${solicitudesAceptadasMap[tutoria.id]}</td> <!-- Muestra el número de cupos aceptados -->
    </tr>
  </c:forEach>
</table>

<h2>Solicitudes Recibidas</h2>

<!-- Formulario para seleccionar el estado de las solicitudes -->
<form action="${pageContext.request.contextPath}/VerTutoriasServlet" method="get">
  <label for="estado">Filtrar por estado:</label>
  <select name="estado" id="estado" onchange="this.form.submit()">
    <option value="">Todas</option>
    <option value="Pendiente" ${estadoSeleccionado == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
    <option value="Aceptada" ${estadoSeleccionado == 'Aceptada' ? 'selected' : ''}>Aceptada</option>
    <option value="Rechazada" ${estadoSeleccionado == 'Rechazada' ? 'selected' : ''}>Rechazada</option>
  </select>
</form>

<!-- Verifica si la lista de solicitudes está vacía y muestra un mensaje -->
<c:if test="${empty solicitudes}">
  <p style="color: red;">No se encontraron solicitudes en el estado seleccionado.</p>
</c:if>

<!-- Tabla para mostrar las solicitudes de estudiantes -->
<table border="1">
  <tr>
    <th>ID Solicitud</th>
    <th>Materia</th>
    <th>Alumno</th>
    <th>Estado</th>
    <th>Acciones</th>
    <th>Ver Respuestas</th>
  </tr>
  <c:forEach var="solicitud" items="${solicitudes}">
    <tr>
      <td>${solicitud.id}</td>
      <td>${solicitud.tutoria.materia.nombre}</td>
      <td>${solicitud.alumno.nombre}</td>
      <td>${solicitud.estado}</td>
      <td>
        <!-- Deshabilitar solo el botón "Aceptar" si la tutoría está completa -->
        <form action="${pageContext.request.contextPath}/CambiarEstadoSolicitudServlet" method="post" style="display:inline;">
          <input type="hidden" name="solicitudId" value="${solicitud.id}">
          <input type="hidden" name="nuevoEstado" value="Aceptada">
          <button type="submit" ${cuposCompletosMap[solicitud.tutoria.id] ? 'disabled' : ''}>Aceptar</button>
        </form>

        <!-- Botón Rechazar siempre habilitado -->
        <form action="${pageContext.request.contextPath}/CambiarEstadoSolicitudServlet" method="post" style="display:inline;">
          <input type="hidden" name="solicitudId" value="${solicitud.id}">
          <input type="hidden" name="nuevoEstado" value="Rechazada">
          <button type="submit">Rechazar</button>
        </form>
      </td>
      <td>
        <form action="${pageContext.request.contextPath}/VerRespuestasServlet" method="get" style="display:inline;">
          <input type="hidden" name="idSolicitud" value="${solicitud.id}">
          <button type="submit">Ver Respuestas</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
