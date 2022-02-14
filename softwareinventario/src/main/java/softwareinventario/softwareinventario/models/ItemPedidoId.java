package softwareinventario.softwareinventario.models;

import java.io.Serializable;

public class ItemPedidoId implements Serializable {

    private PedidoDetalle pedido_detalle;
    private Producto producto;

    public PedidoDetalle getPedido_detalle() {
        return pedido_detalle;
    }

    public void setPedido_detalle(PedidoDetalle pedido_detalle) {
        this.pedido_detalle = pedido_detalle;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
