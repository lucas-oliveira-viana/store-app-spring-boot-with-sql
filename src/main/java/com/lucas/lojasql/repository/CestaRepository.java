package com.lucas.lojasql.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.exception.db.DBException;
import com.lucas.lojasql.interfaces.CestaInterface;
import com.lucas.lojasql.jdbc.DB;

public class CestaRepository implements CestaInterface {

	private Connection conn;

	public CestaRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(ProdutoComprado produtoComprado) {
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("INSERT INTO cesta " + "(id_estoque, id_compra) VALUES " + " (?, ?)");
			preencheInterrogacoesCesta(produtoComprado, ps);
			ps.execute();
			
			conn.commit();
		
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transação não foi concluida! Erro: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Erro no Rollback! Erro: " + e1.getMessage());
			}
		} finally {
			DB.closeStatement(ps);
		}
	}

	private void preencheInterrogacoesCesta(ProdutoComprado produtoComprado, PreparedStatement ps) throws SQLException {
		ps.setInt(1, produtoComprado.getIdEstoque());
		ps.setInt(2, produtoComprado.getIdCompra());
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("DELETE FROM cesta WHERE id_compra = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transação não foi concluida! Erro: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Erro no Rollback! Erro: " + e1.getMessage());
			}
		} finally {
			DB.closeStatement(ps);
		}
	}
}
