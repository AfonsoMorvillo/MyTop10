package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.DatabaseConnection;

public class MusicaTop10DAO {
	public void salvarTop10(int id, List<Musica> musicas ) throws SQLException {
        DatabaseConnection instance = DatabaseConnection.getInstance();

        String sql = "INSERT INTO musica_top10 (ID_musica, ID_top10, ordem_musica) VALUES (?, ?, ?);";

        try (PreparedStatement ps = instance.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            for (Musica musica : musicas) {
            	ps.setString(1, musica.getId());
            	ps.setInt(2, id);
            	ps.setInt(3, musica.getOrdem());
            	ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public List<MusicaTop10> listarTodasMusicaTop10() {
		 DatabaseConnection instance = DatabaseConnection.getInstance();

        List<MusicaTop10> resultado = new ArrayList<>();
        String sql = "SELECT m.ID as id_spotify, m.Descricao AS musica_descricao, m.ordem_musica, t.Titulo AS top10_titulo, t.Descricao AS top10_descricao " +
                     "FROM musica_top10 mt " +
                     "LEFT JOIN musica m ON mt.ID_musica = m.ID " +
                     "LEFT JOIN top10 t ON mt.ID_top10 = t.ID";

        try (
             PreparedStatement ps = instance.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MusicaTop10 item = new MusicaTop10();
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
        String sql = "SELECT mt.ordem_musica as ordem, t.Titulo AS top10_titulo, t.Descricao AS top10_descricao, "
                    + "mt.id_musica AS musica " +
                     "FROM musica_top10 mt " +
                     "LEFT JOIN musica m ON mt.ID_musica = m.ID " +
                     "LEFT JOIN top10 t ON mt.ID_top10 = t.ID " +
                     "WHERE t.id = ?"; 
        
        DatabaseConnection instance = DatabaseConnection.getInstance();

        try (PreparedStatement ps = instance.prepareStatement(sql, PreparedStatement.NO_GENERATED_KEYS)) {

            ps.setInt(1,chave); 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MusicaTop10 item = new MusicaTop10();
                    item.getTop10().setTitulo(rs.getString("top10_titulo"));
                    item.getTop10().setDescricao(rs.getString("top10_descricao"));
                    item.getMusica().setId(rs.getString("musica"));
                    item.getMusica().setOrdem(rs.getInt("ordem"));
                    resultado.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
