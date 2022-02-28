package softwareinventario.softwareinventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softwareinventario.softwareinventario.models.EstadoPedido;
import softwareinventario.softwareinventario.repository.EstadoPedidoRepository;

@Service
public class EstadoPedidoServiceImpl implements IEstadoPedidoService {

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Override
    public List<EstadoPedido> findAll() {
        return (List<EstadoPedido>) estadoPedidoRepository.findAll();
    }

    @Override
    public void save(EstadoPedido estado) {
        estadoPedidoRepository.save(estado);

    }

    @Override
    public EstadoPedido findById(EstadoPedido estado) {
        return estadoPedidoRepository.findById(estado.getId_estado()).orElse(null);
    }

    @Override
    public void delete(EstadoPedido estado) {
        estadoPedidoRepository.deleteById(estado.getId_estado());

    }

}
