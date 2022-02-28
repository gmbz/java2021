package softwareinventario.softwareinventario.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import softwareinventario.softwareinventario.models.Pedido;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Integer> {

    @Query(value = "SELECT * FROM pedidos p WHERE p.id_cliente=:idCliente", nativeQuery = true)
    public List<Pedido> findAllByCliente(@Param("idCliente") int id);

    @Query(value = "SELECT * FROM pedidos p WHERE p.fecha_entrega=:fechaEntrega", nativeQuery = true)
    public List<Pedido> findAllByFechaEntrega(@Param("fechaEntrega") LocalDate fecha_entrega);

}
