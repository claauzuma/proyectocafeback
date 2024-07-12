package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PedidoDao;
import dao.PedidoPendienteDao;
import modelo.Pedido;
import modelo.PedidoPendiente;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/GestionPedidosPendientes")
public class GestionPedidosPendientes extends HttpServlet {
    private PedidoPendienteDao pedidoPendienteDAO;
    private PedidoDao pedidoDAO;
    private ObjectMapper objectMapper;

    public GestionPedidosPendientes() {
        this.pedidoPendienteDAO = new PedidoPendienteDao();
        this.pedidoDAO = new PedidoDao();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

     
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                PedidoPendiente pedidoPendiente = pedidoPendienteDAO.obtenerPedidoPendientePorId(id);
                if (pedidoPendiente != null) {
                    objectMapper.writeValue(response.getWriter(), pedidoPendiente);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    objectMapper.writeValue(response.getWriter(), "Pedido pendiente no encontrado");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(response.getWriter(), "ID inválido");
            }
        } else {
            
            List<PedidoPendiente> pedidosPendientes = pedidoPendienteDAO.obtenerTodos();
            objectMapper.writeValue(response.getWriter(), pedidosPendientes);
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
       
            PedidoPendiente pedidoPendiente = objectMapper.readValue(request.getReader(), PedidoPendiente.class);

         
            boolean exito = pedidoPendienteDAO.insertarPedidoPendiente(pedidoPendiente);

           
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

        String action = request.getParameter("action");

        try {
            if ("aprobar".equals(action)) {
         
                int id = Integer.parseInt(request.getParameter("id"));
                PedidoPendiente pedidoPendiente = pedidoPendienteDAO.obtenerPedidoPendientePorId(id);
                if (pedidoPendiente != null) {
                    Pedido pedido = new Pedido(
                        pedidoPendiente.getId(),
                        pedidoPendiente.getNumeroDeMesa(),
                        pedidoPendiente.getNombre(),
                        pedidoPendiente.getDescripcion(),
                        pedidoPendiente.getPrecioTotal()
                    );
                    boolean insertado = pedidoDAO.insertarPedido(pedido);
                    if (insertado) {
                        boolean eliminado = pedidoPendienteDAO.eliminarPedidoPendiente(id);
                        if (eliminado) {
                            response.getWriter().write("{\"exito\": true}");
                            return;
                        }
                    }
                }
                response.getWriter().write("{\"exito\": false}");
            } else {
     
                System.out.println("Hasta aca llega, vamos a actualizar");
                PedidoPendiente pedidoPendiente = objectMapper.readValue(request.getReader(), PedidoPendiente.class);
                boolean exito = pedidoPendienteDAO.actualizarPedidoPendiente(pedidoPendiente);
                
                response.getWriter().write("{\"exito\": " + exito + "}");
            }
        } catch (IOException | NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(response.getWriter(), "Error al procesar la solicitud");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Configurar la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
       
            int id = Integer.parseInt(request.getParameter("id"));

 
            boolean exito = pedidoPendienteDAO.eliminarPedidoPendiente(id);

      
            response.getWriter().write("{\"exito\": " + exito + "}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(response.getWriter(), "ID inválido");
        }
    }
}
