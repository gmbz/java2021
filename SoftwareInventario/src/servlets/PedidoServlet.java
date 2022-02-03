package servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ClienteController;
import controller.PedidoController;
import controller.ProductoController;
import models.Cliente;
import models.ItemPedido;
import models.Pedido;
import models.PedidoDetalle;
import models.Producto;

/**
 * Servlet implementation class PedidoServlet
 */
@WebServlet("/pedidoServlet")
public class PedidoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PedidoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String opcion = request.getParameter("opcion");
		PedidoController ped_controller = new PedidoController();
		ProductoController prod_controller = new ProductoController();
		ClienteController cli_controller = new ClienteController();

		if (opcion.equals("listar")) {
			LinkedList<Pedido> lista = new LinkedList<>();

			lista = ped_controller.listarPedidos();
			
			String titulo = "Listado de pedidos";
			request.setAttribute("titulo", titulo);
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_pedidos.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("nuevo")) {
			LinkedList<Cliente> lista_clientes = new LinkedList<>();

			lista_clientes = cli_controller.listarClientes();
			
			String titulo = "Seleccionar cliente";
			request.setAttribute("titulo", titulo);
			request.setAttribute("lista", lista_clientes);
			RequestDispatcher rd = request.getRequestDispatcher("new_pedido_select_cliente.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("selectCliente")) {
			Cliente cli = new Cliente();
			Cliente cliente = new Cliente();
			PedidoDetalle pd = new PedidoDetalle();
			LinkedList<Producto> lista_productos = new LinkedList<>();

			cli.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			cliente = cli_controller.getById(cli);
			ped_controller.crearDetalle(pd);
			lista_productos = prod_controller.listaProductos();
			
			String titulo = "Nuevo pedido";
			request.setAttribute("titulo", titulo);
			request.setAttribute("cliente", cliente);
			request.setAttribute("lista", lista_productos);
			request.setAttribute("pedido_detalle", pd);
			RequestDispatcher rd = request.getRequestDispatcher("new_pedido.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("crearPedido")) {
			PedidoDetalle pd = new PedidoDetalle();
			Pedido ped = new Pedido();
			Cliente c = new Cliente();
			Cliente cli = new Cliente();

			c.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			cli = cli_controller.getById(c);
			pd.setId_detalle(Integer.parseInt(request.getParameter("idPedDet")));
			ped.setDetalle(pd);
			ped_controller.newPedido(ped, cli);

			RequestDispatcher rd = request.getRequestDispatcher("indexServlet");
			rd.forward(request, response);

		} else if (opcion.equals("verDetalle")) {
			Pedido ped = new Pedido();
			Pedido pedido = new Pedido();
			
			ped.setNro_pedido(Integer.parseInt(request.getParameter("idPedido")));
			pedido = ped_controller.getById(ped);
			
			String titulo = "Detalle";
			request.setAttribute("titulo", titulo);
			request.setAttribute("pedido", pedido);
			RequestDispatcher rd = request.getRequestDispatcher("show_detalle.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		String opcion = request.getParameter("opcion");
		PedidoController ped_controller = new PedidoController();
		ClienteController cli_controller = new ClienteController();
		ProductoController prod_controller = new ProductoController();
		
		if (opcion.equals("addProduct")) {
			Cliente cli = new Cliente();
			Cliente cliente = new Cliente();
			PedidoDetalle pd = new PedidoDetalle();
			PedidoDetalle pedido_detalle = new PedidoDetalle();
			ItemPedido ip = new ItemPedido();
			LinkedList<Producto> lista_productos = new LinkedList<>();

			cli.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			cliente = cli_controller.getById(cli);

			pd.setId_detalle(Integer.parseInt(request.getParameter("idPedDet")));
			
			ip.setId_producto(Integer.parseInt(request.getParameter("idProd")));
			ip.setId_detalle(Integer.parseInt(request.getParameter("idPedDet")));
			ip.setCantidad(Integer.parseInt(request.getParameter("cantidad")));

			ped_controller.addProduct(ip);
			lista_productos = prod_controller.listaProductos();
			pedido_detalle = ped_controller.obtenerItemsPedidos(pd);

			String titulo = "Nuevo pedido";
			request.setAttribute("titulo", titulo);
			request.setAttribute("cliente", cliente);
			request.setAttribute("lista", lista_productos);
			request.setAttribute("pedido_detalle", pedido_detalle);
			RequestDispatcher rd = request.getRequestDispatcher("new_pedido.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("listaByCliente")) {
			Cliente cli = new Cliente();
			LinkedList<Pedido> pedidos = new LinkedList<>();

			cli.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			pedidos = ped_controller.listarPedidosByCliente(cli);
			
			String titulo = "Listado de pedidos de un cliente";
			request.setAttribute("titulo", titulo);
			request.setAttribute("lista", pedidos);
			RequestDispatcher rd = request.getRequestDispatcher("show_pedidos.jsp");
			rd.forward(request, response);
		}
	}

}
