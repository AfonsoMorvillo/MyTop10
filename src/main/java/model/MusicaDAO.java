package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.DatabaseConnection;

public class MusicaDAO {
	public String insertMusica(Musica musica) {
		DatabaseConnection instance = DatabaseConnection.getInstance();
	
		String sql = "INSERT INTO musica (ID) VALUES (?);";

		try (PreparedStatement ps = instance.prepareStatement(sql)) {
			ps.setString(1, musica.getId());
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
	
//	public List<String> checkExistingIds(List<String> ids) {
//	    DatabaseConnection instance = DatabaseConnection.getInstance();
//	    List<String> existingIds = new ArrayList<>();
//	    
//	    // Construa a consulta SQL com o operador IN
//	    StringBuilder sql = new StringBuilder("SELECT ID FROM musica WHERE ID IN (");
//	    for (int i = 0; i < ids.size(); i++) {
//	        sql.append("?");
//	        if (i < ids.size() - 1) {
//	            sql.append(", ");
//	        }
//	    }
//	    sql.append(");");
//
//	    try (Connection conn = instance.getConnection();
//	         PreparedStatement ps = conn.prepareStatement(sql.toString())) {
//	        
//	        // Defina os parâmetros da consulta
//	        for (int i = 0; i < ids.size(); i++) {
//	            ps.setString(i + 1, ids.get(i));
//	        }
//
//	        // Execute a consulta e recupere os IDs existentes
//	        try (ResultSet rs = ps.executeQuery()) {
//	            while (rs.next()) {
//	                existingIds.add(rs.getString("ID"));
//	            }
//	        }
//	        
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    
//	    return existingIds;
//	}
}
