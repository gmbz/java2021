package softwareinventario.softwareinventario.service;

import java.util.List;

import softwareinventario.softwareinventario.models.Cliente;

public interface IClienteService {

    public List<Cliente> findAll();

    public void save(Cliente cliente);

    public Cliente findById(Cliente cliente);

    public void delete(Cliente cliente);

}
