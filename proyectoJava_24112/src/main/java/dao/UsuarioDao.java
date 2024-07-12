package dao;

import conexion.ConexionDB;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    public boolean insertarUsuario(Usuario usuario) {
        String SQL = "INSERT INTO usuarios (nombre, apellido, email, password, fechaNacimiento, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getPassword());
            pstmt.setDate(5, usuario.getFechaNacimiento());
            pstmt.setString(6, usuario.getTipoUsuario());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Usuario extraerUsuarioDeResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setEmail(rs.getString("email"));
        usuario.setPassword(rs.getString("password"));
        usuario.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        usuario.setTipoUsuario(rs.getString("tipoUsuario"));
        return usuario;
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";

        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Usuario usuario = extraerUsuarioDeResultSet(rs);
                usuarios.add(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public Usuario obtenerPorId(int id) {
        String query = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerUsuarioDeResultSet(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean modificar(Usuario usuario) {
        String query = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, password = ?, fechaNacimiento = ?, tipoUsuario = ? WHERE id = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getPassword());
            pstmt.setDate(5, usuario.getFechaNacimiento());
            pstmt.setString(6, usuario.getTipoUsuario());
            pstmt.setInt(7, usuario.getId());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminar(int id) {
        String query = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validarUsuario(String email, String password) {
        String query = "SELECT * FROM usuarios WHERE email = ? AND password = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; 
    }

    public Usuario obtenerUsuarioPorEmailYPassword(String email, String password) {
        String query = "SELECT * FROM usuarios WHERE email = ? AND password = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerUsuarioDeResultSet(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }

    public List<Usuario> obtenerUsuariosPorTipo(String tipoUsuario) {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Connection conn = ConexionDB.obtenerConexion();
            String sql = "SELECT * FROM usuarios WHERE tipoUsuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tipoUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = extraerUsuarioDeResultSet(rs);
                usuarios.add(usuario);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

  public List<Usuario> obtenerTodosUsuarios() {
    List<Usuario> usuarios = new ArrayList<>();
    String query = "SELECT * FROM usuarios";

    try (Connection conn = ConexionDB.obtenerConexion();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            Usuario usuario = extraerUsuarioDeResultSet(rs);
            usuarios.add(usuario);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return usuarios;
}
}

    
    
    