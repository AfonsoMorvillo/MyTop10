package com.mytop10.web.command.top10;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytop10.web.command.Command;

public class SalvarSessaoTop10 implements Command {

   @Override
   public void execute( HttpServletRequest request, HttpServletResponse response ) throws Exception {
      request.getSession().invalidate();
   }

}
