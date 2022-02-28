package softwareinventario.softwareinventario.routes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import softwareinventario.softwareinventario.controller.ProveedorController;
import softwareinventario.softwareinventario.models.Proveedor;
import softwareinventario.softwareinventario.service.IProveedorService;

@Controller
@RequestMapping("/proveedores")
public class Proveedores {
    ProveedorController prov_controller = new ProveedorController();

    @Autowired
    private IProveedorService proveedorService;

    @GetMapping("/")
    public String listarProveedores(Model model) {
        List<Proveedor> proveedores = prov_controller.listarProveedores(proveedorService);
        model.addAttribute("proveedores", proveedores);
        return "show_proveedores";
    }

    @GetMapping("/nuevo_proveedor")
    public String nuevoProveedor(Model model) {
        Proveedor proveedor = new Proveedor();
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("titulo", "Nuevo proveedor");
        return "new_proveedor";
    }

    @PostMapping("/create")
    public String createProveedor(@Valid @ModelAttribute Proveedor proveedor, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("proveedor", proveedor);
            model.addAttribute("titulo", "Nuevo proveedor");
            return "new_proveedor";
        }

        prov_controller.newProveedor(proveedor, proveedorService);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String borrarProveedor(@PathVariable("id") int id_proveedor, Model model) {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id_proveedor);
        prov_controller.delete(proveedor, proveedorService);
        return "redirect:/proveedores/";
    }

    @GetMapping("/update/{id}")
    public String editarProveedor(@PathVariable("id") int id_proveedor, Model model) {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id_proveedor);
        Proveedor proveedorActualizar = prov_controller.getById(proveedor, proveedorService);
        model.addAttribute("proveedor", proveedorActualizar);
        model.addAttribute("titulo", "Editar proveedor");
        return "update_proveedor";
    }

    @PostMapping("/update")
    public String actualizar(@ModelAttribute Proveedor proveedor) {
        prov_controller.update(proveedor, proveedorService);
        return "redirect:/proveedores/";
    }

    @GetMapping("/excel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        Workbook workbook = new XSSFWorkbook();

        response.setHeader("Content-Disposition", "attachment; filename=listado-proveedores.xlsx");

        Sheet sheet = workbook.createSheet("Proveedores");

        Row encabezado = sheet.createRow(0);
        String[] columnas = { "ID", "NOMBRE", "APELLIDO", "TELEFONO" };

        for (int i = 0; i < columnas.length; i++) {
            Cell celda = encabezado.createCell(i);
            celda.setCellValue(columnas[i]);
        }

        List<Proveedor> proveedores = prov_controller.listarProveedores(proveedorService);

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

    @PostMapping(value = "/leer_excel")
    public String postMethodName(@RequestParam("inputExcel") Part filePart, HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        prov_controller.readDataFromExcel(filePart, proveedorService, request, response);
        return "redirect:/proveedores/";
    }
}
