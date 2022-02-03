<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<body>

	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2 class="text-center">Nuevo proveedor</h2>
		<div class="row justify-content-center">
			<form action="proveedorServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="create">
				<div class="form-group">
					<label for="inputFirstName">Nombre proveedor</label> <input
						type="text" class="form-control" id="inputFirstName"
						placeholder="Ingresar nombre proveedor" name="nombreProv">
				</div>
				<div class="form-group">
					<label for="inputLastName">Apellido proveedor</label> <input
						type="text" class="form-control" id="inputLastName"
						placeholder="Ingresar apellido proveedor" name="apellidoProv">
				</div>
				<div class="form-group">
					<label for="inputTel">Telefono</label> <input type="text"
						class="form-control" id="inputTel" placeholder="Ingresar telefono"
						name="telProv">
				</div>
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-primary">Registrar
						proveedor</button>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="scripts.html"%>
</body>
</html>