package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UsuarioDao;
import modelo.Usuario;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Pedido;

@WebServlet("/GestionUsuariosServlet")

public class GestionUsuariosServlet extends HttpServlet {
    private final UsuarioDao usuarioDAO;
    private final ObjectMapper objectMapper;

    public GestionUsuariosServlet() {
        this.usuarioDAO = new UsuarioDao();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ENTRA POR ACA PRIMERO USUARIOS");
        // Configurar las cabeceras CORS
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

       
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Usuario usuario = usuarioDAO.obtenerPorId(id);
                if (usuario != null) {
                    objectMapper.writeValue(response.getWriter(), usuario);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    objectMapper.writeValue(response.getWriter(), "Usuario no encontrado");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(response.getWriter(), "ID inválido");
            }
        } else {
            System.out.println("AHORA ESTAMOS POR OBTENER USUARIOS POR TIPO");
            List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
            System.out.println("YA OBTUVIMOS USUARIOS POR TIPO");
            System.out.println(usuarios);
            objectMapper.writeValue(response.getWriter(), usuarios);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        
         
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
        try {
     
            Usuario usuario = objectMapper.readValue(request.getReader(), Usuario.class);
            
         
            boolean exito = usuarioDAO.modificar(usuario);
            
            response.getWriter().write("{\"exito\": " + exito + "}");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(response.getWriter(), "Error al procesar la solicitud");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
              response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        try {
           
            int id = Integer.parseInt(request.getParameter("id"));

        
            boolean exito = usuarioDAO.eliminar(id);

           
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"exito\": " + exito + "}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(response.getWriter(), "ID inválido");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
  
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

     
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
          
            Usuario usuario = objectMapper.readValue(request.getReader(), Usuario.class);
            
           
            boolean exito = usuarioDAO.insertarUsuario(usuario);
            
            response.getWriter().write("{\"exito\": " + exito + "}");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(response.getWriter(), "Error al procesar la solicitud");
        }
    }
    
    
    
    
}
