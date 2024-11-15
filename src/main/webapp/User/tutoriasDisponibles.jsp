<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
  <title>Tutorías Disponibles</title>
</head>
<body>
<h2>Tutorías Disponibles</h2>

<form id="filtrosForm" action="consultarTutorias" method="get">
  <!-- Campos para los filtros -->
  <select name="materiaId" id="materiaId" onchange="document.getElementById('filtrosForm').submit();">
    <option value="">Todas</option>
    <c:forEach var="materia" items="${materias}">
      <option value="${materia.codigomateria}" ${materia.codigomateria == param.materiaId ? 'selected' : ''}>${materia.nombre}</option>
    </c:forEach>
  </select>

  <select name="ordenFecha" id="ordenFecha" onchange="document.getElementById('filtrosForm').submit();">
    <option value="asc" ${param.ordenFecha == 'asc' ? 'selected' : ''}>Fecha ascendente</option>
    <option value="desc" ${param.ordenFecha == 'desc' ? 'selected' : ''}>Fecha descendente</option>
  </select>
</form>




<!-- Tabla para mostrar las tutorías -->
<table border="1">
  <tr>
    <th>Materia</th>
    <th>Fecha</th>
    <th>Hora Inicio</th>
    <th>Hora Fin</th>
    <th>Tutor</th> <!-- Nueva columna para mostrar el nombre del tutor -->
    <th>Acción</th>
  </tr>
  <c:forEach var="tutoria" items="${tutorias}">
    <tr>
      <td>${tutoria.materia.nombre}</td>
      <td><fmt:formatDate value="${tutoria.fecha}" pattern="yyyy-MM-dd" /></td>
      <td>${tutoria.horaInicio}</td>
      <td>${tutoria.horaFin}</td>
      <td>${tutoria.tutor.nombre}</td>
      <td>
        <form action="${pageContext.request.contextPath}/AceptarTutoriaServlet" method="post">
          <input type="hidden" name="tutoriaId" value="${tutoria.id}">
          <button type="submit">Solicitar unirse</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
