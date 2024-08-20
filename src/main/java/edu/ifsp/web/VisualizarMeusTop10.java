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

@WebServlet("/VisualizarMeusTop10")
public class VisualizarMeusTop10 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MusicaTop10DAO dao = new MusicaTop10DAO();
		List<MusicaTop10> tops10 = dao.listarTodasMusicaTop10();
		request.setAttribute("tops10", tops10);
		Template.render("top10/verTop10", request, response);
	}
}
