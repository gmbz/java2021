package softwareinventario.softwareinventario.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(ItemPedidoId.class)
@Table(name = "item_pedidos")
public class ItemPedido implements Serializable {

    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "precio_total")
    private double subtotal;
    @Id
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "id_detalle")
    private PedidoDetalle pedido_detalle;
    @Id
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "id_producto")
    private Producto producto;

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

    // @Override
    // public String toString() {
    //     return "ItemPedido [cantidad=" + cantidad + ", pedido_detalle=" + pedido_detalle + ", producto=" + producto
    //             + ", subtotal=" + subtotal + "]";
    // }

}
