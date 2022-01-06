package models;

import java.util.HashMap;

public class PedidoDetalle {
	private int id_detalle;
	private double importe;
	private HashMap<Producto, ItemPedido> productos;

	
	public HashMap<Producto, ItemPedido> getProductos() {
		return productos;
	}

	public PedidoDetalle() {
		this.productos = new HashMap<>();
	}

	public void addProduct(Producto prod, ItemPedido ip) {
		this.productos.put(prod, ip);
	}

	public int getId_detalle() {
		return id_detalle;
	}

	public void setId_detalle(int id_detalle) {
		this.id_detalle = id_detalle;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "PedidoDetalle [id_detalle=" + id_detalle + ", importe=" + importe + ", productos=" + productos + "]";
	}


}
