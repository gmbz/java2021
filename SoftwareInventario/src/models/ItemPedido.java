package models;

public class ItemPedido {
	private int id_detalle;
	private int id_producto;
	private int cantidad;
	private double subtotal;

	public int getId_detalle() {
		return id_detalle;
	}

	public void setId_detalle(int id_detalle) {
		this.id_detalle = id_detalle;
	}

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	@Override
	public String toString() {
		return "ItemPedido [id_detalle=" + id_detalle + ", id_producto=" + id_producto + ", cantidad=" + cantidad
				+ ", subtotal=" + subtotal + "]";
	}

}
