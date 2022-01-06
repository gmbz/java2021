package controller;

import java.util.LinkedList;

import database.ProductoDb;
import models.Producto;

public class ProductoController {
	ProductoDb db_prod = new ProductoDb();
	
	public LinkedList<Producto> listaProductos() {
		return db_prod.getAll();
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
	
}
