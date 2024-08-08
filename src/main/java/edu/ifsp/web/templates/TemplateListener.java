package edu.ifsp.web.templates;

import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

@WebListener
public class TemplateListener implements ServletContextListener {

    public TemplateListener() { }

    public void contextDestroyed(ServletContextEvent sce)  { }

    public void contextInitialized(ServletContextEvent sce)  {

    	ServletContext servletContext = sce.getServletContext();
    	JavaxServletWebApplication webApplication = 
    			JavaxServletWebApplication.buildApplication(servletContext);
    	
    	WebApplicationTemplateResolver templateResolver = 
    			new WebApplicationTemplateResolver(webApplication);
    	
    	templateResolver.setTemplateMode(TemplateMode.HTML);
    	templateResolver.setPrefix("WEB-INF/templates/");
    	templateResolver.setSuffix(".html");
    	templateResolver.setCacheable(false);    	
    	
    	
    	TemplateEngine templateEngine = new TemplateEngine();
    	templateEngine.setTemplateResolver(templateResolver);    	
    	servletContext.setAttribute("templateEngine", templateEngine);
    	servletContext.setAttribute("templateWebApplication", webApplication);
    	
    	
    	Logger.getGlobal().info("Template engine OK");    	
    }
		
}
