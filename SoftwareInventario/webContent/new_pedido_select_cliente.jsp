<%@page import="models.Cliente"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
LinkedList<Cliente> clientes = (LinkedList<Cliente>) request.getAttribute("lista");
%>
<body>
	<%@ include file="navigation.jsp"%>

	<div class="container">
		<h1>SELECCIONAR CLIENTE</h1>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nombre</th>
					<th scope="col">Apellido</th>
					<th scope="col">Direccion</th>
					<th scope="col">Email</th>
					<th scope="col">Telefono</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Cliente cli : clientes) {
				%>
				<tr>
					<th scope="row"><%=cli.getId_cliente()%></th>
					<td><%=cli.getNombre()%></td>
					<td><%=cli.getApellido()%></td>
					<td><%=cli.getDireccion()%></td>
					<td><%=cli.getEmail()%></td>
					<td><%=cli.getTel()%></td>
					<td><a class="btn btn-primary btn-sm"
						href="pedidoServlet?opcion=selectCliente&idCliente=<%=cli.getId_cliente()%>"
						role="button">SELECCIONAR</a>
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