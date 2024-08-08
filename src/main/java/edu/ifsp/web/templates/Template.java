package edu.ifsp.web.templates;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

public class Template {
	
		private Template() { }

		public static void render(
				String template, 
				HttpServletRequest request, 
				HttpServletResponse response) throws IOException {
			
			ServletContext servletContext = request.getServletContext();
			
			TemplateEngine templateEngine = 
					(TemplateEngine)servletContext.getAttribute("templateEngine");
			JavaxServletWebApplication webApplication = 
					(JavaxServletWebApplication)servletContext.getAttribute("templateWebApplication");

			response.setContentType("text/html;charset=UTF-8");
			IContext ctx = new WebContext(webApplication.buildExchange(request, response));
			templateEngine.process(template, ctx, response.getWriter());		
		}
}
