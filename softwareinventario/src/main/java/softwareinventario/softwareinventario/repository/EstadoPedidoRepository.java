package softwareinventario.softwareinventario.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import softwareinventario.softwareinventario.models.EstadoPedido;

@Repository
public interface EstadoPedidoRepository extends CrudRepository<EstadoPedido, Integer> {

}
