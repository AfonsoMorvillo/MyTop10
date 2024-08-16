package edu.ifsp.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/salvar")
public class SalvarTop10 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        
        String jsonData = stringBuilder.toString();
        
        Gson gson = new Gson();
        List<CardInfo> cardData = gson.fromJson(jsonData, new TypeToken<List<CardInfo>>(){}.getType());

        HttpSession session = request.getSession();
        
        Map<Integer, List<CardInfo>> carrinho = (Map<Integer, List<CardInfo>>) session.getAttribute("top10");
        
        if (carrinho == null) {
            carrinho = new HashMap<>();
        }
        
        int id = carrinho.size() + 1; 

        carrinho.put(id, cardData);

        session.setAttribute("top10", carrinho);

        for (CardInfo card : cardData) {
            System.out.println("Card ID: " + card.getId() + ", Posição: " + card.getPosicao());
        }
        
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private class CardInfo {
        private String id;
        private int posicao; // Novo atributo para posição

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPosicao() {
            return posicao;
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }
    }
}
