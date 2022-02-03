<%@page import="models.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
Categoria cat = (Categoria) request.getAttribute("categoria");
%>
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
	<%@ include file="scripts.html"%>
</body>
</html>