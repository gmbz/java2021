package models;

import java.time.LocalDate;

public class Pedido {
	private int nro_pedido;
	private LocalDate fecha;
	private PedidoDetalle detalle;
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public PedidoDetalle getDetalle() {
		return detalle;
	}

	public void setDetalle(PedidoDetalle detalle) {
		this.detalle = detalle;
	}

	public int getNro_pedido() {
		return nro_pedido;
	}

	public void setNro_pedido(int nro_pedido) {
		this.nro_pedido = nro_pedido;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Pedido [nro_pedido=" + nro_pedido + ", fecha=" + fecha + "]";
	}

}
