package softwareinventario.softwareinventario.service;

import java.util.List;

import softwareinventario.softwareinventario.models.Proveedor;

public interface IProveedorService {

    public List<Proveedor> findAll();

    public void save(Proveedor proveedor);

    public Proveedor findById(Proveedor proveedor);

    public void delete(Proveedor proveedor);
}
