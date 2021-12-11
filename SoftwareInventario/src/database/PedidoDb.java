package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;

import models.Pedido;
import models.PedidoDetalle;
import models.Producto;

public class PedidoDb {
	public LinkedList<Pedido> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Pedido> pedidos = new LinkedList<>();
		String query;
		try {
			query = "SELECT nro_pedido, fecha FROM pedido";
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(query);
			if (rs != null) {
				while (rs.next()) {
					Pedido p = new Pedido();
					p.setNro_pedido(rs.getInt("nro_pedido"));
					p.setFecha(rs.getObject("fecha", LocalDate.class));
					
					setPedidoDetalle(p);
					
					System.out.println(p.getDetalle());
					pedidos.add(p);
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
		return pedidos;
	}
	
	public Pedido getById(Pedido ped) {
		Pedido p = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query;
		try {
			query = "SELECT nro_pedido, fecha FROM pedido WHERE nro_pedido=?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, ped.getNro_pedido());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				p = new Pedido();
				p.setNro_pedido(rs.getInt("nro_pedido"));
				p.setFecha(rs.getObject("fecha", LocalDate.class));
				
				setPedidoDetalle(p);
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

	public void create(Pedido p) {
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		String query;
		try {
			query = "INSERT INTO pedido(fecha) VALUES(?)";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query,
					PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setObject(1, p.getFecha());
			stmt.executeUpdate();

			keyResultSet = stmt.getGeneratedKeys();
			if (keyResultSet != null && keyResultSet.next()) {
				p.setNro_pedido(keyResultSet.getInt(1));
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

	public void addProduct(PedidoDetalle pd) {
		PreparedStatement stmt = null;
		String query;
		try {
			query = "INSERT INTO pedido_detalle(nro_pedido, id_producto, cantidad, importe) VALUES(?, ?, ?, ?)";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, pd.getPedido().getNro_pedido());
			stmt.setInt(2, pd.getProducto().getId());
			stmt.setInt(3, pd.getCantidad());
			stmt.setDouble(4, pd.getImporte());
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

	public void setPedidoDetalle(Pedido ped) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query;
		try {
			query = "SELECT p.descripcion, p.precio, pd.cantidad, pd.importe," + " pd.id_producto, pd.nro_pedido"
					+ " FROM producto AS p INNER JOIN pedido_detalle AS pd"
					+ " ON p.id_producto = pd.id_producto WHERE pd.nro_pedido=?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, ped.getNro_pedido());
			rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Producto p = new Producto();
					PedidoDetalle pd = new PedidoDetalle();
					p.setId(rs.getInt("id_producto"));
					p.setDescrip(rs.getString("descripcion"));
					p.setPrecio(rs.getDouble("precio"));
					pd.setProducto(p);
					pd.setCantidad(rs.getInt("cantidad"));
					pd.setImporte(rs.getDouble("importe"));
					
					ped.addDetalle(pd);
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
	}

}
