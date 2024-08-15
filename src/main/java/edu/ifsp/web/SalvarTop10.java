package edu.ifsp.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
//        // Processa a lista de CardInfo
//        for (CardInfo card : cardData) {
//            System.out.println("Card ID: " + card.getId());
//        }
//        
//        HttpSession session = request.getSession();
//        HAshmap top10 = (map)session.getAttribute("carrinho");
//        
//        
//        
//        carrinho.adicionar(produto);

    }

    // Classe interna para representar os dados dos cartões
    private class CardInfo {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
