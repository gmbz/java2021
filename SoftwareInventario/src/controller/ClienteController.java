package controller;

import java.util.LinkedList;

import database.ClienteDb;
import models.Cliente;

public class ClienteController {
	ClienteDb db_cli = new ClienteDb();
	
	public LinkedList<Cliente> listarClientes() {
		return db_cli.getAll();
	}
	
	public Cliente getById(Cliente cli) {
		return db_cli.getById(cli);
	}
	
	public void newCliente(Cliente cli) {
		db_cli.create(cli);
	}
	
	public void delete(Cliente cli) {
		db_cli.delete(cli);
	}
	
	public void update(Cliente cli) {
		db_cli.update(cli);
	}
}
