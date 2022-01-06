package controller;

import java.util.LinkedList;

import database.ProveedorDb;
import models.Proveedor;

public class ProveedorController {
	ProveedorDb db_prov = new ProveedorDb();

	public LinkedList<Proveedor> listarProveedores() {
		return db_prov.getAll();
	}

	public Proveedor getById(Proveedor prov) {
		return db_prov.getById(prov);
	}

	public void newProveedor(Proveedor prov) {
		db_prov.create(prov);
	}

	public void delete(Proveedor prov) {
		db_prov.delete(prov);
	}

	public void update(Proveedor prov) {
		db_prov.update(prov);
	}
}
