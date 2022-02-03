<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<body>
	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2 class="text-center">Nuevo cliente</h2>
		<div class="row justify-content-center">
			<form action="clienteServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="create">
				<div class="form-group">
					<label for="inputNombre">Nombre</label> <input type="text"
						class="form-control" id="inputNombre"
						placeholder="Ingresar nombre" name="nombreCliente">
				</div>
				<div class="form-group">
					<label for="inputApellido">Apellido</label> <input type="text"
						class="form-control" id="inputApellido"
						placeholder="Ingresar apellido" name="apellidoCliente">
				</div>
				<div class="form-group">
					<label for="inputDireccion">Direccion</label> <input type="text"
						class="form-control" id="inputDireccion"
						placeholder="Ingresar direccion" name="direcCliente">
				</div>
				<div class="form-group">
					<label for="inputEmail">Email</label> <input type="text"
						class="form-control" id="inputEmail" placeholder="Ingresar email"
						name="emailCliente">
				</div>
				<div class="form-group">
					<label for="inputTel">Telefono</label> <input type="text"
						class="form-control" id="inputTel" placeholder="Ingresar telefono"
						name="telCliente">
				</div>
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-primary">Registrar
						cliente</button>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="scripts.html"%>
</body>
</html>