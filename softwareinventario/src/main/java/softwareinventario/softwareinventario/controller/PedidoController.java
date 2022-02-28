package softwareinventario.softwareinventario.controller;

import java.time.LocalDate;
import java.util.List;

//import softwareinventario.softwareinventario.database.*;
import softwareinventario.softwareinventario.models.*;
import softwareinventario.softwareinventario.service.IClienteService;
import softwareinventario.softwareinventario.service.IEstadoPedidoService;
import softwareinventario.softwareinventario.service.IPedidoService;
import softwareinventario.softwareinventario.service.IProductoService;

public class PedidoController {
    // PedidoDb db_ped = new PedidoDb();
    // ClienteDb db_cli = new ClienteDb();

    public List<Pedido> listarPedidos(IPedidoService pedidoService) {
        return pedidoService.findAll();
    }

    // public LinkedList<Pedido> listarPedidosByCliente(Cliente cli) {
    // Cliente c = new Cliente();

    // c = db_cli.getById(cli);
    // return db_ped.getAllByCliente(c);
    // }

    public List<Pedido> listarPedidosByCliente(Cliente cli, IPedidoService pedidoService,
            IClienteService clienteService) throws Exception {
        Cliente cliente = clienteService.findById(cli);
        return pedidoService.findAllByCliente(cliente);
    }

    public Pedido getById(Pedido ped, IPedidoService pedidoService) {
        return pedidoService.findById(ped);
    }

    public Pedido newPedido(Cliente cliente, IPedidoService pedidoService, IClienteService clienteService) {
        Pedido pedido = new Pedido();
        PedidoDetalle pd = new PedidoDetalle();
        Cliente cli = clienteService.findById(cliente);
        pedido.setCliente(cli);
        pedido.setDetalle(pd);
        return pedidoService.save(pedido);
    }

    public Pedido finalizarPedido(Pedido pedido, IPedidoService pedidoService, IProductoService productoService,
            IEstadoPedidoService estadoPedidoService) {

        pedido.setFecha(LocalDate.now());

        List<EstadoPedido> estados = estadoPedidoService.findAll();

        for (EstadoPedido estado : estados) {
            if (estado.getDescripcion().equals("En curso")) {
                pedido.setEstado(estado);
            }
        }

        calculaImporte(pedido);

        ProductoController prod_controller = new ProductoController();
        prod_controller.updateStock(pedido, productoService);

        return pedidoService.save(pedido);
    }

    // public void crearDetalle(PedidoDetalle pd) {
    // db_ped.createDetalle(pd);
    // }

    public Pedido addProduct(ItemPedido ip, Pedido pedido, IProductoService productoService,
            IPedidoService pedidoService) {
        Producto prod = new Producto();
        Producto p = new Producto();
        p.setId(ip.getProducto().getId());
        prod = productoService.findById(p);
        if (prod.getStock() >= ip.getCantidad()) { // recordar hacer alerta para avisar al usuario
            ip.setProducto(prod);
            ip.setSubtotal(ip.getProducto().getPrecio() * ip.getCantidad());
            Pedido updated_pedido = pedidoService.findById(pedido);
            ip.setPedido_detalle(updated_pedido.getDetalle());
            updated_pedido.getDetalle().addItemPedido(ip);
            return pedidoService.save(updated_pedido);
        }
        return null;
    }

    // public PedidoDetalle obtenerItemsPedidos(PedidoDetalle pd) {
    // return db_ped.getItemsPedidos(pd);
    // }

    private void calculaImporte(Pedido pedido) {
        double importe = 0;
        for (ItemPedido ip : pedido.getDetalle().getItems_pedidos()) {
            importe += ip.getSubtotal();
            System.out.println(ip.getSubtotal());
        }
        pedido.getDetalle().setImporte(importe);
    }

    public List<Pedido> listarPedidosByFechaEntrega(Pedido pedido, IPedidoService pedidoService) {
        return pedidoService.findByFechaEntrega(pedido);
    }

    public List<EstadoPedido> listarEstados(IEstadoPedidoService estadoPedidoService) {
        return estadoPedidoService.findAll();
    }

    public void updateEstado(Pedido pedido, IPedidoService pedidoService,
            IEstadoPedidoService estadoPedidoService) {
        EstadoPedido new_estado = estadoPedidoService.findById(pedido.getEstado());
        Pedido pedido_to_update = pedidoService.findById(pedido);

        pedido_to_update.setEstado(new_estado);
        pedidoService.save(pedido_to_update);

    }

}
