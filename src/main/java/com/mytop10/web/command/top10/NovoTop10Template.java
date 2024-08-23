package com.mytop10.web.command.top10;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytop10.web.command.Command;
import com.mytop10.web.templates.Template;

public class NovoTop10Template implements Command {

   @Override
   public void execute( HttpServletRequest request, HttpServletResponse response ) throws Exception {
      Template.render( "top10/novo", request, response );

   }

}
