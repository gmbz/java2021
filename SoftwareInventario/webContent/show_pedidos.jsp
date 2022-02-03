<%@page import="models.Pedido"%>
<%@page import="models.Producto"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
LinkedList<Pedido> lista = (LinkedList<Pedido>) request.getAttribute("lista");
%>
<body>

	<%@ include file="navigation.jsp"%>

	<div class="container">
		<div class="row justify-content-center">
			<form action="pedidoServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="listaByCliente">
				<div class="form-group">
					<label for="inputCliente">ID cliente</label> <input type="text"
						class="form-control" id="inputCliente" placeholder="Ingresar ID"
						name=idCliente>
				</div>
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-primary">Buscar</button>
				</div>
			</form>
		</div>
	</div>

	<div class="container">
		<h2>Pedidos</h2>
		<table class="table table-striped table-sm">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Fecha</th>
					<th scope="col">Cliente</th>
					<th scope="col">Importe</th>
					<th scope="col">Detalle</th>

				</tr>
			</thead>
			<tbody>
				<%
				for (Pedido ped : lista) {
				%>
				<tr>
					<th scope="row" class="align-middle"><%=ped.getNro_pedido()%></th>
					<td class="align-middle"><%=ped.getFecha()%></td>
					<td class="align-middle"><%=ped.getCliente().getNombre() + " " + ped.getCliente().getApellido()%></td>
					<td class="align-middle"><%=ped.getDetalle().getImporte()%></td>
					<td class="align-middle"><a class="btn btn-primary"
						href="pedidoServlet?opcion=verDetalle&idPedido=<%=ped.getNro_pedido()%>"
						role="button">Ver Detalle</a></td>
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