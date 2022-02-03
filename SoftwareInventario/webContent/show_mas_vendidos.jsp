<%@page import="java.util.LinkedList"%>
<%@page import="models.Producto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
LinkedList<Producto> productos_mas_vendidos = (LinkedList<Producto>) request.getAttribute("productos_mas_vendidos");
%>
<body>
	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2>Productos mas vendidos</h2>
		<table class="table table-striped table-sm">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Producto</th>
					<th scope="col">Cantidad vendidos</th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Producto producto : productos_mas_vendidos) {
				%>
				<tr>
					<th scope="row" class="align-middle"><%=producto.getId()%></th>
					<td class="align-middle"><%=producto.getDescrip()%></td>
					<td class="align-middle"><%=producto.getTotalVendidos()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

	</div>

	<%@ include file="scripts.html"%>
</body>
</html>