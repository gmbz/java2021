package softwareinventario.softwareinventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softwareinventario.softwareinventario.models.Cliente;
import softwareinventario.softwareinventario.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    public void save(Cliente cliente) {
        clienteRepository.save(cliente);

    }

    @Override
    public Cliente findById(Cliente cliente) {
        return clienteRepository.findById(cliente.getId_cliente()).orElse(null);
    }

    @Override
    public void delete(Cliente cliente) {
        clienteRepository.deleteById(cliente.getId_cliente());
    }

}
