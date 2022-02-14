package softwareinventario.softwareinventario.service;

import java.util.List;

import softwareinventario.softwareinventario.models.Cliente;
import softwareinventario.softwareinventario.models.Pedido;

public interface IPedidoService {

    public List<Pedido> findAll();

    public List<Pedido> findAllByCliente(Cliente cliente) throws Exception;

    public Pedido save(Pedido pedido);

    public Pedido findById(Pedido pedido);

    public void delete(Pedido pedido);

}
