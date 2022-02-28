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
import softwareinventario.softwareinventario.controller.ProductoController;
import softwareinventario.softwareinventario.controller.ProveedorController;
import softwareinventario.softwareinventario.models.Categoria;
import softwareinventario.softwareinventario.models.Producto;
import softwareinventario.softwareinventario.models.Proveedor;
import softwareinventario.softwareinventario.service.ICategoriaService;
import softwareinventario.softwareinventario.service.IProductoService;
import softwareinventario.softwareinventario.service.IProveedorService;

@Controller
@RequestMapping("/productos")
public class Productos {
    ProductoController prod_controller = new ProductoController();
    ProveedorController prov_controller = new ProveedorController();
    CategoriaController cat_controller = new CategoriaController();

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IProveedorService proveedorService;

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping(value = "/")
    public String listarProductos(Model model) {
        List<Producto> productos = prod_controller.listaProductos(productoService);
        model.addAttribute("productos", productos);
        return "show_productos";
    }

    @GetMapping(value = "/nuevo_producto")
    public String nuevoProducto(Model model) {
        Producto producto = new Producto();
        List<Proveedor> proveedores = prov_controller.listarProveedores(proveedorService);
        List<Categoria> categorias = cat_controller.listarCategorias(categoriaService);
        model.addAttribute("categorias", categorias);
        model.addAttribute("proveedores", proveedores);
        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Nuevo producto");
        return "new_producto";
    }

    @PostMapping(value = "/create")
    public String createProducto(@Valid @ModelAttribute Producto producto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<Proveedor> proveedores = prov_controller.listarProveedores(proveedorService);
            List<Categoria> categorias = cat_controller.listarCategorias(categoriaService);
            model.addAttribute("categorias", categorias);
            model.addAttribute("proveedores", proveedores);
            model.addAttribute("producto", producto);
            model.addAttribute("titulo", "Nuevo producto");
            return "new_producto";
        }

        prod_controller.newProduct(producto, productoService);
        return "redirect:/";
    }

    @GetMapping(value = "/delete/{id}")
    public String borrarProducto(@PathVariable("id") int id_producto, Model model) {
        Producto producto = new Producto();
        producto.setId(id_producto);
        prod_controller.delete(producto, productoService);
        return "redirect:/productos/";
    }

    @GetMapping(value = "/update/{id}")
    public String editarProducto(@PathVariable("id") int id_categoria, Model model) {
        ProveedorController prov_controller = new ProveedorController();
        CategoriaController cat_controller = new CategoriaController();
        List<Proveedor> proveedores = prov_controller.listarProveedores(proveedorService);
        List<Categoria> categorias = cat_controller.listarCategorias(categoriaService);
        Producto producto = new Producto();

        producto.setId(id_categoria);
        Producto productoActualizar = prod_controller.getById(producto, productoService);

        model.addAttribute("categorias", categorias);
        model.addAttribute("proveedores", proveedores);
        model.addAttribute("producto", productoActualizar);
        model.addAttribute("titulo", "Editar producto");
        return "update_producto";
    }

    @PostMapping(value = "/update")
    public String actualizar(@ModelAttribute Producto producto) {
        prod_controller.update(producto, productoService);
        return "redirect:/productos/";
    }

    @GetMapping("/mas_vendidos")
    public String masVendidos(Model model) {
        List<Producto> productos = prod_controller.listaMasVendidos(productoService);
        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Ranking de productos");
        return "mas_vendidos";
    }

    @GetMapping("new_entry")
    public String nuevoIngreso(Model model) {
        List<Producto> productos = productoService.findAll();

        model.addAttribute("titulo", "Nuevo ingreso");
        model.addAttribute("productos", productos);

        return "new_entry_product";
    }

    @PostMapping(value = "new_entry_save")
    public String postMethodName(@RequestParam("idProducto") int id_producto, @RequestParam("inputStock") int stock,
            Model model) {
        Producto producto = new Producto();
        producto.setId(id_producto);
        producto.setStock(stock);
        prod_controller.newEntry(producto, productoService);

        List<Producto> productos = productoService.findAll();

        model.addAttribute("titulo", "Nuevo ingreso");
        model.addAttribute("productos", productos);

        String msg = "Nuevo ingreso realizado.";
        model.addAttribute("success", msg);
        return "new_entry_product";
    }

    @GetMapping("/excel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        Workbook workbook = new XSSFWorkbook();

        response.setHeader("Content-Disposition", "attachment; filename=listado-proveedores.xlsx");

        Sheet sheet = workbook.createSheet("Proveedores");

        Row encabezado = sheet.createRow(0);
        String[] columnas = { "ID", "DESCRIPCION", "STOCK", "PRECIO", "TOTAL VENDIDOS", "ID CATEGORIA",
                "ID_PROVEEDOR" };

        for (int i = 0; i < columnas.length; i++) {
            Cell celda = encabezado.createCell(i);
            celda.setCellValue(columnas[i]);
        }

        List<Producto> productos = prod_controller.listaProductos(productoService);

        int i = 1;
        for (Producto producto : productos) {
            Row row = sheet.createRow(i);

            Cell celda = row.createCell(0);
            celda.setCellValue(producto.getId());

            celda = row.createCell(1);
            celda.setCellValue(producto.getDescrip());

            celda = row.createCell(2);
            celda.setCellValue(producto.getStock());

            celda = row.createCell(3);
            celda.setCellValue(producto.getPrecio());

            celda = row.createCell(4);
            celda.setCellValue(producto.getTotalVendidos());

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

    @PostMapping(value = "/leer_excel")
    public String postMethodName(@RequestParam("inputExcel") Part filePart, HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        prod_controller.readDataFromExcel(filePart, productoService, request, response);
        return "redirect:/proveedores/";
    }
}
