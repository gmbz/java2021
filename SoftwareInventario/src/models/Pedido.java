package models;

import java.time.LocalDate;
import java.util.LinkedList;

public class Pedido {
	private int nro_pedido;
	private LocalDate fecha;
	private LinkedList<PedidoDetalle> detalle;
	
	public LinkedList<PedidoDetalle> getDetalle() {
		return detalle;
	}

	public Pedido() {
		this.detalle = new LinkedList<>();
	}
	
	public void addDetalle(PedidoDetalle detalle) {
		this.detalle.add(detalle);		
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
		return "Pedido [nro_pedido=" + nro_pedido + ", fecha=" + fecha + ", detalle=" + detalle + "]";
	}

}
