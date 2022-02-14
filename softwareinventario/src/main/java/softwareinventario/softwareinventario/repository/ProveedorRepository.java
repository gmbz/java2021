package softwareinventario.softwareinventario.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import softwareinventario.softwareinventario.models.Proveedor;

@Repository
public interface ProveedorRepository extends CrudRepository<Proveedor, Integer> {
    
}
