<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
    <title>Respuestas de la Encuesta</title>
</head>
<body>
<h2>Respuestas de la Encuesta</h2>

<!-- Información de la solicitud y tutoría -->
<p><strong>Solicitud ID:</strong> ${solicitud.id}</p>
<p><strong>Materia:</strong> ${solicitud.tutoria.materia.nombre}</p>
<p><strong>Alumno:</strong> ${solicitud.alumno.nombre} ${solicitud.alumno.apellido}</p>

<!-- Tabla para mostrar las respuestas de la encuesta -->
<table border="1">
    <tr>
        <th>Pregunta</th>
        <th>Calificación (Escala de Likert)</th>
    </tr>
    <c:forEach var="respuesta" items="${respuestas}">
        <tr>
            <td>${respuesta.pregunta.pregunta}</td>
            <td>${respuesta.calificacion}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
