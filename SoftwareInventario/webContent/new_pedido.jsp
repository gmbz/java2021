<%@page import="models.*"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<%
Cliente cliente = (Cliente) request.getAttribute("cliente");
PedidoDetalle pd = (PedidoDetalle) request.getAttribute("pedido_detalle");
LinkedList<Producto> lista = (LinkedList<Producto>) request.getAttribute("lista");
%>
<body>
	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h1 class="text-center">Nuevo pedido</h1>
		<p>
			Cliente:
			<%=cliente.getNombre() + " " + cliente.getApellido()%></p>
		<div class="row">
			<div class="col-8">
				<table class="table table-striped table-sm">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">Descripcion</th>
							<th scope="col">Marca</th>
							<th scope="col">Stock</th>
							<th scope="col">Precio</th>
							<th scope="col">Proveedor</th>
							<th scope="col">Categoria</th>
							<th scope="col"></th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Producto prod : lista) {
						%>
						<tr>
							<th scope="row"><%=prod.getId()%></th>
							<td><%=prod.getDescrip()%></td>
							<td><%=prod.getMarca()%></td>
							<td><%=prod.getStock()%></td>
							<td><%=prod.getPrecio()%></td>
							<td><%=prod.getProveedor().getNombre()%></td>
							<td><%=prod.getCategoria().getDescripcion()%></td>
							<td>
								<div class="">
									<form action="pedidoServlet" method="POST" class="col-12">
										<input type="hidden" name="opcion" value="addProduct">
										<input type="hidden" name="idProd" value="<%=prod.getId()%>">
										<input type="hidden" name="idCliente"
											value="<%=cliente.getId_cliente()%>"> <input
											type="hidden" name="idPedDet" value="<%=pd.getId_detalle()%>">
										<div class="form-group">
											<label for="inputCantidad">Cantidad</label> <input
												type="text" class="form-control" id="inputCantidad"
												placeholder="Ingresar cantidad" name="cantidad">
										</div>
										<div class="d-flex justify-content-center mt-3">
											<button type="submit" class="btn btn-primary">Agregar</button>
										</div>
									</form>
								</div>
							</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
			<div class="col-4 justify-content-center">
				<table class="table">
					<thead>
						<tr>
							<th scope="col">Descripcion</th>
							<th scope="col">Cantidad</th>
							<th scope="col">Precio</th>
							<th scope="col"></th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Map.Entry<Producto, ItemPedido> producto : pd.getProductos().entrySet()) {
						%>
						<tr>
							<td><%=producto.getKey().getDescrip()%></td>
							<td><%=producto.getValue().getCantidad()%></td>
							<td><%=producto.getValue().getSubtotal()%></td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
				<a class="btn btn-primary"
					href="pedidoServlet?opcion=crearPedido&idPedDet=<%=pd.getId_detalle()%>&idCliente=<%=cliente.getId_cliente()%>"
					role="button">Crear pedido</a>
			</div>
		</div>
	</div>
	<%@ include file="scripts.html"%>
</body>
</html>