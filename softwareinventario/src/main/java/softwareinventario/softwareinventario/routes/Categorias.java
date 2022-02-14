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
    public String createCategoria(@ModelAttribute Categoria categoria) {
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
}
