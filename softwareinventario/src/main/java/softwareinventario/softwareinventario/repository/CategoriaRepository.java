package softwareinventario.softwareinventario.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import softwareinventario.softwareinventario.models.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {
    
}
