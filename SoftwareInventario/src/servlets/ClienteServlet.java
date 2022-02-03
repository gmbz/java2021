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

import controller.ClienteController;
import models.Cliente;

/**
 * Servlet implementation class ClienteServlet
 */
@WebServlet("/clienteServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "assets";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClienteServlet() {
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
		ClienteController cli_controller = new ClienteController();
		if (opcion.equals("listar")) {
			LinkedList<Cliente> lista = new LinkedList<>();

			lista = cli_controller.listarClientes();
			
			String titulo = "Listado de clientes";
			request.setAttribute("titulo", titulo);
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_clientes.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("borrar")) {

			Cliente cli = new Cliente();
			cli.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			cli_controller.delete(cli);

			RequestDispatcher rd = request.getRequestDispatcher("clienteServlet?opcion=listar");
			rd.forward(request, response);

		} else if (opcion.equals("buscar")) {
			Cliente cli = new Cliente();
			Cliente c = new Cliente();

			cli.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			c = cli_controller.getById(cli);
			
			String titulo = "Editar cliente";
			request.setAttribute("titulo", titulo);
			request.setAttribute("cliente", c);
			RequestDispatcher rd = request.getRequestDispatcher("update_cliente.jsp");
			rd.forward(request, response);
			
		} else if (opcion.equals("generarExcel")) {
			response.setContentType("application/octet-stream");

			Workbook workbook = new XSSFWorkbook();

			response.setHeader("Content-Disposition", "attachment; filename=listado-clientes.xlsx");

			Sheet sheet = workbook.createSheet("Productos");

			Row encabezado = sheet.createRow(0);
			String[] columnas = { "ID", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "EMAIL"};

			for (int i = 0; i < columnas.length; i++) {
				Cell celda = encabezado.createCell(i);
				celda.setCellValue(columnas[i]);
			}

			LinkedList<Cliente> clientes = new LinkedList<>();
			clientes = cli_controller.listarClientes();
			
			int i = 1;
			for (Cliente cliente : clientes) {
				Row row = sheet.createRow(i);

				Cell celda = row.createCell(0);
				celda.setCellValue(cliente.getId_cliente());

				celda = row.createCell(1);
				celda.setCellValue(cliente.getNombre());

				celda = row.createCell(2);
				celda.setCellValue(cliente.getApellido());

				celda = row.createCell(3);
				celda.setCellValue(cliente.getDireccion());

				celda = row.createCell(4);
				celda.setCellValue(cliente.getTel());

				celda = row.createCell(5);
				celda.setCellValue(cliente.getEmail());

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
		ClienteController cli_controller = new ClienteController();
		Cliente cli = new Cliente();
		
		if (opcion.equals("create")) {
			
			cli.setNombre(request.getParameter("nombreCliente"));
			cli.setApellido(request.getParameter("apellidoCliente"));
			cli.setDireccion(request.getParameter("direcCliente"));
			cli.setEmail(request.getParameter("emailCliente"));
			cli.setTel(request.getParameter("telCliente"));
			
			cli_controller.newCliente(cli);
			
			RequestDispatcher rd = request.getRequestDispatcher("clienteServlet?opcion=listar");
			rd.forward(request, response);
			
		} else if (opcion.equals("editar")) {
			
			cli.setId_cliente(Integer.parseInt(request.getParameter("idCliente")));
			cli.setNombre(request.getParameter("nombreCliente"));
			cli.setApellido(request.getParameter("apellidoCliente"));
			cli.setDireccion(request.getParameter("direcCliente"));
			cli.setEmail(request.getParameter("emailCliente"));
			cli.setTel(request.getParameter("telCliente"));
			
			cli_controller.update(cli);
			
			RequestDispatcher rd = request.getRequestDispatcher("clienteServlet?opcion=listar");
			rd.forward(request, response);
			
		} else if (opcion.equals("listar")) {
			LinkedList<Cliente> lista = new LinkedList<>();
			
			lista = cli_controller.listarClientes();
			
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("show_clientes.jsp");
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
			ClienteController cli_controller = new ClienteController();
			
			int numRows = sheet.getLastRowNum();
			
			for (int i = 1; i <= numRows; i++) {
				Cliente cli = new Cliente();
				Row row = sheet.getRow(i);
				
				Cell cell = row.getCell(1);
				cli.setNombre(cell.getStringCellValue());
				
				cell = row.getCell(2);
				cli.setApellido(cell.getStringCellValue());
				
				cell = row.getCell(3);
				cli.setDireccion(cell.getStringCellValue());
				
				cell = row.getCell(4);
				cli.setTel(cell.getStringCellValue());
				
				cell = row.getCell(5);
				cli.setEmail(cell.getStringCellValue());
				
				cli_controller.newCliente(cli);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
