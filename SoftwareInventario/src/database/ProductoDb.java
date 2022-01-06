package database;

import java.sql.*;
import java.util.LinkedList;

import models.Categoria;
import models.Producto;
import models.Proveedor;

public class ProductoDb {

	public LinkedList<Producto> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		ProveedorDb db_prov = new ProveedorDb();
		CategoriaDb db_cat = new CategoriaDb();
		Proveedor prov = new Proveedor();
		Categoria cat = new Categoria();
		LinkedList<Producto> prods = new LinkedList<>();
		String query;
		try {
			query = "SELECT id_producto, descripcion, marca, precio, stock, id_proveedor, id_categoria FROM producto";
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(query);
			if (rs != null) {
				while (rs.next()) {
					Producto p = new Producto();
					p.setId(rs.getInt("id_producto"));
					p.setDescrip(rs.getString("descripcion"));
					p.setMarca(rs.getString("marca"));
					p.setPrecio(rs.getDouble("precio"));
					p.setStock(rs.getInt("stock"));
					prov.setId(rs.getInt("id_proveedor"));
					p.setProveedor(prov);
					cat.setId(rs.getInt("id_categoria"));
					p.setCategoria(cat);

					db_prov.setProveedor(p);
					db_cat.setCategoria(p);

					prods.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return prods;
	}

	public Producto getById(Producto prod) {
		Producto p = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query;
		try {
			query = "SELECT id_producto, descripcion, stock, marca, precio FROM producto WHERE id_producto=?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, prod.getId());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				p = new Producto();
				p.setId(rs.getInt("id_producto"));
				p.setDescrip(rs.getString("descripcion"));
				p.setMarca(rs.getString("marca"));
				p.setPrecio(rs.getDouble("precio"));
				p.setStock(rs.getInt("stock"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return p;
	}

	public void create(Producto p) {
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		String query;
		try {
			query = "INSERT INTO producto(descripcion, stock, marca, precio, id_proveedor, id_categoria) VALUES(?, ?, ?, ?, ?, ?)";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query,
					PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, p.getDescrip());
			stmt.setInt(2, p.getStock());
			stmt.setString(3, p.getMarca());
			stmt.setDouble(4, p.getPrecio());
			stmt.setInt(5, p.getProveedor().getId());
			stmt.setInt(6, p.getCategoria().getId());

			stmt.executeUpdate();

			keyResultSet = stmt.getGeneratedKeys();
			if (keyResultSet != null && keyResultSet.next()) {
				p.setId(keyResultSet.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (keyResultSet != null)
					keyResultSet.close();
				if (stmt != null)
					stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(Producto p) {
		PreparedStatement stmt = null;
		String query;
		try {
			query = "DELETE FROM producto WHERE id_producto = ?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, p.getId());
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(Producto p) {
		PreparedStatement stmt = null;
		String query;
		try {
			query = "UPDATE producto SET descripcion=?, stock=?, marca=?, precio=?, id_proveedor=?, id_categoria=? WHERE id_producto=?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setString(1, p.getDescrip());
			stmt.setInt(2, p.getStock());
			stmt.setString(3, p.getMarca());
			stmt.setDouble(4, p.getPrecio());
			stmt.setInt(5, p.getProveedor().getId());
			stmt.setInt(6, p.getCategoria().getId());
			stmt.setInt(7, p.getId());
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
