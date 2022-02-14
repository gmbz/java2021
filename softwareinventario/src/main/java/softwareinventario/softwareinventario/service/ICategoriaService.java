package softwareinventario.softwareinventario.service;

import java.util.List;

import softwareinventario.softwareinventario.models.Categoria;

public interface ICategoriaService {

    public List<Categoria> findAll();

    public void save(Categoria categoria);

    public Categoria findById(Categoria categoria);

    public void delete(Categoria categoria);

}
