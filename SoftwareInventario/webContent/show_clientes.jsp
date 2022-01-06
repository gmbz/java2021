<%@page import="models.Cliente"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%
LinkedList<Cliente> lista = (LinkedList<Cliente>) request.getAttribute("lista");
%>
</head>
<body>

	<%@ include file="navigation.jsp"%>

	<div class="container">
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
				for (Cliente cli : lista) {
				%>
				<tr>
					<th scope="row"><%=cli.getId_cliente()%></th>
					<td><%=cli.getNombre()%></td>
					<td><%=cli.getApellido()%></td>
					<td><%=cli.getDireccion()%></td>
					<td><%=cli.getEmail()%></td>
					<td><%=cli.getTel()%></td>
					<td><a class="btn btn-primary btn-sm"
						href="clienteServlet?opcion=buscar&idCliente=<%=cli.getId_cliente()%>"
						role="button">Editar</a> <a class="btn btn-danger btn-sm"
						href="clienteServlet?opcion=borrar&idCliente=<%=cli.getId_cliente()%>"
						role="button">Eliminar</a></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>


</body>
</html>