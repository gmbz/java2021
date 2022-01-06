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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String opcion = request.getParameter("opcion");
		PedidoController ped_controller = new PedidoController();
		ProductoController prod_controller = new ProductoController();
		
		if (opcion.equals("listar")) {
			LinkedList<Pedido> lista = new LinkedList<>();
			
			lista = ped_controller.listarPedidos();
			
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_pedidos.jsp");
			rd.forward(request, response);
			
		} else if (opcion.equals("nuevo")) {
			PedidoDetalle pd = new PedidoDetalle();
			LinkedList<Producto> lista_productos = new LinkedList<>();
			
			lista_productos = prod_controller.listaProductos();
			ped_controller.crearDetalle(pd);
			
			request.setAttribute("lista", lista_productos);
			request.setAttribute("pedido_detalle", pd);
			RequestDispatcher rd = request.getRequestDispatcher("new_pedido.jsp");
			rd.forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String opcion = request.getParameter("opcion");
		PedidoController ped_controller = new PedidoController();
		ClienteController cli_controller = new ClienteController();
		ProductoController prod_controller = new ProductoController();
		
		if (opcion.equals("crearPedido")) {
			PedidoDetalle pd = new PedidoDetalle();
			Pedido ped = new Pedido();
			Cliente c = new Cliente();
			Cliente cli = new Cliente();
			
			c.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			cli = cli_controller.getById(c);
			pd.setId_detalle(Integer.parseInt(request.getParameter("idPedDet")));
			ped.setDetalle(pd);
			ped_controller.newPedido(ped, cli);			
			
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
			
		} else if (opcion.equals("addProduct")) {
			PedidoDetalle pd = new PedidoDetalle();
			ItemPedido ip = new ItemPedido();
			LinkedList<Producto> lista_productos = new LinkedList<>();
			
			pd.setId_detalle(Integer.parseInt(request.getParameter("idPedDet")));
			
			ip.setId_producto(Integer.parseInt(request.getParameter("idProd")));
			ip.setId_detalle(Integer.parseInt(request.getParameter("idPedDet")));
			ip.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
			
			ped_controller.addProduct(ip);
			lista_productos = prod_controller.listaProductos();
			
			request.setAttribute("lista", lista_productos);
			request.setAttribute("pedido_detalle", pd);
			RequestDispatcher rd = request.getRequestDispatcher("new_pedido.jsp");
			rd.forward(request, response);
		}
	}

}
