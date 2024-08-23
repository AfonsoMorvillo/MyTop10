package com.mytop10.web.command.top10;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytop10.dao.Top10DAO;
import com.mytop10.modelo.Top10;
import com.mytop10.web.command.Command;
import com.mytop10.web.templates.Template;

public class ListarTop10 implements Command {

   @Override
   public void execute( HttpServletRequest request, HttpServletResponse response ) throws Exception {
      Top10DAO dao = new Top10DAO();
      List<Top10> tops10 = dao.getAll();
      request.setAttribute( "tops10", tops10 );
      Template.render( "top10/listar", request, response );
   }

}
