<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Insert title here</title>
</head>
<body>

	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2 class="text-center">Nuevo producto</h2>
		<div class="row justify-content-center">
			<form action="productoServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="create">
				<div class="form-group">
					<label for="inputProduct">Nuevo producto</label> <input type="text"
						class="form-control" id="inputProduct"
						placeholder="Ingresar producto" name="descripProd">
				</div>
				<div class="form-group">
					<label for="inputMarca">Marca</label> <input type="text"
						class="form-control" id="inputMarca"
						placeholder="Ingresar marca" name="marca">
				</div>
				<div class="form-group">
					<label for="inputQuantity">Cantidad</label> <input type="text"
						class="form-control" id="inputQuantity"
						placeholder="Ingresar cantidad" name="cantProd">
				</div>
				<div class="form-group">
					<label for="inputPrice">Precio</label> <input type="text"
						class="form-control" id="inputPrice"
						placeholder="Ingresar precio" name="precio">
				</div>
				<div class="form-group">
					<label for="inputProveedor">ID Proveedor</label> <input type="text"
						class="form-control" id="inputProveedor"
						placeholder="Ingresar ID proveedor" name="idProv">
				</div>
				<div class="form-group">
					<label for="inputCategoria">ID Categoria</label> <input type="text"
						class="form-control" id="inputCategoria"
						placeholder="Ingresar ID categoria" name="idCat">
				</div>
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-primary">Registrar
						producto</button>
				</div>
			</form>
		</div>
	</div>

</body>
</html>