package softwareinventario.softwareinventario.routes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import softwareinventario.softwareinventario.controller.*;
import softwareinventario.softwareinventario.models.*;
import softwareinventario.softwareinventario.service.IClienteService;
import softwareinventario.softwareinventario.service.IEstadoPedidoService;
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

    @Autowired
    private IEstadoPedidoService estadoPedidoService;

    /**
     * Vista principal de pedidos, muestra listado de todos los pedidos realizados
     * 
     * @param model
     * @return template "show_pedidos.html"
     */
    @GetMapping(value = "/")
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = ped_controller.listarPedidos(pedidoService);
        List<Cliente> clientes = cli_controller.listarClientes(clienteService);
        List<EstadoPedido> estados = ped_controller.listarEstados(estadoPedidoService);
        model.addAttribute("titulo", "Listado de pedidos");
        model.addAttribute("estados", estados);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pedidos", pedidos);
        return "show_pedidos";
    }

    /**
     * Vista que se encarga de mostrar listado de clientes para seleccionar a la
     * hora de hacer un nuevo pedido
     * 
     * @param model
     * @return template "seleccionar_cliente.html"
     */
    @GetMapping(value = "/seleccionar_cliente")
    public String seleccionarCliente(Model model) {
        List<Cliente> clientes = cli_controller.listarClientes(clienteService);
        model.addAttribute("clientes", clientes);
        model.addAttribute("titulo", "Seleccionar cliente");
        return "seleccionar_cliente";
    }

    /**
     * Vista que recibe el id del cliente que requiere hacer un pedido y luego
     * muestra informacion del cliente y listado de productos
     * 
     * @param model
     * @param id_cliente ID del cliente enviado desde la template
     *                   "seleccionar_cliente.html"
     * @return template "new_pedido.html"
     */
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

    /**
     * Vista que recibe id del producto a añadir con su cantidad y el nro del
     * pedido, añade el producto al pedido del cliente y muestra el listado de todos
     * los productos y el listado de los productos añadidos al pedido.
     * 
     * @param model
     * @param id_producto ID del producto a añadir
     * @param nro_pedido  Número del pedido
     * @param cantidad    Cantidad del producto
     * @return template "new_pedido.html"
     */
    @PostMapping("/add_product")
    public String addProduct(Model model, @RequestParam("idProducto") int id_producto,
            @RequestParam("nroPedido") int nro_pedido, @RequestParam("cantidad") int cantidad) {
        ItemPedido item_pedido = new ItemPedido();
        List<Producto> productos = prod_controller.listaProductos(productoService);

        Pedido pedido = new Pedido();
        pedido.setNro_pedido(nro_pedido);

        Producto producto = new Producto();
        producto.setId(id_producto);
        producto = prod_controller.getById(producto, productoService);

        item_pedido.setProducto(producto);
        item_pedido.setCantidad(cantidad);

        Pedido updated_pedido = ped_controller.addProduct(item_pedido, pedido, productoService, pedidoService);

        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Nuevo pedido");

        if (updated_pedido == null) {
            updated_pedido = ped_controller.getById(pedido, pedidoService);

            model.addAttribute("pedido", updated_pedido);
            model.addAttribute("error", "No hay stock solicitado.");
            return "new_pedido";
        }

        String msg = "Producto " + producto.getDescrip() + " agregado.";
        model.addAttribute("pedido", updated_pedido);
        model.addAttribute("success", msg);
        return "new_pedido";
    }

    /**
     * Vista correspondiente al ultimo paso para generar el pedido. Recibe el numero
     * del pedido y muestra el detalle
     * 
     * @param nro_pedido
     * @param model
     * @return template "pedido_ultimo_paso.html"
     */
    @GetMapping(value = "/ultimo_paso")
    public String generarFecha(@RequestParam("nroPedido") int nro_pedido, Model model) {
        Pedido pedido = new Pedido();
        pedido.setNro_pedido(nro_pedido);
        Pedido new_pedido = ped_controller.getById(pedido, pedidoService);
        ped_controller.calculaImporte(new_pedido);
        model.addAttribute("pedido", new_pedido);
        model.addAttribute("titulo", "último paso");
        return "pedido_ultimo_paso";
    }

    /**
     * Vista que registra el pedido nuevo. Recibe el numero del pedido y la fecha de
     * entrega. Muestra la pagina principal.
     * 
     * @param nro_pedido
     * @param fecha_entrega
     * @return
     */
    @PostMapping("/create")
    public String createPedidoPrueba(@RequestParam("nroPedido") String nro_pedido,
            @RequestParam("fechaEntrega") String fecha_entrega) {

        Pedido pedido = new Pedido();
        pedido.setNro_pedido(Integer.parseInt(nro_pedido));
        Pedido new_pedido = ped_controller.getById(pedido, pedidoService);
        new_pedido.setFecha_entrega(LocalDate.parse(fecha_entrega));
        ped_controller.finalizarPedido(new_pedido, pedidoService, productoService, estadoPedidoService);

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
    public String listadoByCliente(@RequestParam("idCliente") int id_cliente, Model model) throws Exception {
        Cliente request_cliente = new Cliente();
        request_cliente.setId_cliente(id_cliente);
        Cliente cliente = cli_controller.getById(request_cliente, clienteService);
        List<Pedido> pedidos = ped_controller.listarPedidosByCliente(cliente, pedidoService, clienteService);
        List<Cliente> clientes = cli_controller.listarClientes(clienteService);
        List<EstadoPedido> estados = ped_controller.listarEstados(estadoPedidoService);
        model.addAttribute("titulo", "Pedidos por cliente");
        model.addAttribute("estados", estados);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pedidos", pedidos);
        return "show_pedidos";
    }

    @PostMapping(value = "/fecha_entrega")
    public String listadoByFechaEntrega(@RequestParam("fechaEntrega") String fecha_entrega, Model model)
            throws Exception {
        Pedido ped = new Pedido();
        ped.setFecha_entrega(LocalDate.parse(fecha_entrega));
        List<Pedido> pedidos = ped_controller.listarPedidosByFechaEntrega(ped, pedidoService);
        List<Cliente> clientes = cli_controller.listarClientes(clienteService);
        List<EstadoPedido> estados = ped_controller.listarEstados(estadoPedidoService);
        model.addAttribute("titulo", "Pedidos por fecha de entrega");
        model.addAttribute("estados", estados);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pedidos", pedidos);
        return "show_pedidos";
    }

    @PostMapping("/update_estado")
    public String actualizarEstado(@RequestParam("idNuevoEstado") int id_estado,
            @RequestParam("idPedido") int nro_pedido) {

        EstadoPedido estado = new EstadoPedido();
        estado.setId_estado(id_estado);
        Pedido pedido = new Pedido();
        pedido.setNro_pedido(nro_pedido);
        pedido.setEstado(estado);
        ped_controller.updateEstado(pedido, pedidoService, estadoPedidoService);
        return "redirect:/pedidos/";
    }
}
