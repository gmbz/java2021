package softwareinventario.softwareinventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softwareinventario.softwareinventario.models.Cliente;
import softwareinventario.softwareinventario.models.Pedido;
import softwareinventario.softwareinventario.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements IPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Pedido> findAll() {
        return (List<Pedido>) pedidoRepository.findAll();
    }

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);

    }

    @Override
    public Pedido findById(Pedido pedido) {
        return pedidoRepository.findById(pedido.getNro_pedido()).orElse(null);
    }

    @Override
    public void delete(Pedido pedido) {
        pedidoRepository.deleteById(pedido.getNro_pedido());
    }

    @Override
    public List<Pedido> findAllByCliente(Cliente cliente) throws Exception {
        try {
            return pedidoRepository.findAllByCliente(cliente.getId_cliente());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        
    }

    @Override
    public List<Pedido> findByFechaEntrega(Pedido pedido) {
        return pedidoRepository.findAllByFechaEntrega(pedido.getFecha_entrega());
    }

}
