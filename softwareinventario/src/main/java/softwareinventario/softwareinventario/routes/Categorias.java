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

import softwareinventario.softwareinventario.controller.CategoriaController;
import softwareinventario.softwareinventario.models.Categoria;
import softwareinventario.softwareinventario.service.ICategoriaService;

@Controller
@RequestMapping("/categorias")
public class Categorias {
    CategoriaController cat_controller = new CategoriaController();

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping(value = "/")
    public String listarCategorias(Model model) {
        List<Categoria> categorias = cat_controller.listarCategorias(categoriaService);
        model.addAttribute("categorias", categorias);
        model.addAttribute("titulo", "Listado de categorias");
        return "show_categorias";

    }

    @GetMapping(value = "/nueva_categoria")
    public String nuevaCategoria(Model model) {
        Categoria categoria = new Categoria();
        model.addAttribute("categoria", categoria);
        model.addAttribute("titulo", "Nueva categoria");
        return "new_categoria";
    }

    @PostMapping(value = "/create")
    public String createCategoria(@Valid @ModelAttribute Categoria categoria, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categoria", categoria);
            model.addAttribute("titulo", "Nueva categoria");
            return "new_categoria";
        }

        cat_controller.newCategory(categoria, categoriaService);
        return "redirect:/";
    }

    @GetMapping(value = "/delete/{id}")
    public String borrarCategoria(@PathVariable("id") int id_categoria, Model model) {
        Categoria categoria = new Categoria();
        categoria.setId(id_categoria);
        cat_controller.delete(categoria, categoriaService);
        return "redirect:/categorias/";
    }

    @GetMapping(value = "/update/{id}")
    public String editarCategoria(@PathVariable("id") int id_categoria, Model model) {
        Categoria categoria = new Categoria();
        Categoria categoriaActualizar = new Categoria();

        categoria.setId(id_categoria);
        categoriaActualizar = cat_controller.getById(categoria, categoriaService);

        model.addAttribute("categoria", categoriaActualizar);
        model.addAttribute("titulo", "Editar categoria");
        return "update_categoria";
    }

    @PostMapping(value = "/update")
    public String actualizar(@ModelAttribute Categoria categoria) {
        cat_controller.update(categoria, categoriaService);
        return "redirect:/categorias/";
    }

    @GetMapping("/excel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        Workbook workbook = new XSSFWorkbook();

        response.setHeader("Content-Disposition", "attachment; filename=listado-proveedores.xlsx");

        Sheet sheet = workbook.createSheet("Proveedores");

        Row encabezado = sheet.createRow(0);
        String[] columnas = { "ID", "DESCRIPCION" };

        for (int i = 0; i < columnas.length; i++) {
            Cell celda = encabezado.createCell(i);
            celda.setCellValue(columnas[i]);
        }

        List<Categoria> categorias = cat_controller.listarCategorias(categoriaService);

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

    @PostMapping(value = "/leer_excel")
    public String postMethodName(@RequestParam("inputExcel") Part filePart, HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        cat_controller.readDataFromExcel(filePart, categoriaService, request, response);
        return "redirect:/proveedores/";
    }
}
