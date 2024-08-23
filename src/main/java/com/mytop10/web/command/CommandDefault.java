package com.mytop10.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytop10.web.templates.Template;

public class CommandDefault implements Command {

   @Override
   public void execute( HttpServletRequest request, HttpServletResponse response ) throws Exception {
      System.out.println( "Comando desconhecido " + request.getRequestURI() );

      response.sendRedirect( request.getContextPath() + "/top10/listar" );
   }

}
