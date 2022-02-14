package softwareinventario.softwareinventario.controller;

import java.util.List;

import softwareinventario.softwareinventario.models.Proveedor;
import softwareinventario.softwareinventario.service.IProveedorService;

public class ProveedorController {

    public List<Proveedor> listarProveedores(IProveedorService proveedorService) {
        return proveedorService.findAll();
    }

    public Proveedor getById(Proveedor prov, IProveedorService proveedorService) {
        return proveedorService.findById(prov);
    }

    public void newProveedor(Proveedor prov, IProveedorService proveedorService) {
        proveedorService.save(prov);
    }

    public void delete(Proveedor prov, IProveedorService proveedorService) {
        proveedorService.delete(prov);
    }

    public void update(Proveedor prov, IProveedorService proveedorService) {
        proveedorService.save(prov);
    }

}