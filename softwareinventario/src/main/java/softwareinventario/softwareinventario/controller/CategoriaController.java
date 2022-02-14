package softwareinventario.softwareinventario.controller;

import java.util.List;

import softwareinventario.softwareinventario.models.Categoria;
import softwareinventario.softwareinventario.service.ICategoriaService;

public class CategoriaController {

    public List<Categoria> listarCategorias(ICategoriaService categoriaService) {
        return categoriaService.findAll();
    }

    public Categoria getById(Categoria cat, ICategoriaService categoriaService) {
        return categoriaService.findById(cat);
    }

    public void newCategory(Categoria cat, ICategoriaService categoriaService) {
        categoriaService.save(cat);
    }

    public void delete(Categoria cat, ICategoriaService categoriaService) {
        categoriaService.delete(cat);
    }

    public void update(Categoria cat, ICategoriaService categoriaService) {
        categoriaService.save(cat);
    }
}
