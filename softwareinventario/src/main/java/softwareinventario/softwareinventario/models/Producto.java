package softwareinventario.softwareinventario.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private int id;
    @Column(name = "descripcion")
    @NotEmpty
    private String descrip;
    @Column(name = "stock")
    private int stock;
    @Column(name = "precio")
    @NotNull
    @Positive
    private double precio;
    @Column(name = "total_vendidos")
    private int totalVendidos;
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;
    @OneToMany(mappedBy = "producto", cascade = { CascadeType.PERSIST,
    CascadeType.DETACH, CascadeType.MERGE,
    CascadeType.REFRESH })
    private List<ItemPedido> items_pedidos;

    public Producto() {
    this.items_pedidos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getTotalVendidos() {
        return totalVendidos;
    }

    public void setTotalVendidos(int totalVendidos) {
        this.totalVendidos = totalVendidos;
    }

    public List<ItemPedido> getItems_pedidos() {
        return items_pedidos;
    }

    public void setItems_pedidos(List<ItemPedido> items_pedidos) {
        this.items_pedidos = items_pedidos;
    }



    @Override
    public String toString() {
        return "Producto [categoria=" + categoria + ", descrip=" + descrip + ", id=" + id + ", precio=" + precio
                + ", proveedor=" + proveedor + ", stock=" + stock
                + ", totalVendidos=" + totalVendidos + "]";
    }

}
