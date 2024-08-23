package com.mytop10.web.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytop10.web.command.Command;
import com.mytop10.web.command.CommandDefault;
import com.mytop10.web.command.top10.EditarTop10;
import com.mytop10.web.command.top10.ListarTop10;
import com.mytop10.web.command.top10.NovoTop10Criar;
import com.mytop10.web.command.top10.NovoTop10Template;
import com.mytop10.web.command.top10.SalvarSessaoTop10;
import com.mytop10.web.command.top10.SalvarTop10;
import com.mytop10.web.utils.RequestUtils;

@WebServlet( "/top10/*" )
public class Top10Controller extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
      process( request, response );
   }


   protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
      process( request, response );
   }


   private void process( HttpServletRequest request, HttpServletResponse response ) throws ServletException {

      try{

         Command cmd = getCommand( request );
         cmd.execute( request, response );

      }
      catch( Exception e ){
         Logger.getGlobal().log( Level.SEVERE, e.getMessage(), e );
         throw new ServletException( e );
      }

   }


   private Command getCommand( HttpServletRequest request ) {
      String operation = RequestUtils.getOperation( request );
      String method = request.getMethod();

      Command cmd = null;

      if( operation.equals( "/top10/novo" ) ){
         if( "GET".equalsIgnoreCase( method ) ){
            cmd = new NovoTop10Template();
         }
         else if( "POST".equalsIgnoreCase( method ) ){
            cmd = new NovoTop10Criar();
         }
      }
      else if( operation.matches( "^/top10/\\d+$" ) ){
         cmd = new EditarTop10();
      }
      else if( operation.equals( "/top10/salvar" ) ){
         cmd = new SalvarTop10();
      }
      else if( operation.equals( "/top10/listar" ) ){
         cmd = new ListarTop10();
      }
      else if( operation.equals( "/top10/salvarSessao" ) ){
         cmd = new SalvarSessaoTop10();
      }
      else{
         cmd = new CommandDefault();
      }

      return cmd;
   }

}
