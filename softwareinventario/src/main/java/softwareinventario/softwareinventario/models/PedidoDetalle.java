package softwareinventario.softwareinventario.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedido_detalles")
public class PedidoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private int id_detalle_2;
    @Column(name = "subtotal")
    private double importe;
    @OneToOne
    @JoinColumn(name = "nro_pedido")
    private Pedido pedido;
    @OneToMany(mappedBy = "pedido_detalle", cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    private List<ItemPedido> items_pedidos;

    public PedidoDetalle() {
        this.items_pedidos = new ArrayList<>();
        this.importe = 0;
    }

    public void addItemPedido(ItemPedido itemPedido) {
        this.items_pedidos.add(itemPedido);
    }

    public int getId_detalle() {
        return id_detalle_2;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle_2 = id_detalle;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<ItemPedido> getItems_pedidos() {
        return items_pedidos;
    }

    public void setItems_pedidos(List<ItemPedido> items_pedidos) {
        this.items_pedidos = items_pedidos;
    }

    @Override
    public String toString() {
        return "PedidoDetalle [id_detalle=" + id_detalle_2 + ", importe=" + importe + ", items_pedidos=" + items_pedidos
                + ", pedido=" + pedido + "]";
    }

}
