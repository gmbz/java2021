package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import controller.CategoriaController;
import models.Categoria;

/**
 * Servlet implementation class CategoriaServlet
 */
@WebServlet("/categoriaServlet")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 *100 // 100 MB
	)
public class CategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "assets";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoriaServlet() {
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
		CategoriaController cat_controller = new CategoriaController();

		if (opcion.equals("listar")) {
			LinkedList<Categoria> lista = new LinkedList<>();

			lista = cat_controller.listarCategorias();

			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_categorias.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("borrar")) {
			Categoria cat = new Categoria();
			
			cat.setId(Integer.parseInt(request.getParameter("idCat")));
			cat_controller.delete(cat);
			
			RequestDispatcher rd = request.getRequestDispatcher("categoriaServlet?opcion=listar");
			rd.forward(request, response);
			
		} else if (opcion.equals("buscar")) {
			Categoria cat = new Categoria();
			Categoria c = new Categoria();
			
			cat.setId(Integer.parseInt(request.getParameter("idCat")));
			c = cat_controller.getById(cat);
			
			request.setAttribute("categoria", c);
			RequestDispatcher rd = request.getRequestDispatcher("update_categoria.jsp");
			rd.forward(request, response);
			
		} else if (opcion.equals("generarExcel")) {
			response.setContentType("application/octet-stream");
			
			Workbook workbook = new XSSFWorkbook();

			response.setHeader("Content-Disposition", "attachment; filename=listado-categorias.xlsx");

			Sheet sheet = workbook.createSheet("Categorias");

			Row encabezado = sheet.createRow(0);
			String[] columnas = { "ID", "DESCRIPCION" };

			for (int i = 0; i < columnas.length; i++) {
				Cell celda = encabezado.createCell(i);
				celda.setCellValue(columnas[i]);
			}
			
			LinkedList<Categoria> categorias = new LinkedList<>();
			categorias = cat_controller.listarCategorias();
			int i = 1;
			for (Categoria categoria : categorias) {
				Row row = sheet.createRow(i);
				
				Cell celda = row.createCell(0);
				celda.setCellValue(categoria.getId());
				
				celda = row.createCell(1);
				celda.setCellValue(categoria.getDescripcion());
	
				i++;
			}
			ServletOutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
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
		CategoriaController cat_controller = new CategoriaController();
		Categoria cat = new Categoria();
		
		if (opcion.equals("create")) {
			
			cat.setDescripcion(request.getParameter("descripCat"));
			cat_controller.newCategory(cat);
			
			RequestDispatcher rd = request.getRequestDispatcher("categoriaServlet?opcion=listar");
			rd.forward(request, response);
			
		} else if (opcion.equals("editar")) {
			
			cat.setId(Integer.parseInt(request.getParameter("idCat")));
			cat.setDescripcion(request.getParameter("descripCat"));
			cat_controller.update(cat);
			
			RequestDispatcher rd = request.getRequestDispatcher("categoriaServlet?opcion=listar");
			rd.forward(request, response);
			
		} else if (opcion.equals("listar")) {
			
			LinkedList<Categoria> lista = new LinkedList<>();
			lista = cat_controller.listarCategorias();
			
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_categorias.jsp");
			rd.forward(request, response);
			
		} else if (opcion.equals("leerExcel")) {
			String filename = "";
			Part filePart = request.getPart("inputExcel");
			filename = getFilename(filePart);
			String applicationPath = request.getServletContext().getRealPath("");
			String basePath = applicationPath + UPLOAD_DIR + File.separator;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			
			try {
				File outputFilePath = new File(basePath + filename);
				inputStream = filePart.getInputStream();
				outputStream = new FileOutputStream(outputFilePath);
				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			} catch (Exception e) {
				filename = "";
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
			cargarExcel(basePath + filename);
		}
	}
	
	private String getFilename(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=")+1).trim().replace("\"", "");
			}
		}
		return null;
	}
	
	private void cargarExcel(String pathFile) throws IOException {
		try {
			FileInputStream file = new FileInputStream(pathFile);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			CategoriaController cat_controller = new CategoriaController();
			
			int numRows = sheet.getLastRowNum();
			
			for (int i = 1; i <= numRows; i++) {
				Categoria cat = new Categoria();
				Row row = sheet.getRow(i);
				
				Cell cell = row.getCell(1);
				cat.setDescripcion(cell.getStringCellValue());
								
				cat_controller.newCategory(cat);		
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
