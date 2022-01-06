package servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ClienteDb;
import models.Cliente;

/**
 * Servlet implementation class ClienteServlet
 */
@WebServlet("/clienteServlet")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClienteServlet() {
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
			ClienteDb db_cli = new ClienteDb();
			LinkedList<Cliente> lista = new LinkedList<>();
			lista = db_cli.getAll();
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_clientes.jsp");
			rd.forward(request, response);
		} else if (opcion.equals("borrar")) {
			ClienteDb db_cli = new ClienteDb();
			Cliente cli = new Cliente();
			cli.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			db_cli.delete(cli);
			RequestDispatcher rd = request.getRequestDispatcher("clienteServlet?opcion=listar");
			rd.forward(request, response);
		} else if (opcion.equals("buscar")) {
			ClienteDb db_cli = new ClienteDb();
			Cliente cli = new Cliente();
			Cliente c = new Cliente();
			cli.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			c = db_cli.getById(cli);
			request.setAttribute("cliente", c);
			RequestDispatcher rd = request.getRequestDispatcher("update_cliente.jsp");
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
		ClienteDb db_cli = new ClienteDb();
		Cliente cli = new Cliente();
		if (opcion.equals("create")) {
			cli.setNombre(request.getParameter("nombreCliente"));
			cli.setApellido(request.getParameter("apellidoCliente"));
			cli.setDireccion(request.getParameter("direcCliente"));
			cli.setEmail(request.getParameter("emailCliente"));
			cli.setTel(request.getParameter("telCliente"));
			db_cli.create(cli);
			RequestDispatcher rd = request.getRequestDispatcher("clienteServlet?opcion=listar");
			rd.forward(request, response);
		} else if (opcion.equals("editar")) {
			cli.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			cli.setNombre(request.getParameter("nombreCliente"));
			cli.setApellido(request.getParameter("apellidoCliente"));
			cli.setDireccion(request.getParameter("direcCliente"));
			cli.setEmail(request.getParameter("emailCliente"));
			cli.setTel(request.getParameter("telCliente"));
			db_cli.update(cli);
			RequestDispatcher rd = request.getRequestDispatcher("clienteServlet?opcion=listar");
			rd.forward(request, response);
		} else if (opcion.equals("listar")) {
			LinkedList<Cliente> lista = new LinkedList<>();
			lista = db_cli.getAll();
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_clientes.jsp");
			rd.forward(request, response);
		}
		
	}

}
