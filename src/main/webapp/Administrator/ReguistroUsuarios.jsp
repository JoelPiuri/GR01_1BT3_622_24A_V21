<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
  <title>Registro Usuario</title>
  <script type="text/javascript">
    function toggleMateria() {
      var rol = document.getElementById("rol").value;
      var materiaField = document.getElementById("materiaField");
      if (rol === "2") { // Asumiendo que el rol con id 2 es 'Tutor'
        materiaField.style.display = "block";
      } else {
        materiaField.style.display = "none";
      }
    }

    function validarFormulario() {
      var nombre = document.getElementById("nombre").value;
      var apellido = document.getElementById("apellido").value;
      var correo = document.getElementById("correo").value;
      var rol = document.getElementById("rol").value;

      // Validación de nombre y apellido (solo letras y espacios)
      var nombreApellidoRegex = /^[a-zA-Z\s]+$/;
      if (!nombreApellidoRegex.test(nombre)) {
        alert("El nombre solo debe contener letras y espacios.");
        return false;
      }
      if (!nombreApellidoRegex.test(apellido)) {
        alert("El apellido solo debe contener letras y espacios.");
        return false;
      }

      // Validación del correo institucional (debe terminar en @epn.edu.ec)
      var correoRegex = /^[a-zA-Z0-9._%+-]+@epn\.edu\.ec$/;
      if (!correoRegex.test(correo)) {
        alert("El correo debe terminar en @epn.edu.ec.");
        return false;
      }

      // Validación de materias si el rol es 'Tutor'
      if (rol === "2") {
        var checkboxes = document.querySelectorAll('#materiaField input[type="checkbox"]');
        var isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);
        if (!isChecked) {
          alert("Debe seleccionar al menos una materia si el rol es Tutor.");
          return false;
        }
      }

      return true;
    }
  </script>
</head>
<body>
<h2>Registro Usuario</h2>
<!-- Mostrar el mensaje de error si existe -->
<c:if test="${not empty errorMessage}">
  <p style="color: red;">${errorMessage}</p>
</c:if>
<form action="${pageContext.request.contextPath}/ReguistroSistemaServlet" method="post" onsubmit="return validarFormulario()">
  <label for="nombre">Nombre:</label>
  <input type="text" id="nombre" name="nombre" required><br>

  <label for="apellido">Apellido:</label>
  <input type="text" id="apellido" name="apellido" required><br>

  <label for="correo">Correo Institucional:</label>
  <input type="text" id="correo" name="correo" required><br>

  <label for="rol">Rol:</label>
  <select id="rol" name="rol" onchange="toggleMateria()" required>
    <option value="">Seleccionar Rol</option>
    <c:forEach var="rol" items="${rols}">
      <option value="${rol.id}">${rol.tipo}</option>
    </c:forEach>
  </select><br>

  <!-- Campos de nombre de usuario y contraseña -->
  <label for="nombreUsuario">Nombre de Usuario:</label>
  <input type="text" id="nombreUsuario" name="nombreUsuario" required><br>

  <label for="contrasena">Contraseña:</label>
  <input type="password" id="contrasena" name="contrasena" required><br>

  <!-- Sección de materias, inicialmente oculta -->
  <div id="materiaField" style="display:none;">
    <h3>Selecciona Materias</h3>
    <table border="1">
      <thead>
      <tr>
        <th>Seleccionar</th>
        <th>Código de Materia</th>
        <th>Nombre de Materia</th>
        <th>Descripción</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="materia" items="${materias}">
        <tr>
          <td>
            <input type="checkbox" name="materiasSeleccionadas" value="${materia.codigomateria}">
          </td>
          <td>${materia.codigomateria}</td>
          <td>${materia.nombre}</td>
          <td>${materia.descripcion}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table><br>
  </div>

  <input type="submit" value="Registrar">
</form>
</body>
</html>
