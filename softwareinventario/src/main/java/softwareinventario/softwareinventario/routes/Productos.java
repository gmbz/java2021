package softwareinventario.softwareinventario.routes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        ProveedorController prov_controller = new ProveedorController();
        CategoriaController cat_controller = new CategoriaController();
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
    public String createProducto(@ModelAttribute Producto producto) {
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
}
