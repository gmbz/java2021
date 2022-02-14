package softwareinventario.softwareinventario.routes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import softwareinventario.softwareinventario.controller.ClienteController;
import softwareinventario.softwareinventario.controller.PedidoController;
import softwareinventario.softwareinventario.controller.ProductoController;
import softwareinventario.softwareinventario.models.*;
import softwareinventario.softwareinventario.service.IClienteService;
import softwareinventario.softwareinventario.service.IPedidoService;
import softwareinventario.softwareinventario.service.IProductoService;

@Controller
@RequestMapping("/pedidos")
public class Pedidos {
    PedidoController ped_controller = new PedidoController();
    ClienteController cli_controller = new ClienteController();
    ProductoController prod_controller = new ProductoController();

    @Autowired
    private IPedidoService pedidoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProductoService productoService;

    @GetMapping(value = "/")
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = ped_controller.listarPedidos(pedidoService);
        List<Cliente> clientes = cli_controller.listarClientes(clienteService);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pedidos", pedidos);
        return "show_pedidos";
    }

    @GetMapping(value = "/seleccionar_cliente")
    public String seleccionarCliente(Model model) {
        List<Cliente> clientes = cli_controller.listarClientes(clienteService);
        model.addAttribute("clientes", clientes);
        model.addAttribute("titulo", "Seleccionar cliente");
        return "seleccionar_cliente";
    }

    @GetMapping("/nuevo_pedido")
    public String nuevoPedido(Model model, @RequestParam(name = "idCliente") int id_cliente) {
        Cliente request_cliente = new Cliente();
        List<Producto> productos = prod_controller.listaProductos(productoService);
        request_cliente.setId_cliente(id_cliente);
        Pedido pedido = ped_controller.newPedido(request_cliente, pedidoService, clienteService);
        model.addAttribute("productos", productos);
        model.addAttribute("pedido", pedido);
        model.addAttribute("titulo", "Nuevo pedido");
        return "new_pedido";
    }

    @PostMapping("/add_product")
    public String addProduct(Model model, @RequestParam("idProducto") int id_producto,
            @RequestParam("nroPedido") int nro_pedido, @RequestParam("cantidad") int cantidad) {
        ItemPedido item_pedido = new ItemPedido();
        List<Producto> productos = prod_controller.listaProductos(productoService);

        Pedido pedido = new Pedido();
        pedido.setNro_pedido(nro_pedido);

        Producto producto = new Producto();
        producto.setId(id_producto);

        item_pedido.setProducto(producto);
        item_pedido.setCantidad(cantidad);

        Pedido updated_pedido = ped_controller.addProduct(item_pedido, pedido, productoService, pedidoService);

        model.addAttribute("productos", productos);
        model.addAttribute("pedido", updated_pedido);
        model.addAttribute("titulo", "Nuevo pedido");
        return "new_pedido";
    }

    @GetMapping(value = "/create")
    public String createPedido(@RequestParam("nroPedido") int nro_pedido) {
        Pedido pedido = new Pedido();
        pedido.setNro_pedido(nro_pedido);
        ped_controller.finalizarPedido(pedido, pedidoService, productoService);
        return "redirect:/";
    }

    @GetMapping("/ver_detalle")
    public String verDetalle(Model model, @RequestParam("idPedido") int id_pedido) {
        Pedido request_pedido = new Pedido();
        Pedido pedido = new Pedido();
        request_pedido.setNro_pedido(id_pedido);
        pedido = ped_controller.getById(request_pedido, pedidoService);

        model.addAttribute("pedido", pedido);
        model.addAttribute("titulo", "Detalle");
        return "show_detalle";
    }

    @PostMapping(value = "/cliente")
    public String postMethodName(@RequestParam("idCliente") int id_cliente, Model model) throws Exception {
        Cliente request_cliente = new Cliente();
        request_cliente.setId_cliente(id_cliente);
        Cliente cliente = cli_controller.getById(request_cliente, clienteService);
        List<Pedido> pedidos = ped_controller.listarPedidosByCliente(cliente, pedidoService, clienteService);
        List<Cliente> clientes = cli_controller.listarClientes(clienteService);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pedidos", pedidos);
        return "show_pedidos";
    }
}
