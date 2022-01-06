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
LinkedList<Producto> lista = (LinkedList<Producto>) request.getAttribute("lista");
%>
</head>
<body>

	<%@ include file="navigation.jsp"%>
	<div>
		<a class="btn btn-success btn-sm"
			href="productoServlet?opcion=generarExcel" role="button">Generar
			Excel</a>
	</div>

	<div>
		<form action="productoServlet?opcion=leerExcel" method="POST"
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
		<table class="table">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Descripcion</th>
					<th scope="col">Marca</th>
					<th scope="col">Stock</th>
					<th scope="col">Precio</th>
					<th scope="col">Proveedor</th>
					<th scope="col">Categoria</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Producto prod : lista) {
				%>
				<tr>
					<th scope="row"><%=prod.getId()%></th>
					<td><%=prod.getDescrip()%></td>
					<td><%=prod.getMarca()%></td>
					<td><%=prod.getStock()%></td>
					<td><%=prod.getPrecio()%></td>
					<td><%=prod.getProveedor().getNombre()%></td>
					<td><%=prod.getCategoria().getDescripcion()%></td>
					<td><a class="btn btn-primary btn-sm"
						href="productoServlet?opcion=buscar&idProd=<%=prod.getId()%>"
						role="button">Editar</a> <a class="btn btn-danger btn-sm"
						href="productoServlet?opcion=borrar&idProd=<%=prod.getId()%>"
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