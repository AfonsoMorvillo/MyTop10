package edu.ifsp.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.web.templates.Template;
import model.MusicaTop10;
import model.MusicaTop10DAO;
import model.Top10;
import model.Top10DAO;

@WebServlet("/listar")
public class VisualizarMeusTop10 extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Top10DAO dao = new Top10DAO();
		List<Top10> tops10 = dao.getAll();
		request.setAttribute("tops10", tops10);
		Template.render("top10/listar", request, response);
	}
}
