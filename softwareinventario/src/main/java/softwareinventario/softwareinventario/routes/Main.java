package softwareinventario.softwareinventario.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import softwareinventario.softwareinventario.controller.ProductoController;
import softwareinventario.softwareinventario.models.Producto;
import softwareinventario.softwareinventario.service.IProductoService;

@Controller
public class Main {

    @Autowired
    private IProductoService productoService;

    @GetMapping(value = "/")
    public String home(Model model) {
        ProductoController prod_controller = new ProductoController();
        Producto producto_mas_vendido = prod_controller.obtenerElMasVendido(productoService);

        model.addAttribute("producto_mas_vendido", producto_mas_vendido);
        model.addAttribute("titulo", "Inicio");
        return "index";
    }
}
