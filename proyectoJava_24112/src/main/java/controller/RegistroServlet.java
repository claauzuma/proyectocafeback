/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import conexion.Puerto;
import dao.UsuarioDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Usuario;
 
/**
 *
 * @author zumar
 */
@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        String tipoUsuario = "usuario";
     

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setFechaNacimiento(Date.valueOf(fechaNacimiento));
        usuario.setTipoUsuario(tipoUsuario);
  

        UsuarioDao usuarioDAO = new UsuarioDao();
        boolean registroExitoso = usuarioDAO.insertarUsuario(usuario);
        

        if (registroExitoso) {
              
          response.sendRedirect(Puerto.direccion + "index.html?source=logeado");
        } else {
             response.sendRedirect(Puerto.direccion +"registro.html?error=true");
        }
    }
}
