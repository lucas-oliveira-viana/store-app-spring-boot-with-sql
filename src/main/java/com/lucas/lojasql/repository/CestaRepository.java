package com.lucas.lojasql.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.interfaces.CestaInterface;
import com.lucas.lojasql.jdbc.DB;
import com.lucas.lojasql.jdbc.DBException;

public class CestaRepository implements CestaInterface {

	private Connection conn;

	public CestaRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(ProdutoComprado produtoComprado) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO cesta " + "(id_estoque, id_compra) VALUES " + " (?, ?)");

			preencheInterrogacoesCesta(produtoComprado, ps);
			ps.execute();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
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
			ps = conn.prepareStatement("DELETE FROM cesta WHERE id_compra = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}
}
