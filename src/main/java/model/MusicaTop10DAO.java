package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.DatabaseConnection;

public class MusicaTop10DAO {
	public void salvarTop10(int id, ArrayList<Musica> musicas ) throws SQLException {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        Connection conn = instance.getConn();

        String sql = "INSERT INTO musica_top10 (ID_musica, ID_top10) VALUES (?, ?);";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Musica musica : musicas) {
            	MusicaDAO musicaDAO = new MusicaDAO();
            	String idDaMusica = musicaDAO.insertMusica(musica);
            	ps.setString(1, idDaMusica);
            	ps.setInt(2, id);
            	ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public List<MusicaTop10> listarTodosMusicaTop10() {
        List<MusicaTop10> resultado = new ArrayList<>();
        String sql = "SELECT m.ID as id_spotify, m.Descricao AS musica_descricao, m.ordem_musica, t.Titulo AS top10_titulo, t.Descricao AS top10_descricao " +
                     "FROM musica_top10 mt " +
                     "LEFT JOIN musica m ON mt.ID_musica = m.ID " +
                     "LEFT JOIN top10 t ON mt.ID_top10 = t.ID";

        try (Connection conn = DatabaseConnection.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MusicaTop10 item = new MusicaTop10();
//                item.set(rs.getString("musica_descricao"));
//                item.setOrdem(rs.getInt("ordem_musica"));
//                item.setTop10Titulo(rs.getString("top10_titulo"));
//                item.setTop10Descricao(rs.getString("top10_descricao"));
                item.getTop10().setTitulo(rs.getString("top10_titulo"));
                item.getTop10().setDescricao(rs.getString("top10_descricao"));
                item.getMusica().setId(rs.getString("id_spotify"));
                item.getMusica().setOrdem(rs.getInt("ordem_musica"));
                resultado.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    
    public List<MusicaTop10> listarMusicaTop10PorTitulo(Integer chave) {
        List<MusicaTop10> resultado = new ArrayList<>();
        String sql = "SELECT m.Descricao AS musica_descricao, m.ordem_musica, t.Titulo AS top10_titulo, t.Descricao AS top10_descricao " +
                     "FROM musica_top10 mt " +
                     "LEFT JOIN musica m ON mt.ID_musica = m.ID " +
                     "LEFT JOIN top10 t ON mt.ID_top10 = t.ID " +
                     "WHERE t.id = ?"; 

        try (Connection conn = DatabaseConnection.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,chave); 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MusicaTop10 item = new MusicaTop10();
                    item.getTop10().setTitulo(rs.getString("top10_titulo"));
                    item.getTop10().setDescricao(rs.getString("top10_descricao"));
                    item.getMusica().setId(rs.getString("id_spotify"));
                    item.getMusica().setOrdem(rs.getInt("ordem_musica"));
                    resultado.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
