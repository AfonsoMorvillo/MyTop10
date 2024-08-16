package edu.ifsp.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.web.templates.Template;

/**
 * Servlet implementation class Top10Servlet
 */
@WebServlet("/top10/*")
public class Top10Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // Deve retornar algo como "/123" se a URL for "/top10/123"
//        
//        if (pathInfo != null && pathInfo.length() > 1) {
//            String id = pathInfo.substring(1);
//            response.getWriter().append("ID: ").append(id);
//        } else {
//            response.getWriter().append("ID não fornecido");
//        }
        Template.render("top10/index", request, response);      
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
