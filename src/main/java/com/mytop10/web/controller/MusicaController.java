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
import com.mytop10.web.command.musica.BuscarMusica;
import com.mytop10.web.utils.RequestUtils;
@WebServlet( "/musica/*" )
public class MusicaController extends HttpServlet {
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

      if( operation.equals( "/musica/buscar" ) ){
         cmd = new BuscarMusica();
      }
      else{
          cmd = new CommandDefault();
      }

      return cmd;
   }

}
