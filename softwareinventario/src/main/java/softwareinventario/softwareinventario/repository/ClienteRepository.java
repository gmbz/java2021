package softwareinventario.softwareinventario.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import softwareinventario.softwareinventario.models.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
    
}
