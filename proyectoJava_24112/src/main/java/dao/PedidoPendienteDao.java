package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.PedidoPendiente;

public class PedidoPendienteDao {

    public boolean insertarPedidoPendiente(PedidoPendiente pedidoPendiente) {
        String SQL = "INSERT INTO pedidosPendientes (numero_de_mesa, nombre, descripcion, preciototal) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, pedidoPendiente.getNumeroDeMesa());
            pstmt.setString(2, pedidoPendiente.getNombre());
            pstmt.setString(3, pedidoPendiente.getDescripcion());
            pstmt.setDouble(4, pedidoPendiente.getPrecioTotal());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PedidoPendiente> obtenerTodos() {
        List<PedidoPendiente> pedidosPendientes = new ArrayList<>();
        String query = "SELECT * FROM pedidosPendientes";

        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                PedidoPendiente pedidoPendiente = extraerPedidoPendienteDeResultSet(rs);
                pedidosPendientes.add(pedidoPendiente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidosPendientes;
    }

    public PedidoPendiente obtenerPedidoPendientePorId(int id) {
        String query = "SELECT * FROM pedidosPendientes WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerPedidoPendienteDeResultSet(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarPedidoPendiente(PedidoPendiente pedidoPendiente) {
        String query = "UPDATE pedidosPendientes SET numero_de_mesa = ?, nombre = ?, descripcion = ?, preciototal = ? WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, pedidoPendiente.getNumeroDeMesa());
            pstmt.setString(2, pedidoPendiente.getNombre());
            pstmt.setString(3, pedidoPendiente.getDescripcion());
            pstmt.setDouble(4, pedidoPendiente.getPrecioTotal());
            pstmt.setInt(5, pedidoPendiente.getId());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarPedidoPendiente(int id) {
        String query = "DELETE FROM pedidosPendientes WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private PedidoPendiente extraerPedidoPendienteDeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int numeroDeMesa = rs.getInt("numero_de_mesa");
        String nombre = rs.getString("nombre");
        String descripcion = rs.getString("descripcion");
        double precioTotal = rs.getDouble("preciototal");

        return new PedidoPendiente(id, numeroDeMesa, nombre, descripcion, precioTotal);
    }
}
