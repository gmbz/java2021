<%@page import="models.Producto"%>
<%@page import="java.util.LinkedList"%>
<%@page import="models.PedidoDetalle"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<%
PedidoDetalle pd = (PedidoDetalle) request.getAttribute("pedido_detalle");
LinkedList<Producto> lista = (LinkedList<Producto>) request.getAttribute("lista");
%>

</head>
<body>
	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2 class="text-center">
			Nuevo pedido
			<%=pd.getId_detalle()%>
		</h2>
		<div class="container">
			<table class="table">
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

							<div class="row justify-content-center">
								<form action="pedidoServlet" method="POST" class="col-4">
									<input type="hidden" name="opcion" value="addProduct">
									<input type="hidden" name="idProd" value="<%=prod.getId()%>">
									<input type="hidden" name="idPedDet"
										value="<%=pd.getId_detalle()%>">
									<div class="form-group">
										<label for="inputCantidad">Cantidad</label> <input type="text"
											class="form-control" id="inputCantidad"
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

		<div class="row justify-content-center">
			<form action="pedidoServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="crearPedido"> <input
					type="hidden" name="idPedDet" value="<%=pd.getId_detalle()%>"">
				<div class="form-group">
					<label for="inputIdCliente">ID del cliente</label> <input
						type="text" class="form-control" id="inputIdCliente"
						placeholder="Ingresar id del cliente" name="idCliente">
				</div>
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-primary">Crear pedido</button>
				</div>
			</form>
		</div>
	</div>

</body>
</html>