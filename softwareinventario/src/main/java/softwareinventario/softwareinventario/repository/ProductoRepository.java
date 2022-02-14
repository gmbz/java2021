package softwareinventario.softwareinventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import softwareinventario.softwareinventario.models.Producto;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Integer> {

    @Query(value = "SELECT * FROM productos ORDER BY total_vendidos desc", nativeQuery = true)
    public List<Producto> findTopSellers();

}
