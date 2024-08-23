package com.mytop10.web.templates;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

public class Template {

    private Template() { }

    public static void render(
            String template, 
            HttpServletRequest request, 
            HttpServletResponse response) throws IOException {

        ServletContext servletContext = request.getServletContext();

        // Obtém o TemplateEngine do ServletContext
        TemplateEngine templateEngine = (TemplateEngine) servletContext.getAttribute("templateEngine");

        // Configura o contexto do Thymeleaf para Web
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        // Configura o tipo de conteúdo e processa o template
        response.setContentType("text/html;charset=UTF-8");
        templateEngine.process(template, ctx, response.getWriter());
    }
}
