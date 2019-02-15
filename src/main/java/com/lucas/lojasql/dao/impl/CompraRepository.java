package com.lucas.lojasql.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.lucas.lojasql.dao.CompraInterface;
import com.lucas.lojasql.database.DB;
import com.lucas.lojasql.database.DBException;
import com.lucas.lojasql.entities.Compra;

public class CompraRepository implements CompraInterface {

	private static final int COLUNA_ID = 1;
	private Connection conn;

	public CompraRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Compra> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Compra findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Compra findByProdutoComprado(String produtoComprado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Compra compra) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO compra "
					+ "(id_cliente, id_funcionario, FormaPagamento, ValorTotal) VALUES " + " (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			setaCompraDaRequisicao(compra, ps);
			int linhasAfetadas = ps.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = setaIdDeTodasComprasAdicionadas(compra, ps);
				DB.closeResultSet(rs);
			} else {
				throw new DBException("Erro inesperado! Nenhuma linha adicionada!");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	private void setaCompraDaRequisicao(Compra compra, PreparedStatement ps) throws SQLException {
		ps.setInt(1, compra.getCliente().getId());
		ps.setInt(2, compra.getFuncionario().getId());
		ps.setString(3, compra.getFormaPagamento());
		ps.setDouble(4, compra.getValorTotal());
	}

	private ResultSet setaIdDeTodasComprasAdicionadas(Compra compra, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			compra.setId(rs.getInt(COLUNA_ID));
		}
		return rs;
	}

	@Override
	public void update(Compra compra) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub

	}
}
