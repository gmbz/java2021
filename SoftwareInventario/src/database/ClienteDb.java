package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import models.Cliente;

public class ClienteDb {

	public LinkedList<Cliente> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Cliente> clientes = new LinkedList<>();
		String query;
		try {
			query = "SELECT id_cliente, nombre, apellido, email, telefono, direccion FROM cliente";
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(query);
			if (rs != null) {
				while (rs.next()) {
					Cliente c = new Cliente();
					c.setId_cliente(rs.getInt("id_cliente"));
					c.setNombre(rs.getString("nombre"));
					c.setApellido(rs.getString("apellido"));
					c.setEmail(rs.getString("email"));
					c.setTel(rs.getString("telefono"));
					c.setDireccion(rs.getString("direccion"));

					clientes.add(c);
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
		return clientes;
	}

	public Cliente getById(Cliente cli) {
		Cliente c = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query;
		try {
			query = "SELECT id_cliente, nombre, apellido, email, telefono, direccion FROM cliente WHERE id_cliente=?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, cli.getId_cliente());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				c = new Cliente();
				c.setId_cliente(rs.getInt("id_cliente"));
				c.setNombre(rs.getString("nombre"));
				c.setApellido(rs.getString("apellido"));
				c.setEmail(rs.getString("email"));
				c.setTel(rs.getString("telefono"));
				c.setDireccion(rs.getString("direccion"));
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
		return c;
	}

	public void create(Cliente c) {
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		String query;
		try {
			query = "INSERT INTO cliente(nombre, apellido, email, telefono, direccion) VALUES(?, ?, ?, ?, ?)";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query,
					PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, c.getNombre());
			stmt.setString(2, c.getApellido());
			stmt.setString(3, c.getEmail());
			stmt.setString(4, c.getTel());
			stmt.setString(5, c.getDireccion());
			stmt.executeUpdate();

			keyResultSet = stmt.getGeneratedKeys();
			if (keyResultSet != null && keyResultSet.next()) {
				c.setId_cliente(keyResultSet.getInt(1));
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

	public void delete(Cliente c) {
		PreparedStatement stmt = null;
		String query;
		try {
			query = "DELETE FROM cliente WHERE id_cliente = ?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, c.getId_cliente());
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

	public void update(Cliente c) {
		PreparedStatement stmt = null;
		String query;
		try {
			query = "UPDATE cliente SET nombre=?, apellido=?, email=?, telefono=?, direccion=? WHERE id_cliente=?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setString(1, c.getNombre());
			stmt.setString(2, c.getApellido());
			stmt.setString(3, c.getEmail());
			stmt.setString(4, c.getTel());
			stmt.setString(5, c.getDireccion());
			stmt.setInt(6, c.getId_cliente());
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
