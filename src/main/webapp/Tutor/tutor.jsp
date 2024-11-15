<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
  <title>Tutor - Sistema Asistencia Virtual FIS</title>
</head>
<body>
<h2>Bienvenido, Tutor</h2>
<p>Desde aquí puedes registrar nuevas tutorías o responder solicitudes.</p>
<a href="${pageContext.request.contextPath}/RegistrarTutoriaServlet">Registrar nueva Tutoría</a>

<a href="${pageContext.request.contextPath}/VerTutoriasServlet">Ver Tutorías Creadas</a>

</body>
</html>
