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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@4.5.2/dist/sandstone/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
	integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
	integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
	crossorigin="anonymous"></script>

<title>Insert title here</title>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<div class="container-fluid">
			<a class="navbar-brand" href="index.jsp">XD</a>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link"
						href="new_product.jsp">Nuevo Producto</a></li>
					<li class="nav-item"><a class="nav-link"
						href="productoServlet?opcion=listar">Listar Producto</a></li>
					<li class="nav-item"><a class="nav-link"
						href="new_proveedor.jsp">Nuevo Proveedor</a></li>
					<li class="nav-item"><a class="nav-link"
						href="proveedorServlet?opcion=listar">Listar Proveedores</a></li>
					<li class="nav-item"><a class="nav-link"
						href="new_categoria.jsp">Nueva Categoria</a></li>
					<li class="nav-item"><a class="nav-link"
						href="categoriaServlet?opcion=listar">Listar Categorias</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pedidoServlet?opcion=nuevo">Nuevo Pedido</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pedidoServlet?opcion=listar">Listar Pedidos</a></li>
					<li class="nav-item"><a class="nav-link"
						href="new_cliente.jsp">Nuevo Cliente</a></li>
					<li class="nav-item"><a class="nav-link"
						href="clienteServlet?opcion=listar">Listar Clientes</a></li>
				</ul>
			</div>
		</div>
	</nav>

</body>
</html>