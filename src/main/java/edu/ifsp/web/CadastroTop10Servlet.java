package edu.ifsp.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.web.templates.Template;

@WebServlet("/novo")
public class CadastroTop10Servlet extends HttpServlet {

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {      
      Template.render("top10/novo", request, response);      
   }
   
   protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

      String nome = request.getParameter( "nome" );
      String descricao = request.getParameter( "descricao" );

         
      request.setAttribute( "usuario", nome);
      request.setAttribute( "senha", descricao );
         
      

   }
}
