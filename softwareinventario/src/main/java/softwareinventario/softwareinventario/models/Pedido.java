package softwareinventario.softwareinventario.models;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_pedido")
    private int nro_pedido;
    @Column(name = "fecha")
    private LocalDate fecha;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_detalle")
    private PedidoDetalle detalle;
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

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

    public PedidoDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(PedidoDetalle detalle) {
        this.detalle = detalle;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
