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
import models.Categoria;
import models.Producto;
import models.Proveedor;

/**
 * Servlet implementation class ProductoServlet
 */
@WebServlet("/productoServlet")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 *100 // 100 MB
	)
public class ProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "assets";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductoServlet() {
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
		ProductoController prod_controller = new ProductoController();

		if (opcion.equals("listar")) {
			LinkedList<Producto> lista = new LinkedList<>();

			lista = prod_controller.listaProductos();

			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_products.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("borrar")) {
			Producto prod = new Producto();

			prod.setId(Integer.parseInt(request.getParameter("idProd")));
			prod_controller.delete(prod);

			RequestDispatcher rd = request.getRequestDispatcher("productoServlet?opcion=listar");
			rd.forward(request, response);

		} else if (opcion.equals("buscar")) {

			Producto prod = new Producto();
			Producto p = new Producto();

			prod.setId(Integer.parseInt(request.getParameter("idProd")));
			p = prod_controller.getById(prod);

			request.setAttribute("producto", p);
			RequestDispatcher rd = request.getRequestDispatcher("update_product.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("generarExcel")) {
			response.setContentType("application/octet-stream");
			
			Workbook workbook = new XSSFWorkbook();

			response.setHeader("Content-Disposition", "attachment; filename=listado-productos.xlsx");

			Sheet sheet = workbook.createSheet("Productos");

			Row encabezado = sheet.createRow(0);
			String[] columnas = { "ID", "DESCRIPCION", "MARCA", "STOCK", "PRECIO", "CATEGORIA", "PROVEEDOR" };

			for (int i = 0; i < columnas.length; i++) {
				Cell celda = encabezado.createCell(i);
				celda.setCellValue(columnas[i]);
			}
			
			LinkedList<Producto> productos = new LinkedList<>();
			productos = prod_controller.listaProductos();
			int i = 1;
			for (Producto producto : productos) {
				Row row = sheet.createRow(i);
				
				Cell celda = row.createCell(0);
				celda.setCellValue(producto.getId());
				
				celda = row.createCell(1);
				celda.setCellValue(producto.getDescrip());
				
				celda = row.createCell(2);
				celda.setCellValue(producto.getMarca());
				
				celda = row.createCell(3);
				celda.setCellValue(producto.getStock());
				
				celda = row.createCell(4);
				celda.setCellValue(producto.getPrecio());
				
				celda = row.createCell(5);
				celda.setCellValue(producto.getCategoria().getId());
				
				celda = row.createCell(6);
				celda.setCellValue(producto.getProveedor().getId());
				
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
		ProductoController prod_controller = new ProductoController();
		Producto prod = new Producto();
		Proveedor prov = new Proveedor();
		Categoria cat = new Categoria();

		if (opcion.equals("create")) {
			prod.setDescrip(request.getParameter("descripProd"));
			prod.setStock(Integer.parseInt(request.getParameter("cantProd")));
			prod.setMarca(request.getParameter("marca"));
			prod.setPrecio(Double.parseDouble(request.getParameter("precio")));
			prov.setId(Integer.parseInt(request.getParameter("idProv")));
			prod.setProveedor(prov);
			cat.setId(Integer.parseInt(request.getParameter("idCat")));
			prod.setCategoria(cat);
			prod_controller.newProduct(prod);

			RequestDispatcher rd = request.getRequestDispatcher("productoServlet?opcion=listar");
			rd.forward(request, response);

		} else if (opcion.equals("editar")) {

			prod.setId(Integer.parseInt(request.getParameter("idProd")));
			prod.setDescrip(request.getParameter("descripProd"));
			prod.setStock(Integer.parseInt(request.getParameter("cantProd")));
			prod.setMarca(request.getParameter("marca"));
			prod.setPrecio(Double.parseDouble(request.getParameter("precio")));
			prov.setId(Integer.parseInt(request.getParameter("idProv")));
			prod.setProveedor(prov);
			cat.setId(Integer.parseInt(request.getParameter("idCat")));
			prod.setCategoria(cat);
			prod_controller.update(prod);

			RequestDispatcher rd = request.getRequestDispatcher("productoServlet?opcion=listar");
			rd.forward(request, response);

		} else if (opcion.equals("listar")) {

			LinkedList<Producto> lista = new LinkedList<>();

			lista = prod_controller.listaProductos();

			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_products.jsp");
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
			ProductoController prod_controller = new ProductoController();
			
			int numRows = sheet.getLastRowNum();
			
			for (int i = 1; i <= numRows; i++) {
				Producto prod = new Producto();
				Row row = sheet.getRow(i);
				
				Cell cell = row.getCell(1);
				prod.setDescrip(cell.getStringCellValue());
				
				cell = row.getCell(2);
				prod.setMarca(cell.getStringCellValue());
				
				cell = row.getCell(3);
				prod.setStock((int)cell.getNumericCellValue());
				
				cell = row.getCell(4);
				prod.setPrecio(cell.getNumericCellValue());
				
				Categoria cat = new Categoria();				
				cell = row.getCell(5);
				cat.setId((int)cell.getNumericCellValue());
				prod.setCategoria(cat);
				
				Proveedor prov = new Proveedor();
				cell = row.getCell(6);
				prov.setId((int)cell.getNumericCellValue());
				prod.setProveedor(prov);
				
				prod_controller.newProduct(prod);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
