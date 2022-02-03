package controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

import database.ClienteDb;
import database.PedidoDb;
import database.ProductoDb;
import models.Cliente;
import models.ItemPedido;
import models.Pedido;
import models.PedidoDetalle;
import models.Producto;

public class PedidoController {
	PedidoDb db_ped = new PedidoDb();
	ClienteDb db_cli = new ClienteDb();

	public LinkedList<Pedido> listarPedidos() {
		return db_ped.getAll();
	}

	public LinkedList<Pedido> listarPedidosByCliente(Cliente cli) {
		Cliente c = new Cliente();

		c = db_cli.getById(cli);
		return db_ped.getAllByCliente(c);
	}

	public Pedido getById(Pedido ped) {
		Pedido pedido = new Pedido();
		pedido = db_ped.getById(ped);
		pedido.setCliente(db_cli.getById(pedido.getCliente()));
		return pedido;
	}
	
	public void newPedido(Pedido ped, Cliente cli) {
		ped.setFecha(LocalDate.now());
		db_ped.newPedido(ped, cli);
		db_ped.insertPedDet(ped);

		PedidoDetalle pd = new PedidoDetalle();
		pd = db_ped.setDetalleProducto(ped);
		calculaImporte(pd);
		db_ped.updateImporte(pd);

		ProductoController prod_controller = new ProductoController();
		prod_controller.updateStock(ped);

	}

	public void crearDetalle(PedidoDetalle pd) {
		db_ped.createDetalle(pd);
	}

	public void addProduct(ItemPedido ip) {
		Producto prod = new Producto();
		Producto p = new Producto();
		ProductoDb db_prod = new ProductoDb();

		prod.setId(ip.getId_producto());
		p = db_prod.getById(prod);
		if (p.getStock() >= ip.getCantidad()) { // recordar hacer alerta para avisar al usuario
			ip.setSubtotal(ip.getCantidad() * p.getPrecio());
			db_ped.addProduct(ip);
		}

	}
	
	public PedidoDetalle obtenerItemsPedidos(PedidoDetalle pd) {
		return db_ped.getItemsPedidos(pd);
	}

	private void calculaImporte(PedidoDetalle pd) {
		double importe = 0;
		for (Map.Entry<Producto, ItemPedido> producto : pd.getProductos().entrySet()) {
			importe += producto.getValue().getSubtotal();
		}
		pd.setImporte(importe);
	}

}
