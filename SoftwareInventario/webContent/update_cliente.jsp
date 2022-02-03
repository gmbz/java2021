<%@page import="models.Cliente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
Cliente cli = (Cliente) request.getAttribute("cliente");
%>
<body>

	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2 class="text-center">Actualizar cliente</h2>
		<div class="row justify-content-center">
			<form action="clienteServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="editar">
				<div class="form-group">
					<label for="inputCliente">ID</label> <input type="hidden"
						class="form-control" name="idCliente"
						placeholder="<%=cli.getId_cliente()%>"
						value="<%=cli.getId_cliente()%>">
				</div>
				<div class="form-group">
					<label for="inputNombre">Nuevo nombre</label> <input type="text"
						class="form-control" id="inputNombre"
						placeholder="<%=cli.getNombre()%>" name="nombreCliente"
						value="<%=cli.getNombre()%>">
				</div>
				<div class="form-group">
					<label for="inputApellido">Nuevo apellido</label> <input
						type="text" class="form-control" id="inputApellido"
						placeholder="<%=cli.getApellido()%>" name="apellidoCliente"
						value="<%=cli.getApellido()%>">
				</div>
				<div class="form-group">
					<label for="inputDireccion">Nueva direccion</label> <input
						type="text" class="form-control" id="inputDireccion"
						placeholder="<%=cli.getDireccion()%>" name="direcCliente"
						value="<%=cli.getDireccion()%>">
				</div>
				<div class="form-group">
					<label for="inputEmail">Nuevo email</label> <input type="text"
						class="form-control" id="inputEmail"
						placeholder="<%=cli.getEmail()%>" name="emailCliente"
						value="<%=cli.getEmail()%>">
				</div>
				<div class="form-group">
					<label for="inputTel">Nuevo telefono</label> <input type="text"
						class="form-control" id="inputTel" placeholder="<%=cli.getTel()%>"
						name="telCliente" value="<%=cli.getTel()%>">
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