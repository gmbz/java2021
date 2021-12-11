package servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.CategoriaDb;
import models.Categoria;

/**
 * Servlet implementation class CategoriaServlet
 */
@WebServlet("/categoriaServlet")
public class CategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriaServlet() {
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
		CategoriaDb db_cat = new CategoriaDb();
		if (opcion.equals("listar")) {
			LinkedList<Categoria> lista = new LinkedList<>();
			lista = db_cat.getAll();
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_categorias.jsp");
			rd.forward(request, response);
		 }else if (opcion.equals("borrar")) {
			Categoria cat = new Categoria();
			cat.setId(Integer.parseInt(request.getParameter("idCat")));
			db_cat.delete(cat);
			RequestDispatcher rd = request.getRequestDispatcher("categoriaServlet?opcion=listar");
			rd.forward(request, response);
		} else if (opcion.equals("buscar")) {
			Categoria cat = new Categoria();
			Categoria c = new Categoria();
			cat.setId(Integer.parseInt(request.getParameter("idCat")));
			c = db_cat.getById(cat);
			request.setAttribute("categoria", c);
			RequestDispatcher rd = request.getRequestDispatcher("update_categoria.jsp");
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
		CategoriaDb db_cat = new CategoriaDb();
		Categoria cat = new Categoria();
		if (opcion.equals("create")) {
			cat.setDescripcion(request.getParameter("descripCat"));
			db_cat.create(cat);
			RequestDispatcher rd = request.getRequestDispatcher("categoriaServlet?opcion=listar");
			rd.forward(request, response);
		} else if (opcion.equals("editar")) {
			cat.setId(Integer.parseInt(request.getParameter("idCat")));
			cat.setDescripcion(request.getParameter("descripCat"));
			db_cat.update(cat);
			RequestDispatcher rd = request.getRequestDispatcher("categoriaServlet?opcion=listar");
			rd.forward(request, response);
		} else if (opcion.equals("listar")) {
			LinkedList<Categoria> lista = new LinkedList<>();
			lista = db_cat.getAll();
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_categorias.jsp");
			rd.forward(request, response);
		}
	}

}
