<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
  <title>Solicitudes de Tutores Pendientes a Revisión</title>
  <style>
    .preview-button {
      background-color: #4CAF50;
      color: white;
      padding: 5px 10px;
      border: none;
      cursor: pointer;
    }
    .pdf-preview {
      display: none;
      width: 80%;
      height: 600px;
      margin-top: 10px;
      border: 1px solid #ccc;
    }
    .materia-section {
      display: none;
      margin-top: 10px;
    }
  </style>
  <script type="text/javascript">
    function togglePreview(tutorId) {
      var previewFrame = document.getElementById("preview-" + tutorId);
      var materiaSection = document.getElementById("materiaSection-" + tutorId);
      if (previewFrame.style.display === "none") {
        previewFrame.style.display = "block";
        materiaSection.style.display = "block";
      } else {
        previewFrame.style.display = "none";
        materiaSection.style.display = "none";
      }
    }
    function validateForm(event) {
      var form = event.target; // Obtiene el formulario que disparó el evento.
      var checkboxes = form.querySelectorAll('input[type="checkbox"]');
      var isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);
      if (!isChecked) {
        alert("Debe seleccionar al menos una materia para aprobar al tutor.");
        event.preventDefault(); // Prevenir el envío del formulario.
        return false;
      }
      return true; // Permitir el envío del formulario.
    }
  </script>
</head>
<body>
<h2>Solicitudes de Tutores Pendientes a Revisión</h2>
<table border="1">
  <tr>
    <th>ID Tutor</th>
    <th>ID Usuario</th>
    <th>Nombre</th>
    <th>Apellido</th>
    <th>Email</th>
    <th>Nombre de Usuario</th>
    <th>Contraseña</th>
    <th>Estado</th>
    <th>Certificación</th>
  </tr>
  <c:forEach var="tutor" items="${tutoresPendientes}">
    <tr>
      <td>${tutor.id}</td>
      <td>${tutor.usuario.id}</td>
      <td>${tutor.nombre}</td>
      <td>${tutor.apellido}</td>
      <td>${tutor.email}</td>
      <td>${tutor.usuario.nombreUsuario}</td>
      <td>${tutor.usuario.contrasena}</td>
      <td>${tutor.estadoCuenta}</td>
      <td>
        <c:if test="${not empty tutor.rutaPdf}">
          <button class="preview-button" onclick="togglePreview(${tutor.id})">Ver PDF</button>
        </c:if>
      </td>
    </tr>
    <tr>
      <td colspan="9">
        <!-- Previsualización del PDF y selección de materias -->
        <c:if test="${not empty tutor.rutaPdf}">
          <iframe id="preview-${tutor.id}" class="pdf-preview" src="${pageContext.request.contextPath}/verPDF?ruta=${tutor.rutaPdf}" frameborder="0"></iframe>

          <!-- Formulario para cambiar el estado a "Activo" y asignar materias -->
          <div id="materiaSection-${tutor.id}" class="materia-section">
            <h3>Asignación de Materias para ${tutor.nombre} ${tutor.apellido}</h3>
            <form
                    action="${pageContext.request.contextPath}/ActualizarEstadoYMateriasServlet"
                    method="post"
                    onsubmit="validateForm(event);">
              <input type="hidden" name="tutorId" value="${tutor.id}">

              <!-- Selección de materias -->
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
              </table>

              <!-- Botón para cambiar estado a "Activo" y asignar materias -->
              <input type="submit" value="Aprobar Tutor y Asignar Materias">
            </form>
          </div>
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
