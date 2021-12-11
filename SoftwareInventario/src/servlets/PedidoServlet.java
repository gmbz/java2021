package servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.PedidoDb;
import database.ProductoDb;
import models.Pedido;
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
		if (opcion.equals("listar")) {
			PedidoDb db_ped = new PedidoDb();
			LinkedList<Pedido> lista = new LinkedList<>();
			lista = db_ped.getAll();
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_pedidos.jsp");
			rd.forward(request, response);
		} else if (opcion.equals("borrar")) {
			ProductoDb db_prod = new ProductoDb();
			Producto prod = new Producto();
			prod.setId(Integer.parseInt(request.getParameter("idProd")));
			db_prod.delete(prod);
			RequestDispatcher rd = request.getRequestDispatcher("productoServlet?opcion=listar");
			rd.forward(request, response);
		} else if (opcion.equals("buscar")) {
			Producto prod = new Producto();
			Producto p = new Producto();
			ProductoDb db_prod = new ProductoDb();
			prod.setId(Integer.parseInt(request.getParameter("idProd")));
			p = db_prod.getById(prod);
			request.setAttribute("producto", p);
			RequestDispatcher rd = request.getRequestDispatcher("update_product.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
