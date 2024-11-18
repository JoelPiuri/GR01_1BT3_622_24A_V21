<%@ page language="java" pageEncoding="UTF-8"%>
<header>
    <style>
        header {
            background-color: #f8f9fa;
            padding: 10px;
            text-align: right;
        }
        .header-btn {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 5px 10px;
            margin-left: 10px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
        }
        .logout-btn {
            background-color: #dc3545;
        }
        .username {
            font-weight: bold;
            margin-right: 10px;
        }
    </style>
    <!-- Botón de regreso -->
    <a href="javascript:history.back()" class="header-btn">Regresar</a>

    <!-- Mostrar saludo y botón de logout -->
    <c:if test="${not empty sessionScope.nombre && not empty sessionScope.apellido && not empty sessionScope.rolUsuario}">
        <span class="username">
            Hola ${sessionScope.rolUsuario} ${sessionScope.nombre} ${sessionScope.apellido}, Bienvenid@
        </span>
        <form action="${pageContext.request.contextPath}/LogoutServlet" method="post" style="display: inline;">
            <button type="submit" class="header-btn logout-btn">Sign Out</button>
        </form>
    </c:if>
</header>
