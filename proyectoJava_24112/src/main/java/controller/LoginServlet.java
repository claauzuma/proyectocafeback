package controller;

import conexion.Puerto;
import dao.UsuarioDao;
import modelo.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

  
        String email = request.getParameter("email");
        String password = request.getParameter("password");

   
        UsuarioDao userDao = new UsuarioDao();
        Usuario usuario = userDao.obtenerUsuarioPorEmailYPassword(email, password);

     
        if (usuario != null) {
            String nombre = usuario.getNombre();
            System.out.println(usuario.getNombre());
            System.out.println(usuario.getApellido());
            System.out.println(usuario.getEmail());
            System.out.println(usuario.getTipoUsuario()); 
            
            if (usuario.getTipoUsuario().equals("admin")) {
   
           
                response.sendRedirect( Puerto.direccion + "gestionUsuariosAdmin.html?nombre=" + nombre);
                
                
            } else {
            boolean logeado = true;
          response.sendRedirect(Puerto.direccion + "index.html?source=" + logeado + "&nombre=" + nombre + "&tipo=" + usuario.getTipoUsuario());
            }

        } else {
            System.out.println("No Valido");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Usuario no registrado");
            response.sendRedirect(Puerto.direccion+"sesion.html");
        }
    }
}
