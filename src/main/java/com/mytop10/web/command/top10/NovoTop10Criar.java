package com.mytop10.web.command.top10;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytop10.dao.Top10DAO;
import com.mytop10.modelo.Top10;
import com.mytop10.web.command.Command;

public class NovoTop10Criar implements Command {

   @Override
   public void execute( HttpServletRequest request, HttpServletResponse response ) throws Exception {
      String nome = request.getParameter( "nome" );
      String descricao = request.getParameter( "descricao" );

      request.setAttribute( "usuario", nome );
      request.setAttribute( "senha", descricao );
      Top10DAO top10dao = new Top10DAO();
      int criaTop10 = top10dao.criaTop10( new Top10( descricao, nome ) );

      response.sendRedirect( request.getContextPath() + "/top10/" + criaTop10 );

   }

}
