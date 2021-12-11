<%@page import="models.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<%
Categoria cat = (Categoria) request.getAttribute("categoria");
%>

<title>Insert title here</title>
</head>
<body>

	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2 class="text-center">Actualizar categoria</h2>
		<div class="row justify-content-center">
			<form action="categoriaServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="editar">
				<div class="form-group">
					<label for="inputProduct">ID</label> <input type="hidden"
						class="form-control" name="idCat" placeholder="<%=cat.getId()%>"
						value="<%=cat.getId()%>">
				</div>
				<div class="form-group">
					<label for="inputCat">Descripcion categoria</label> <input
						type="text" class="form-control" id="inputCat"
						placeholder="<%=cat.getDescripcion()%>" name="descripCat"
						value="<%=cat.getDescripcion()%>">
				</div>
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-primary">Actualizar</button>
				</div>
			</form>
		</div>
	</div>

</body>
</html>