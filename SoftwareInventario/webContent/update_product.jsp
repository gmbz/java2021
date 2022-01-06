<%@page import="models.Producto"%>
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
Producto prod = (Producto) request.getAttribute("producto");
%>

<title>Insert title here</title>
</head>
<body>

	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2 class="text-center">Actualizar producto</h2>
		<div class="row justify-content-center">
			<form action="productoServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="editar">
				<div class="form-group">
					<label for="inputProduct">ID</label> <input type="hidden"
						class="form-control" name="idProd" placeholder="<%=prod.getId()%>"
						value="<%=prod.getId()%>">
				</div>
				<div class="form-group">
					<label for="inputProduct">Nuevo producto</label> <input type="text"
						class="form-control" id="inputProduct"
						placeholder="<%=prod.getDescrip()%>" name="descripProd"
						value="<%=prod.getDescrip()%>">
				</div>
				<div class="form-group">
					<label for="inputMarca">Marca</label> <input type="text"
						class="form-control" id="inputMarca"
						placeholder="<%=prod.getMarca()%>" name="marca"
						value="<%=prod.getMarca()%>">
				</div>
				<div class="form-group">
					<label for="inputQuantity">Cantidad</label> <input type="text"
						class="form-control" id="inputQuantity"
						placeholder="<%=prod.getStock()%>" name="cantProd"
						value="<%=prod.getStock()%>">
				</div>
				<div class="form-group">
					<label for="inputPrice">Precio</label> <input type="text"
						class="form-control" id="inputPrice"
						placeholder="<%=prod.getPrecio()%>" name="precio"
						value="<%=prod.getPrecio()%>">
				</div>
				<div class="form-group">
					<label for="inputProveedor">ID Proveedor</label> <input type="text"
						class="form-control" id="inputProveedor"
						placeholder="<%=prod.getProveedor().getId()%>" name="idProv"
						value="<%=prod.getProveedor().getId()%>">
				</div>
				<div class="form-group">
					<label for="inputCategoria">ID Categoria</label> <input type="text"
						class="form-control" id="inputCategoria"
						placeholder="<%=prod.getCategoria().getId()%>" name="idProv"
						value="<%=prod.getCategoria().getId()%>">
				</div>
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-primary">Actualizar</button>
				</div>
			</form>
		</div>
	</div>

</body>
</html>