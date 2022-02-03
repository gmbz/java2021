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

import controller.ProductoController;
import controller.ProveedorController;
import models.Categoria;
import models.Producto;
import models.Proveedor;

/**
 * Servlet implementation class ProveedorServlet
 */
@WebServlet("/proveedorServlet")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 *100 // 100 MB
	)
public class ProveedorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "assets";

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
			
			String titulo = "Listado de proveedores";
			request.setAttribute("titulo", titulo);
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
			
			String titulo = "Editar proveedor";
			request.setAttribute("titulo", titulo);
			request.setAttribute("proveedor", p);
			RequestDispatcher rd = request.getRequestDispatcher("update_proveedor.jsp");
			rd.forward(request, response);
			
		} else if (opcion.equals("generarExcel")) {
			response.setContentType("application/octet-stream");
			
			Workbook workbook = new XSSFWorkbook();

			response.setHeader("Content-Disposition", "attachment; filename=listado-proveedores.xlsx");

			Sheet sheet = workbook.createSheet("Proveedores");

			Row encabezado = sheet.createRow(0);
			String[] columnas = { "ID", "DESCRIPCION", "APELLIDO", "TELEFONO" };

			for (int i = 0; i < columnas.length; i++) {
				Cell celda = encabezado.createCell(i);
				celda.setCellValue(columnas[i]);
			}
			
			LinkedList<Proveedor> proveedores = new LinkedList<>();
			proveedores = prov_controller.listarProveedores();
			int i = 1;
			for (Proveedor proveedor : proveedores) {
				Row row = sheet.createRow(i);
				
				Cell celda = row.createCell(0);
				celda.setCellValue(proveedor.getId());
				
				celda = row.createCell(1);
				celda.setCellValue(proveedor.getNombre());
				
				celda = row.createCell(2);
				celda.setCellValue(proveedor.getApellido());
				
				celda = row.createCell(3);
				celda.setCellValue(proveedor.getTel());
				
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
			ProveedorController prov_controller = new ProveedorController();
			
			int numRows = sheet.getLastRowNum();
			
			for (int i = 1; i <= numRows; i++) {
				Proveedor prov = new Proveedor();
				Row row = sheet.getRow(i);
				
				Cell cell = row.getCell(1);
				prov.setNombre(cell.getStringCellValue());
				
				cell = row.getCell(2);				
				prov.setApellido(cell.getStringCellValue());
				
				cell = row.getCell(3);
				prov.setTel(cell.getStringCellValue());
				
				prov_controller.newProveedor(prov);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
