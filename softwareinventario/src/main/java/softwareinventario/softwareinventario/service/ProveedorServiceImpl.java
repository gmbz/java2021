package softwareinventario.softwareinventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softwareinventario.softwareinventario.models.Proveedor;
import softwareinventario.softwareinventario.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements IProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> findAll() {
        return (List<Proveedor>) proveedorRepository.findAll();
    }

    @Override
    public void save(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor findById(Proveedor proveedor) {
        return proveedorRepository.findById(proveedor.getId()).orElse(null);
    }

    @Override
    public void delete(Proveedor proveedor) {
        proveedorRepository.deleteById(proveedor.getId());

    }

}
