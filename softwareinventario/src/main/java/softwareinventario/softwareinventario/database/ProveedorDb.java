/*
package softwareinventario.softwareinventario.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import softwareinventario.softwareinventario.models.Proveedor;
import softwareinventario.softwareinventario.models.Producto;

public class ProveedorDb {
    public LinkedList<Proveedor> getAll() {
        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Proveedor> provs = new LinkedList<>();
        String query;
        try {
            query = "SELECT id_proveedor, nombre, apellido, telefono FROM proveedores";
            stmt = DbConnector.getInstancia().getConn().createStatement();
            rs = stmt.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    Proveedor p = new Proveedor();
                    p.setId(rs.getInt("id_proveedor"));
                    p.setNombre(rs.getString("nombre"));
                    p.setApellido(rs.getString("apellido"));
                    p.setTel(rs.getString("telefono"));

                    provs.add(p);
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
        return provs;
    }

    public Proveedor getById(Proveedor prov) {
        Proveedor p = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query;
        try {
            query = "SELECT id_proveedor, nombre, apellido, telefono FROM proveedores WHERE id_proveedor=?";
            stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
            stmt.setInt(1, prov.getId());
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                p = new Proveedor();
                p.setId(rs.getInt("id_proveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setTel(rs.getString("telefono"));

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

    public void create(Proveedor p) {
        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;
        String query;
        try {
            query = "INSERT INTO proveedores(nombre, apellido, telefono) VALUES(?, ?, ?)";
            stmt = DbConnector.getInstancia().getConn().prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getApellido());
            stmt.setString(3, p.getTel());

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

    public void delete(Proveedor p) {
        PreparedStatement stmt = null;
        String query;
        try {
            query = "DELETE FROM proveedores WHERE id_proveedor = ?";
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

    public void update(Proveedor p) {
        PreparedStatement stmt = null;
        String query;
        try {
            query = "UPDATE proveedores SET nombre=?, apellido=?, telefono=? WHERE id_proveedor=?";
            stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getApellido());
            stmt.setString(3, p.getTel());
            stmt.setInt(4, p.getId());

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

    public void setProveedor(Producto prod) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query;
        try {
            query = "SELECT id_proveedor, nombre, apellido, telefono FROM proveedores WHERE id_proveedor=?";
            stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
            stmt.setInt(1, prod.getProveedor().getId());
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                Proveedor prov = new Proveedor();
                prov.setId(rs.getInt("id_proveedor"));
                prov.setNombre(rs.getString("nombre"));
                prov.setApellido(rs.getString("apellido"));
                prov.setTel(rs.getString("telefono"));

                prod.setProveedor(prov);
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
*/