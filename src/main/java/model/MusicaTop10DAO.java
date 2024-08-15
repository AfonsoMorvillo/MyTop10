package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import controller.DatabaseConnection;

public class MusicaTop10DAO {
	private void salvarNoCarrinho() throws SQLException {
		DatabaseConnection instance = DatabaseConnection.getInstance();
		Connection conn = instance.getConn();
		PreparedStatement ps = conn.prepareStatement("insert into musica (descricao) values (?);");
//		for (Produto p : carrinho.getItens()) {
//			ps.setInt(1, carrinho.getUsuario().getId());
//			ps.setInt(2, p.getId());
//			ps.executeUpdate();
//		}
		
		
		
		
		//
	}
}
