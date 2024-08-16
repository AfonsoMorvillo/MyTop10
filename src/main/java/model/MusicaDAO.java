package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import controller.DatabaseConnection;

public class MusicaDAO {
	public String insertMusica(Musica musica) {
		DatabaseConnection instance = DatabaseConnection.getInstance();
		Connection conn = instance.getConn();
		String sql = "INSERT INTO musica (ID, Ordem ) VALUES (?);";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, musica.getId());
			ps.setInt(2, musica.getOrdem());
			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("ID inserido: " + musica.getId());
			} else {
				System.out.println("Nenhuma linha afetada.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return musica.getId();
	}
}
