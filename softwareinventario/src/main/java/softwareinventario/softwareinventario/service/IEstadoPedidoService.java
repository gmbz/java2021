package softwareinventario.softwareinventario.service;

import java.util.List;

import softwareinventario.softwareinventario.models.EstadoPedido;

public interface IEstadoPedidoService {

    public List<EstadoPedido> findAll();

    public void save(EstadoPedido estado);

    public EstadoPedido findById(EstadoPedido estado);

    public void delete(EstadoPedido estado);

}
