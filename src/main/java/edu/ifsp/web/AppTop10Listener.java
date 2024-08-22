package edu.ifsp.web;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppTop10Listener implements ServletContextListener {

   private Timer timer;
   
   @Override
   public void contextInitialized(ServletContextEvent sce) {
       System.out.println("Aplicação iniciada");


       // Cria um Timer para renovar o token antes de expirar
       timer = new Timer(true);
       int delay = 0; // Delay inicial (0 significa que executa imediatamente)
       int period = 3600 * 1000; // Período para renovação em milissegundos (aqui é configurado para uma hora)

       timer.scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run() {
              SpotifyExample.clientCredentials_Sync();
           }
       }, delay, period);
   }
   
   @Override
   public void contextDestroyed(ServletContextEvent sce) {
       System.out.println("Aplicação encerrada");
       // Código a ser executado ao encerrar a aplicação
       if (timer != null) {
          timer.cancel();
      }
   }

}
