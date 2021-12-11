package models;

public class PedidoDetalle {
	private Pedido pedido;
	private Producto producto;
	private int cantidad;
	private double importe;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "PedidoDetalle [pedido=" + pedido + ", producto=" + producto + ", cantidad=" + cantidad + ", importe="
				+ importe + "]";
	}

}
