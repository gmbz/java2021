<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<div class="container-fluid">
			<a class="navbar-brand" href="indexServlet">XD</a>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
						href="#" role="button" aria-haspopup="true" aria-expanded="false">Pedidos</a>
						<div class="dropdown-menu">
							<a href="pedidoServlet?opcion=nuevo" class="dropdown-item">Nuevo
								Pedido</a> <a href="pedidoServlet?opcion=listar"
								class="dropdown-item">Listar Pedidos</a>
						</div></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
						href="#" role="button" aria-haspopup="true" aria-expanded="false">Productos</a>
						<div class="dropdown-menu">
							<a href="new_product.jsp" class="dropdown-item">Nuevo
								Producto</a> <a href="productoServlet?opcion=listar"
								class="dropdown-item">Listar Productos</a>
								<a href="productoServlet?opcion=listarVendidos"
								class="dropdown-item">Mas Vendidos</a>
						</div></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
						href="#" role="button" aria-haspopup="true" aria-expanded="false">Clientes</a>
						<div class="dropdown-menu">
							<a href="new_cliente.jsp" class="dropdown-item">Nuevo Cliente</a>
							<a href="clienteServlet?opcion=listar" class="dropdown-item">Listar
								Cliente</a>
						</div></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
						href="#" role="button" aria-haspopup="true" aria-expanded="false">Proveedores</a>
						<div class="dropdown-menu">
							<a href="new_proveedor.jsp" class="dropdown-item">Nuevo
								Proveedor</a> <a href="proveedorServlet?opcion=listar"
								class="dropdown-item">Listar Proveedores</a>
						</div></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
						href="#" role="button" aria-haspopup="true" aria-expanded="false">Categorias</a>
						<div class="dropdown-menu">
							<a href="new_categoria.jsp" class="dropdown-item">Nuevo
								Categoria</a> <a href="categoriaServlet?opcion=listar"
								class="dropdown-item">Listar Categorias</a>
						</div></li>

				</ul>
			</div>
		</div>
	</nav>



</body>
</html>