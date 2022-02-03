package controller;

import java.util.LinkedList;

import database.ProductoDb;
import models.ItemPedido;
import models.Pedido;
import models.Producto;

public class ProductoController {
	ProductoDb db_prod = new ProductoDb();
	
	public LinkedList<Producto> listaProductos() {
		return db_prod.getAll();
	}
	
	public LinkedList<Producto> listaMasVendidos() {
		return db_prod.getTopSelling();
	}
	
	public Producto obtenerElMasVendido() {
		return db_prod.getBestSeller();
	}
	
	public Producto getById(Producto prod) {
		return db_prod.getById(prod);
	}
	
	public void newProduct(Producto prod) {
		db_prod.create(prod);
	}
	
	public void delete(Producto prod) {
		db_prod.delete(prod);
	}
	
	public void update(Producto prod) {
		db_prod.update(prod);
	}

	public void updateStock(Pedido ped) {
		LinkedList<ItemPedido> productos_pedidos = new LinkedList<>();
		productos_pedidos = db_prod.getItemsPedidos(ped);
		
		for (ItemPedido producto : productos_pedidos) {
			Producto prod = new Producto();
			Producto p = new Producto();
			prod.setId(producto.getId_producto());
			p = db_prod.getById(prod);
			
			p.setStock(p.getStock()-producto.getCantidad());
			db_prod.updateStock(p);

		}
		
		
	}
	
}
