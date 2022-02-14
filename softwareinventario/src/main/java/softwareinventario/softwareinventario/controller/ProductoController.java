package softwareinventario.softwareinventario.controller;

import java.util.List;

import softwareinventario.softwareinventario.models.ItemPedido;
import softwareinventario.softwareinventario.models.Pedido;
import softwareinventario.softwareinventario.models.Producto;
import softwareinventario.softwareinventario.service.IProductoService;

public class ProductoController {

    public List<Producto> listaProductos(IProductoService productoService) {
        return productoService.findAll();
    }

    public Producto obtenerElMasVendido(IProductoService productoService) {
        List<Producto> productos = productoService.findTopSellers();
        Producto producto = productos.get(0);
        return producto;
    }

    public List<Producto> listaMasVendidos(IProductoService productoService) {
        return productoService.findTopSellers();
    }

    public Producto getById(Producto prod, IProductoService productoService) {
        return productoService.findById(prod);
    }

    public void newProduct(Producto prod, IProductoService productoService) {
        productoService.save(prod);
    }

    public void delete(Producto prod, IProductoService productoService) {
        productoService.delete(prod);
    }

    public void update(Producto prod, IProductoService productoService) {
        productoService.save(prod);
    }

    public void updateStock(Pedido pedido, IProductoService productoService) {
        for (ItemPedido itemPedido : pedido.getDetalle().getItems_pedidos()) {
            Producto producto = productoService.findById(itemPedido.getProducto());
            producto.setStock(producto.getStock() - itemPedido.getCantidad());
            productoService.save(producto);
        }
    }
}
