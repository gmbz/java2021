package softwareinventario.softwareinventario.routes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import softwareinventario.softwareinventario.controller.ClienteController;
import softwareinventario.softwareinventario.models.Cliente;
import softwareinventario.softwareinventario.service.IClienteService;

@Controller
@RequestMapping("/clientes")
public class Clientes {
    ClienteController cli_controller = new ClienteController();

    @Autowired
    private IClienteService clienteService;

    @GetMapping(value = "/")
    public String listarCategorias(Model model) {
        List<Cliente> clientes = cli_controller.listarClientes(clienteService);
        model.addAttribute("clientes", clientes);
        return "show_clientes";
    }

    @GetMapping(value = "/nuevo_cliente")
    public String nuevoCliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Nuevo cliente");
        return "new_cliente";
    }

    @PostMapping(value = "/create")
    public String createCliente(@ModelAttribute Cliente cliente) {
        cli_controller.newCliente(cliente, clienteService);
        return "redirect:/";
    }

    @GetMapping(value = "/delete/{id}")
    public String borrarCliente(@PathVariable("id") int id_cliente, Model model) {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(id_cliente);
        cli_controller.delete(cliente, clienteService);
        return "redirect:/clientes/";
    }

    @GetMapping(value = "/update/{id}")
    public String editarCliente(@PathVariable("id") int id_cliente, Model model) {
        Cliente cliente = new Cliente();
        Cliente clienteActualizar = new Cliente();
        cliente.setId_cliente(id_cliente);
        clienteActualizar = cli_controller.getById(cliente, clienteService);
        model.addAttribute("cliente", clienteActualizar);
        model.addAttribute("titulo", "Editar cliente");
        return "update_cliente";
    }

    @PostMapping(value = "/update")
    public String actualizar(@ModelAttribute Cliente cliente) {
        cli_controller.update(cliente, clienteService);
        return "redirect:/clientes/";
    }

    @GetMapping("/excel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        Workbook workbook = new XSSFWorkbook();

        response.setHeader("Content-Disposition", "attachment; filename=listado-clientes.xlsx");

        Sheet sheet = workbook.createSheet("Productos");

        Row encabezado = sheet.createRow(0);
        String[] columnas = { "ID", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "EMAIL" };

        for (int i = 0; i < columnas.length; i++) {
            Cell celda = encabezado.createCell(i);
            celda.setCellValue(columnas[i]);
        }

        List<Cliente> clientes = cli_controller.listarClientes(clienteService);

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

    @PostMapping(value = "/leer_excel")
    public String postMethodName(@RequestParam("inputExcel") Part filePart, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //Part filePart = request.getPart("inputExcel");
        cli_controller.readDataFromExcel(filePart, clienteService, request, response);
        return "redirect:/clientes/";
    }

}
