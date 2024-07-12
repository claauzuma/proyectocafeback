package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PedidoDao;
import modelo.Pedido;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/GestionPedidosServlet")
public class GestionPedidosServlet extends HttpServlet {
    private PedidoDao pedidoDAO;
    private ObjectMapper objectMapper;

    public GestionPedidosServlet() {
        this.pedidoDAO = new PedidoDao();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ENTRA POR ACA PRIMERO PEDIDOS");
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Pedido pedido = pedidoDAO.obtenerPedidoPorId(id);
                if (pedido != null) {
                    objectMapper.writeValue(response.getWriter(), pedido);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    objectMapper.writeValue(response.getWriter(), "Pedido no encontrado");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(response.getWriter(), "ID inválido");
            }
        } else {
            List<Pedido> pedidos = pedidoDAO.obtenerTodos();
            System.out.println(pedidos);
            objectMapper.writeValue(response.getWriter(), pedidos);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
   
            Pedido pedido = objectMapper.readValue(request.getReader(), Pedido.class);

        
            boolean exito = pedidoDAO.insertarPedido(pedido);

           
            response.getWriter().write("{\"exito\": " + exito + "}");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(response.getWriter(), "Error al procesar la solicitud");
        }
    }

@Override
protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");


    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    try {
  
        System.out.println("Estamos por actualizar el PEDIDO");
        Pedido pedido = objectMapper.readValue(request.getReader(), Pedido.class);
        System.out.println("Pedido actualziaDO "  + pedido);


        boolean exito = pedidoDAO.actualizarPedido(pedido);


        if (exito) {
            System.out.println("Pedido actualizado en la base de datos.");
        } else {
            System.out.println("Falló la actualización del pedido.");
        }


        response.getWriter().write("{\"exito\": " + exito + "}");
    } catch (IOException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        objectMapper.writeValue(response.getWriter(), "Error al procesar la solicitud");
    }
}

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            boolean exito = pedidoDAO.eliminarPedido(id);

    
            response.getWriter().write("{\"exito\": " + exito + "}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(response.getWriter(), "ID inválido");
        }
    }
}