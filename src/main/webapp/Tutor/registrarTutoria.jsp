<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
  <title>Registrar Tutoría</title>
  <script type="text/javascript">
    window.onload = function() {
      // Bloquear la selección de fechas pasadas
      var today = new Date().toISOString().split('T')[0];
      var fechaInput = document.getElementById("fecha");
      fechaInput.setAttribute("min", today);
    }

    // Función para generar opciones de hora
    function generarHoras() {
      var horaInicio = document.getElementById("horaInicioSelect");
      var horaFin = document.getElementById("horaFinSelect");

      for (var i = 9; i <= 18; i++) {
        var hora = i.toString().padStart(2, '0');
        var option = document.createElement("option");
        option.value = hora;
        option.text = hora;
        horaInicio.appendChild(option);

        var optionFin = document.createElement("option");
        optionFin.value = hora;
        optionFin.text = hora;
        horaFin.appendChild(optionFin);
      }
    }
  </script>
</head>
<body onload="generarHoras();">
<h2>Registrar una Nueva Tutoría</h2>
<form action="${pageContext.request.contextPath}/RegistrarTutoriaServlet" method="post">
  <!-- Menú desplegable para seleccionar la materia -->
  <label for="materia">Materia:</label>
  <select id="materia" name="materia" required>
    <option value="">Selecciona una materia</option>
    <c:forEach var="materia" items="${materias}">
      <option value="${materia.codigomateria}">${materia.nombre}</option>
    </c:forEach>
  </select><br>

  <!-- Fecha, solo fechas futuras -->
  <label for="fecha">Fecha:</label>
  <input type="date" id="fecha" name="fecha" required><br>

  <!-- Hora de inicio: entre 9 AM y 6 PM -->
  <label for="horaInicioSelect">Hora de inicio:</label>
  <select id="horaInicioSelect" name="horaInicioSelect" required></select><br> :

  <!-- Hora de fin: entre 9 AM y 6 PM -->
  <label for="horaFinSelect">Hora de fin:</label>
  <select id="horaFinSelect" name="horaFinSelect" required></select><br> :

  <!-- Campo para cupos -->
  <label for="cupos">Cupos:</label>
  <input type="number" id="cupos" name="cupos" min="1" required><br>

  <button type="submit">Registrar</button>
</form>

</body>
</html>
