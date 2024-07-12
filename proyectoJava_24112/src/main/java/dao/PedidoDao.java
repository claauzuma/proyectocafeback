package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Pedido;

public class PedidoDao {

    public boolean insertarPedido(Pedido pedido) {
        String SQL = "INSERT INTO pedidos (numero_de_mesa, nombre, descripcion, preciototal) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, pedido.getNumeroDeMesa());
            pstmt.setString(2, pedido.getNombre());
            pstmt.setString(3, pedido.getDescripcion());
            pstmt.setDouble(4, pedido.getPrecioTotal());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Pedido> obtenerTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT * FROM pedidos";

        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Pedido pedido = extraerPedidoDeResultSet(rs);
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public Pedido obtenerPedidoPorId(int id) {
        String query = "SELECT * FROM pedidos WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerPedidoDeResultSet(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

public boolean actualizarPedido(Pedido pedido) {
    System.out.println("Pedido a actualizar: " + pedido);
    String query = "UPDATE pedidos SET numero_de_mesa = ?, nombre = ?, descripcion = ?, preciototal = ? WHERE id = ?";
    try (Connection conn = ConexionDB.obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        // Configurar parámetros
        pstmt.setInt(1, pedido.getNumeroDeMesa());
        pstmt.setString(2, pedido.getNombre());
        pstmt.setString(3, pedido.getDescripcion());
        pstmt.setDouble(4, pedido.getPrecioTotal());
        pstmt.setInt(5, pedido.getId());


        System.out.println("Número de Mesa: " + pedido.getNumeroDeMesa());
        System.out.println("Nombre: " + pedido.getNombre());
        System.out.println("Descripción: " + pedido.getDescripcion());
        System.out.println("Precio Total: " + pedido.getPrecioTotal());
        System.out.println("ID del Pedido: " + pedido.getId());


        int filasAfectadas = pstmt.executeUpdate();
        System.out.println("Filas afectadas: " + filasAfectadas);

        if (filasAfectadas > 0) {
            System.out.println("Pedido actualizado correctamente.");
            return true;
        } else {
            System.out.println("No se encontró el pedido con el ID especificado.");
            return false;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    public boolean eliminarPedido(int id) {
        String query = "DELETE FROM pedidos WHERE id = ?";
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

    private Pedido extraerPedidoDeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int numeroDeMesa = rs.getInt("numero_de_mesa");
        String nombre = rs.getString("nombre");
        String descripcion = rs.getString("descripcion");
        double precioTotal = rs.getDouble("preciototal");

        return new Pedido(id, numeroDeMesa, nombre, descripcion, precioTotal);
    }
}
