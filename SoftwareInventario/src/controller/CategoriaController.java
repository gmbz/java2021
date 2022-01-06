package controller;

import java.util.LinkedList;

import database.CategoriaDb;
import models.Categoria;

public class CategoriaController {
	CategoriaDb db_cat = new CategoriaDb();

	public LinkedList<Categoria> listarCategorias() {
		return db_cat.getAll();
	}

	public Categoria getById(Categoria cat) {
		return db_cat.getById(cat);
	}

	public void newCategory(Categoria cat) {
		db_cat.create(cat);
	}

	public void delete(Categoria cat) {
		db_cat.delete(cat);
	}

	public void update(Categoria cat) {
		db_cat.update(cat);
	}
}
