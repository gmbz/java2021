<%@page import="models.Cliente"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
LinkedList<Cliente> lista = (LinkedList<Cliente>) request.getAttribute("lista");
%>
<body>

	<%@ include file="navigation.jsp"%>

	<div>
		<a class="btn btn-success btn-sm"
			href="clienteServlet?opcion=generarExcel" role="button">Generar
			Excel</a>
	</div>

	<div>
		<form action="clienteServlet?opcion=leerExcel" method="POST"
			enctype="multipart/form-data" class="col-4">
			<div class="form-group d-flex">
				<div class="input-group mb-3">
					<label for="formFile" class="form-label mt-4"></label> <input
						class="form-control" type="file" id="formFile" name="inputExcel"
						aria-describedby="button-addon">
					<button type="submit" class="btn btn-info btn-sm"
						aria-describedby="button-addon">Importar Excel</button>
				</div>
			</div>
		</form>
	</div>

	<div class="container">
		<table class="table table-striped">
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
	<%@ include file="scripts.html"%>
</body>
</html>