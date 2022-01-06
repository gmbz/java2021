package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import models.Categoria;
import models.Producto;

public class CategoriaDb {

	public LinkedList<Categoria> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Categoria> cats= new LinkedList<>();
		String query;
		try {
			query = "SELECT id_categoria, descripcion FROM categoria";
			stmt= DbConnector.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery(query);
			if(rs!=null) {
				while(rs.next()) {
					Categoria c = new Categoria();
					c.setId(rs.getInt("id_categoria"));
					c.setDescripcion(rs.getString("descripcion"));
					
					cats.add(c);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cats;
	}
	
	public Categoria getById(Categoria cat) {
		Categoria c = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String query;
		try {
			query = "SELECT id_categoria, descripcion FROM categoria WHERE id_categoria=?";
			stmt=DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, cat.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				c = new Categoria();
				c.setId(rs.getInt("id_categoria"));
				c.setDescripcion(rs.getString("descripcion"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return c;
	}
	
	public void create(Categoria c) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		String query;
		try {
			query = "INSERT INTO categoria(descripcion) VALUES(?)";
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, c.getDescripcion());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                c.setId(keyResultSet.getInt(1));
            }
		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
    }
	
	public void delete(Categoria c) {
		PreparedStatement stmt= null;
		String query;
		try {
			query = "DELETE FROM categoria WHERE id_categoria = ?";
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(query);
			stmt.setInt(1, c.getId());
			stmt.executeUpdate();

		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}
	
	public void update(Categoria c) {
		PreparedStatement stmt= null;
		String query;
		try {
			query = "UPDATE categoria SET descripcion=? WHERE id_categoria=?";
			stmt=DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setString(1, c.getDescripcion());
			stmt.setInt(2, c.getId());
			stmt.executeUpdate();

		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}
	
	public void setCategoria(Producto prod) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String query;
		try {
			query = "SELECT id_categoria, descripcion FROM categoria WHERE id_categoria=?";
			stmt=DbConnector.getInstancia().getConn().prepareStatement(query);
			stmt.setInt(1, prod.getCategoria().getId());
			rs= stmt.executeQuery();
			if(rs!=null && rs.next()) {
				Categoria cat = new Categoria();
				cat.setId(rs.getInt("id_categoria"));
				cat.setDescripcion(rs.getString("descripcion"));
				prod.setCategoria(cat);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
