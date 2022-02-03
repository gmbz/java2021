<%@page import="models.Proveedor"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
LinkedList<Proveedor> lista = (LinkedList<Proveedor>) request.getAttribute("lista");
%>
<body>

	<%@ include file="navigation.jsp"%>

	<div>
		<a class="btn btn-success btn-sm"
			href="proveedorServlet?opcion=generarExcel" role="button">Generar
			Excel</a>
	</div>

	<div>
		<form action="proveedorServlet?opcion=leerExcel" method="POST"
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
					<th scope="col">Telefono</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Proveedor prov : lista) {
				%>
				<tr>
					<th scope="row"><%=prov.getId()%></th>
					<td><%=prov.getNombre()%></td>
					<td><%=prov.getApellido()%></td>
					<td><%=prov.getTel()%></td>
					<td><a class="btn btn-primary btn-sm"
						href="proveedorServlet?opcion=buscar&idProv=<%=prov.getId()%>"
						role="button">Editar</a> <a class="btn btn-danger btn-sm"
						href="proveedorServlet?opcion=borrar&idProv=<%=prov.getId()%>"
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