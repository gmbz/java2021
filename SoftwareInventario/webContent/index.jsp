<%@page import="models.Producto"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
Producto producto_mas_vendido = (Producto) request.getAttribute("producto_mas_vendido");
%>
<body>
	<%@ include file="navigation.jsp"%>

	<div class="container mt-4">
		<div class="card text-center" style="width: 18rem;">
			<h5 class="card-header">Producto mas vendido</h5>
			<div class="card-body">
				<p class=""><%=producto_mas_vendido.getDescrip()%></p>
				<span class="text-muted"><%=producto_mas_vendido.getTotalVendidos() + " unidades"%></span>
			</div>
		</div>
	</div>

	<%@ include file="scripts.html"%>
</body>
</html>