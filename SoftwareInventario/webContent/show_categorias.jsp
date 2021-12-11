<%@page import="models.Categoria"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Insert title here</title>
<%
LinkedList<Categoria> lista = (LinkedList<Categoria>) request.getAttribute("lista");
%>
</head>
<body>

	<%@ include file="navigation.jsp"%>

	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Descripcion</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Categoria cat : lista) {
				%>
				<tr>
					<th scope="row"><%=cat.getId()%></th>
					<td><%=cat.getDescripcion()%></td>
					<td><a class="btn btn-primary btn-sm"
						href="categoriaServlet?opcion=buscar&idCat=<%=cat.getId()%>"
						role="button">Editar</a> <a class="btn btn-danger btn-sm"
						href="categoriaServlet?opcion=borrar&idCat=<%=cat.getId()%>"
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