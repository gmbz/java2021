package softwareinventario.softwareinventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softwareinventario.softwareinventario.models.Producto;
import softwareinventario.softwareinventario.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) productoRepository.findAll();
    }

    @Override
    public void save(Producto producto) {
        productoRepository.save(producto);

    }

    @Override
    public Producto findById(Producto producto) {
        return productoRepository.findById(producto.getId()).orElse(null);
    }

    @Override
    public void delete(Producto producto) {
        productoRepository.deleteById(producto.getId());
    }

    @Override
    public List<Producto> findTopSellers() {
        return productoRepository.findTopSellers();
    }

}
