package softwareinventario.softwareinventario.service;

import java.util.List;

import softwareinventario.softwareinventario.models.Producto;

public interface IProductoService {

    public List<Producto> findAll();

    public void save(Producto producto);

    public Producto findById(Producto producto);

    public void delete(Producto producto);
    
    public List<Producto> findTopSellers();
}
