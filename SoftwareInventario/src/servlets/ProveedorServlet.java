package servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ProveedorController;
import models.Proveedor;

/**
 * Servlet implementation class ProveedorServlet
 */
@WebServlet("/proveedorServlet")
public class ProveedorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProveedorServlet() {
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
		ProveedorController prov_controller = new ProveedorController();
		Proveedor prov = new Proveedor();

		if (opcion.equals("listar")) {
			LinkedList<Proveedor> lista = new LinkedList<>();

			lista = prov_controller.listarProveedores();
			request.setAttribute("lista", lista);

			RequestDispatcher rd = request.getRequestDispatcher("show_proveedores.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("borrar")) {

			prov.setId(Integer.parseInt(request.getParameter("idProv")));
			prov_controller.delete(prov);

			RequestDispatcher rd = request.getRequestDispatcher("proveedorServlet?opcion=listar");
			rd.forward(request, response);

		} else if (opcion.equals("buscar")) {
			Proveedor p = new Proveedor();

			prov.setId(Integer.parseInt(request.getParameter("idProv")));
			p = prov_controller.getById(prov);

			request.setAttribute("proveedor", p);
			RequestDispatcher rd = request.getRequestDispatcher("update_proveedor.jsp");
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
		ProveedorController prov_controller = new ProveedorController();
		Proveedor prov = new Proveedor();

		if (opcion.equals("create")) {

			prov.setNombre(request.getParameter("nombreProv"));
			prov.setApellido(request.getParameter("apellidoProv"));
			prov.setTel(request.getParameter("telProv"));
			prov_controller.newProveedor(prov);

			RequestDispatcher rd = request.getRequestDispatcher("proveedorServlet?opcion=listar");
			rd.forward(request, response);

		} else if (opcion.equals("listar")) {
			LinkedList<Proveedor> lista = new LinkedList<>();

			lista = prov_controller.listarProveedores();

			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_proveedores.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("editar")) {

			prov.setId(Integer.parseInt(request.getParameter("idProv")));
			prov.setNombre(request.getParameter("nombreProv"));
			prov.setApellido(request.getParameter("apellidoProv"));
			prov.setTel(request.getParameter("telProv"));

			prov_controller.update(prov);

			RequestDispatcher rd = request.getRequestDispatcher("proveedorServlet?opcion=listar");
			rd.forward(request, response);
		}
	}

}
