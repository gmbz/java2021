<%@page import="models.*"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
Pedido pedido = (Pedido) request.getAttribute("pedido");
%>
<body>
	<%@ include file="navigation.jsp"%>

	<div class="container pt-4">
		<h2>Detalle</h2>
		<p>
			Pedido Nº:
			<%=pedido.getNro_pedido()%></p>
		<p>Fecha: <%=pedido.getFecha() %></p>
		<p>Cliente: <%=pedido.getCliente().getNombre()+" "+pedido.getCliente().getNombre() %> </p>
		<p>Direccion: <%=pedido.getCliente().getDireccion() %></p>
		<table class="table">
			<thead>
				<tr>
					<th>Producto</th>
					<th>Cantidad</th>
					<th>Precio Unitario</th>
					<th>Precio Neto</th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Map.Entry<Producto, ItemPedido> producto : pedido.getDetalle().getProductos().entrySet()) {
				%>
				<tr>
					<td><%=producto.getKey().getDescrip()%></td>
					<td><%=producto.getValue().getCantidad()%></td>
					<td><%=producto.getKey().getPrecio()%></td>
					<td><%=producto.getValue().getSubtotal()%></td>
				</tr>
				<%
				}
				%>
				<tr class="table-secondary">
					<th scope="row" colspan="3">Subtotal</th>
					<td><%=pedido.getDetalle().getImporte()%></td>
				</tr>
			</tbody>
		</table>


	</div>
	<%@ include file="scripts.html"%>
</body>
</html>