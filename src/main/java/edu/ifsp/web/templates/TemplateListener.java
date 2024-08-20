package edu.ifsp.web.templates;

import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@WebListener
public class TemplateListener implements ServletContextListener {

    public TemplateListener() { }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        // Criação do Template Resolver
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);

        // Criação do Template Engine
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Armazenando o Template Engine no contexto do Servlet
        servletContext.setAttribute("templateEngine", templateEngine);

        Logger.getGlobal().info("Template engine OK");
    }
}
