package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;

import models.Cliente;
import models.ItemPedido;
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
			query = "SELECT nro_pedido, fecha, id_cliente FROM pedido";
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(query);
			if (rs != null) {
				while (rs.next()) {
					Pedido p = new Pedido();
					PedidoDetalle pd = new PedidoDetalle();
					Cliente c = new Cliente();

					p.setNro_pedido(rs.getInt("nro_pedido"));
					p.setFecha(rs.getObject("fecha", LocalDate.class));
					c.setId_cliente(rs.getInt("id_cliente"));
					p.setCliente(c);

					pd = setDetalleProducto(p);
					p.setDetalle(pd);

					setCliente(p);

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

	public LinkedList<Pedido> getAllByCliente(Cliente cli) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Pedido> pedidos = new LinkedList<>();
		String query;
		try {
			query = "SELECT nro_pedido, fecha, id_cliente FROM pedido" + " WHERE id_cliente = ?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, cli.getId_cliente());
			rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Pedido p = new Pedido();
					PedidoDetalle pd = new PedidoDetalle();

					p.setNro_pedido(rs.getInt("nro_pedido"));
					p.setFecha(rs.getObject("fecha", LocalDate.class));
					p.setCliente(cli);

					pd = setDetalleProducto(p);
					p.setDetalle(pd);

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
	
	public PedidoDetalle getItemsPedidos(PedidoDetalle pd) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PedidoDetalle ped_det = new PedidoDetalle();
		String query;
		try {
			query = "SELECT p.id_producto, p.descripcion, dp.cantidad, dp.subtotal, dp.id_detalle"
					+ " FROM pedido_detalle AS pd"
					+ " INNER JOIN detalle_producto AS dp ON dp.id_detalle=pd.id_detalle"
					+ " INNER JOIN producto AS p ON p.id_producto=dp.id_producto"
					+ " WHERE pd.id_detalle = ?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, pd.getId_detalle());
			rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Producto prod = new Producto();
					ItemPedido ip = new ItemPedido();
					
					prod.setId(rs.getInt("p.id_producto"));
					prod.setDescrip(rs.getString("p.descripcion"));
					ip.setCantidad(rs.getInt("dp.cantidad"));
					ip.setSubtotal(rs.getDouble("dp.subtotal"));
					ped_det.setId_detalle(rs.getInt("dp.id_detalle"));
					
					ped_det.addProduct(prod, ip);
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
		return ped_det;
	}
	
//	public Pedido getItemsPedidos(Pedido ped) {
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		Pedido pedido = new Pedido();
//		String query;
//		try {
//			query = "SELECT nro_pedido, p.fecha, c.nombre, c.apellido, pd.id_detalle,"
//					+ " pd.importe, prod.descripcion, prod.precio, dp.subtotal, dp.cantidad"
//					+ " FROM pedido AS p"
//					+ " INNER JOIN cliente AS c ON c.id_cliente=p.id_cliente"
//					+ " INNER JOIN ped_det ON ped_det.nro_pedido=p.nro_pedido"
//					+ " INNER JOIN pedido_detalle AS pd ON pd.id_detalle=ped_det.id_detalle"
//					+ " INNER JOIN detalle_producto AS dp ON dp.id_detalle=pd.id_detalle"
//					+ " INNER JOIN producto AS prod ON prod.id_producto=dp.id_producto"
//					+ " WHERE p.nro_pedido = ?"
//					;
//			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
//			stmt.setInt(1, ped.getNro_pedido());
//			rs = stmt.executeQuery();
//			if (rs != null) {
//				while (rs.next()) {
//					Producto prod = new Producto();
//					ItemPedido ip = new ItemPedido();
//					PedidoDetalle
//					
//					prod.setId(rs.getInt("p.id_producto"));
//					prod.setDescrip(rs.getString("p.descripcion"));
//					ip.setCantidad(rs.getInt("dp.cantidad"));
//					ip.setSubtotal(rs.getDouble("dp.subtotal"));
//					ped_det.setId_detalle(rs.getInt("dp.id_detalle"));
//					
//					ped_det.addProduct(prod, ip);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (stmt != null) {
//					stmt.close();
//				}
//				DbConnector.getInstancia().releaseConn();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return ped_det;
//	}

	public Pedido getById(Pedido ped) {
		Pedido p = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query;
		try {
			query = "SELECT nro_pedido, fecha, id_cliente FROM pedido WHERE nro_pedido=?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, ped.getNro_pedido());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				p = new Pedido();
				p.setNro_pedido(rs.getInt("nro_pedido"));
				p.setFecha(rs.getObject("fecha", LocalDate.class));
				
				PedidoDetalle pd = new PedidoDetalle();
				pd = setDetalleProducto(p);
				p.setDetalle(pd);
				
				Cliente cli = new Cliente();
				cli.setId_cliente(rs.getInt("id_cliente"));
				p.setCliente(cli);
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

	public void newPedido(Pedido p, Cliente c) {
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		String query;
		try {
			query = "INSERT INTO pedido(fecha, id_cliente) VALUES(?, ?)";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query,
					PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setObject(1, p.getFecha());
			stmt.setInt(2, c.getId_cliente());
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

	public void createDetalle(PedidoDetalle pd) {
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		String query;
		try {
			query = "INSERT INTO pedido_detalle(importe) VALUES(0)";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query,
					PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();

			keyResultSet = stmt.getGeneratedKeys();
			if (keyResultSet != null && keyResultSet.next()) {
				pd.setId_detalle(keyResultSet.getInt(1));
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

	public void updateImporte(PedidoDetalle pd) {
		PreparedStatement stmt = null;
		String query;
		try {
			query = "UPDATE pedido_detalle SET importe=? WHERE id_detalle=?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setDouble(1, pd.getImporte());
			stmt.setInt(2, pd.getId_detalle());
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

	public void insertPedDet(Pedido p) {
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		String query;
		try {
			query = "INSERT INTO ped_det(nro_pedido, id_detalle) VALUES(?, ?)";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query,
					PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, p.getNro_pedido());
			stmt.setInt(2, p.getDetalle().getId_detalle());
			stmt.executeUpdate();

			keyResultSet = stmt.getGeneratedKeys();
			if (keyResultSet != null && keyResultSet.next()) {
				p.getDetalle().setId_detalle(keyResultSet.getInt(1));
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

	public void addProduct(ItemPedido ip) {
		PreparedStatement stmt = null;
		String query;
		try {
			query = "INSERT INTO detalle_producto(id_detalle, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, ip.getId_detalle());
			stmt.setInt(2, ip.getId_producto());
			stmt.setInt(3, ip.getCantidad());
			stmt.setDouble(4, ip.getSubtotal());
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

	public PedidoDetalle setDetalleProducto(Pedido ped) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PedidoDetalle pd = new PedidoDetalle();
		String query;
		try {
			query = "SELECT p.nro_pedido, p.fecha, pd.id_detalle, pd.importe, dp.id_producto, dp.cantidad, dp.subtotal, prod.descripcion"
					+ " FROM pedido AS p INNER JOIN ped_det ON p.nro_pedido=ped_det.nro_pedido"
					+ " INNER JOIN pedido_detalle AS pd ON pd.id_detalle=ped_det.id_detalle"
					+ " INNER JOIN detalle_producto AS dp ON dp.id_detalle=ped_det.id_detalle"
					+ " INNER JOIN producto AS prod ON dp.id_producto=prod.id_producto" + " WHERE p.nro_pedido=?"
					+ " GROUP BY dp.id_producto";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, ped.getNro_pedido());
			rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Producto prod = new Producto();
					ItemPedido ip = new ItemPedido();

					pd.setId_detalle(rs.getInt("pd.id_detalle"));
					pd.setImporte(rs.getDouble("pd.importe"));
					prod.setId(rs.getInt("dp.id_producto"));
					prod.setDescrip(rs.getString("prod.descripcion"));
					ip.setCantidad(rs.getInt("dp.cantidad"));
					ip.setSubtotal(rs.getDouble("dp.subtotal"));
					ip.setId_producto(rs.getInt("dp.id_producto"));
					ip.setId_detalle(rs.getInt("pd.id_detalle"));

					pd.addProduct(prod, ip);
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

		return pd;
	}

	private void setCliente(Pedido ped) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query;
		try {
			query = "SELECT id_cliente, nombre, apellido, telefono FROM cliente WHERE id_cliente=?";
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, ped.getCliente().getId_cliente());
			rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Cliente c = new Cliente();
					c.setId_cliente(rs.getInt("id_cliente"));
					c.setNombre(rs.getString("nombre"));
					c.setApellido(rs.getString("apellido"));
					c.setTel(rs.getString("telefono"));

					ped.setCliente(c);
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
