package softwareinventario.softwareinventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softwareinventario.softwareinventario.models.Categoria;
import softwareinventario.softwareinventario.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return (List<Categoria>) categoriaRepository.findAll();
    }

    @Override
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    @Override
    public Categoria findById(Categoria categoria) {
        return categoriaRepository.findById(categoria.getId()).orElse(null);
    }

    @Override
    public void delete(Categoria categoria) {
        categoriaRepository.deleteById(categoria.getId());
    }

}
