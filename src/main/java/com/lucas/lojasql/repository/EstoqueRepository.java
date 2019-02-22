package com.lucas.lojasql.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.interfaces.EstoqueInterface;
import com.lucas.lojasql.jdbc.DB;
import com.lucas.lojasql.jdbc.DBException;

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
	public Estoque findByCodigoBarras(Integer codigoDeBarras) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM estoque WHERE CodigoBarras = ?");
			ps.setInt(1, codigoDeBarras);
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
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement(
					"INSERT INTO estoque " + "(Nome, Valor, CodigoBarras, Estoque) VALUES "
							+ " (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			preencheInterrogacoesEstoque(estoque, ps);
			int linhasAdicionadas = ps.executeUpdate();
			
			conn.commit();

			if (linhasAdicionadas > 0) {
				ResultSet rs = setaIdDeTodosProdutosAdicionados(estoque, ps);
				DB.closeResultSet(rs);
			} else {
				throw new DBException("Erro inesperado! Nenhuma linha adicionada!");
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transação não foi concluida! Erro: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Erro no Rollback! Erro: " + e1.getMessage());
			}
		}
	}


	@Override
	public void update(Estoque estoque) {
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("UPDATE estoque SET Nome = ?, Valor = ?, CodigoBarras = ?"
					+ ", Estoque = ? WHERE id = ?");
			preencheInterrogacoesEstoque(estoque, ps);
			ps.setInt(5, estoque.getId());
			ps.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transação não foi concluida! Erro: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Erro no Rollback! Erro: " + e1.getMessage());
			}
		}
	}

	@Override
	public void deleteByCodigoBarras(Integer codigoDeBarras) {
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("DELETE FROM estoque WHERE CodigoBarras = ?");
			ps.setInt(1, codigoDeBarras);
			ps.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transação não foi concluida! Erro: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Erro no Rollback! Erro: " + e1.getMessage());
			}
		}
	}
	
	@Override
	public void updateQuantidadeEstoqueDoProduto(Integer quantidade, Integer id) {
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("UPDATE estoque SET Estoque = ? WHERE id = ?");
			ps.setInt(1, quantidade);
			ps.setInt(2, id);
			ps.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transação não foi concluida! Erro: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Erro no Rollback! Erro: " + e1.getMessage());
			}
		}
	}
	
	private Estoque pegaColunasDaTabelaEstoque(ResultSet rs) throws SQLException {
		Estoque estoque = new Estoque();
		estoque.setId(rs.getInt("Id"));
		estoque.setNome(rs.getString("Nome"));
		estoque.setValor(rs.getDouble("Valor"));
		estoque.setCodigoBarras(rs.getInt("CodigoBarras"));
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
	
	private void preencheInterrogacoesEstoque(Estoque estoque, PreparedStatement ps) throws SQLException {
		ps.setString(1, estoque.getNome());
		ps.setDouble(2, estoque.getValor());
		ps.setInt(3, estoque.getCodigoBarras());
		ps.setInt(4, estoque.getEstoque());
	}

}
