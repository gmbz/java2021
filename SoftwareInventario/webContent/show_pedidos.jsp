<%@page import="models.Pedido"%>
<%@page import="models.Producto"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<%
LinkedList<Pedido> lista = (LinkedList<Pedido>) request.getAttribute("lista");
%>
</head>
<body>

	<%@ include file="navigation.jsp"%>

	<div class="container">
		<h2>Pedidos</h2>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Fecha</th>
					<th scope="col">Cliente</th>
					<th scope="col">Detalle</th>
					<th scope="col">Productos</th>
					<th scope="col">Importe</th>
					
				</tr>
			</thead>
			<tbody>
				<%
				for (Pedido ped : lista) {
				%>
				<tr>
					<th scope="row"><%=ped.getNro_pedido()%></th>
					<td><%=ped.getFecha()%></td>
					<td><%=ped.getCliente()%></td>
					<td><%=ped.getDetalle().getId_detalle()%></td>
					<td><%=ped.getDetalle().getProductos()%></td>
					<td><%=ped.getDetalle().getImporte()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>

</body>
</html>