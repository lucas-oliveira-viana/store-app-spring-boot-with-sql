package com.lucas.lojasql.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lucas.lojasql.dao.EstoqueInterface;
import com.lucas.lojasql.database.DB;
import com.lucas.lojasql.database.DBException;
import com.lucas.lojasql.entities.Estoque;

public class EstoqueRepository implements EstoqueInterface {

	private static final int COLUNA_ID = 1;
	private Connection conn;

	public EstoqueRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Estoque> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM estoque");
			rs = ps.executeQuery();

			List<Estoque> produtosEmEstoque = new ArrayList<>();

			while (rs.next()) {
				produtosEmEstoque.add(pegaColunasDaTabelaEstoque(rs));
			}

			if (produtosEmEstoque.size() > 0) {
				return produtosEmEstoque;
			}
			throw new DBException("Erro ao instanciar cliente!");
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public Estoque findByCodigoBarras(String codigoDeBarras) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM estoque WHERE CodigoBarras = ?");
			ps.setString(1, codigoDeBarras);
			rs = ps.executeQuery();

			while (rs.next()) {
				Estoque estoque = pegaColunasDaTabelaEstoque(rs);
				return estoque;
			}
			throw new DBException("Erro ao encontrar produto!");
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public void insert(Estoque estoque) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO estoque " + "(Nome, Valor, CodigoBarras, Estoque) VALUES "
							+ " (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			setaEstoqueDaRequisicao(estoque, ps);
			int linhasAdicionadas = ps.executeUpdate();

			if (linhasAdicionadas > 0) {
				ResultSet rs = setaIdDeTodosProdutosAdicionados(estoque, ps);
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


	@Override
	public void update(Estoque estoque) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE estoque SET Nome = ?, Valor = ?, CodigoBarras = ?"
					+ ", Estoque = ? WHERE id = ?");
			setaEstoqueDaRequisicao(estoque, ps);
			ps.setInt(5, estoque.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteByCodigoBarras(String codigoDeBarras) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM estoque WHERE CodigoBarras = ?");
			ps.setString(1, codigoDeBarras);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}
	
	private Estoque pegaColunasDaTabelaEstoque(ResultSet rs) throws SQLException {
		Estoque estoque = new Estoque();
		estoque.setId(rs.getInt("Id"));
		estoque.setNome(rs.getString("Nome"));
		estoque.setValor(rs.getDouble("Valor"));
		estoque.setCodigoBarras(rs.getString("CodigoBarras"));
		estoque.setEstoque(rs.getInt("Estoque"));
		return estoque;
	}
	
	private ResultSet setaIdDeTodosProdutosAdicionados(Estoque estoque, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			estoque.setId(rs.getInt(COLUNA_ID));
		}
		return rs;
	}
	
	private void setaEstoqueDaRequisicao(Estoque estoque, PreparedStatement ps) throws SQLException {
		ps.setString(1, estoque.getNome());
		ps.setDouble(2, estoque.getValor());
		ps.setString(3, estoque.getCodigoBarras());
		ps.setInt(4, estoque.getEstoque());
	}
}
