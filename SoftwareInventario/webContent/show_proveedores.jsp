<%@page import="models.Proveedor"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Insert title here</title>

<%
LinkedList<Proveedor> lista = (LinkedList<Proveedor>) request.getAttribute("lista");
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

</body>
</html>