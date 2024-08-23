package com.mytop10.web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.mytop10.web.Top10Session;

@WebListener
public class Top10SessionListener implements HttpSessionListener {

   public Top10SessionListener() {
   }

   public void sessionDestroyed( HttpSessionEvent se ) {
      System.out.println( "Sessao destruida" );
      HttpSession session = se.getSession();

      Top10Session.saveTop10Session( session );
   }

}
