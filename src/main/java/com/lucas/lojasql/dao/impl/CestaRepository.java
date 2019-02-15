package com.lucas.lojasql.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lucas.lojasql.dao.CestaInterface;
import com.lucas.lojasql.database.DB;
import com.lucas.lojasql.database.DBException;
import com.lucas.lojasql.entities.ProdutoComprado;

public class CestaRepository implements CestaInterface{
	
	private Connection conn;

	public CestaRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(ProdutoComprado produtoComprado) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO cesta " + "(id_estoque, id_compra) VALUES "
							+ " (?, ?)");

			setaCestaDaRequisicao(produtoComprado, ps);
			ps.execute();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	private void setaCestaDaRequisicao(ProdutoComprado produtoComprado, PreparedStatement ps) throws SQLException {
		ps.setInt(1, produtoComprado.getIdEstoque());
		ps.setInt(2, produtoComprado.getIdCompra());
	}
}
