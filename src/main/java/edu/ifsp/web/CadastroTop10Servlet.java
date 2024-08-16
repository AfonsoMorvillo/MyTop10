package edu.ifsp.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.web.templates.Template;
import model.Top10;
import model.Top10DAO;

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
      Top10DAO top10dao = new Top10DAO();
      int criaTop10 = top10dao.criaTop10( new Top10(descricao , nome));
      System.out.println(criaTop10);

   }
}
