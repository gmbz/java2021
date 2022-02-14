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
    public String createProveedor(@ModelAttribute Proveedor proveedor) {
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
}
