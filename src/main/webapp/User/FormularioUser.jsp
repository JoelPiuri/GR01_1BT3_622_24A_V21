<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layouts/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>Responde la Encuesta</title>
  <script type="text/javascript">
    // Función para validar que todas las preguntas tienen una respuesta
    function validarEncuesta() {
      // Obtener todas las preguntas (grupos de radio buttons)
      const preguntas = document.querySelectorAll('input[type="radio"]');
      const preguntasMap = new Map();

      // Agrupar las preguntas por id para verificar si al menos una opción fue seleccionada
      preguntas.forEach(pregunta => {
        const idPregunta = pregunta.name.split('_')[1];
        if (!preguntasMap.has(idPregunta)) {
          preguntasMap.set(idPregunta, false); // Inicializar como no respondida
        }
        if (pregunta.checked) {
          preguntasMap.set(idPregunta, true); // Marcar como respondida si está seleccionada
        }
      });

      // Verificar si todas las preguntas están respondidas
      for (let [id, respondida] of preguntasMap) {
        if (!respondida) {
          alert("Por favor, responde a todas las preguntas antes de enviar.");
          return false; // Prevenir el envío del formulario
        }
      }

      return true; // Permitir el envío del formulario
    }
  </script>
</head>
<body>
<h2>Responde la Encuesta</h2>
<form action="${pageContext.request.contextPath}/guardarRespuesta" method="post" onsubmit="return validarEncuesta();">
  <c:forEach var="pregunta" items="${preguntas}">
    <div>
      <p>${pregunta.pregunta}</p>
      <input type="hidden" name="idPregunta" value="${pregunta.id}">
      <label><input type="radio" name="calificacion_${pregunta.id}" value="1"> 1</label>
      <label><input type="radio" name="calificacion_${pregunta.id}" value="2"> 2</label>
      <label><input type="radio" name="calificacion_${pregunta.id}" value="3"> 3</label>
      <label><input type="radio" name="calificacion_${pregunta.id}" value="4"> 4</label>
      <label><input type="radio" name="calificacion_${pregunta.id}" value="5"> 5</label>
    </div>
    <hr>
  </c:forEach>

  <!-- Campos ocultos con los nuevos parámetros requeridos -->
  <input type="hidden" name="idSolicitud" value="${idSolicitud}">
  <input type="hidden" name="idAlumno" value="${idAlumno}">
  <input type="hidden" name="idTutor" value="${idTutor}">
  <input type="hidden" name="codigomateria" value="${codigomateria}">

  <button type="submit">Enviar Respuestas</button>
</form>
</body>
</html>
