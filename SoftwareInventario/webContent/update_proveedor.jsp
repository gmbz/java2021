<%@page import="models.Proveedor"%>
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
Proveedor prov = (Proveedor) request.getAttribute("proveedor");
%>

<title>Insert title here</title>
</head>
<body>

	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2 class="text-center">Actualizar proveedor</h2>
		<div class="row justify-content-center">
			<form action="proveedorServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="editar">
				<div class="form-group">
					<label for="inputProveedor">ID</label> <input type="hidden"
						class="form-control" name="idProv" placeholder="<%=prov.getId()%>"
						value="<%=prov.getId()%>">
				</div>
				<div class="form-group">
					<label for="inputFirstName">Nombre proveedor</label> <input
						type="text" class="form-control" id="inputFirstName"
						placeholder="<%=prov.getNombre()%>" name="nombreProv"
						value="<%=prov.getNombre()%>">
				</div>
				<div class="form-group">
					<label for="inputLastName">Apellido proveedor</label> <input
						type="text" class="form-control" id="inputLastName"
						placeholder="<%=prov.getApellido()%>" name="apellidoProv"
						value="<%=prov.getApellido()%>">
				</div>
				<div class="form-group">
					<label for="inputTel">Telefono</label> <input type="text"
						class="form-control" id="inputTel"
						placeholder="<%=prov.getTel()%>" name="telProv"
						value="<%=prov.getTel()%>">
				</div>
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-primary">Actualizar
						proveedor</button>
				</div>
			</form>
		</div>
	</div>

</body>
</html>