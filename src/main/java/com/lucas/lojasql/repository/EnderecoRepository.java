package com.lucas.lojasql.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.interfaces.EnderecoInterface;
import com.lucas.lojasql.jdbc.DB;
import com.lucas.lojasql.jdbc.DBException;

public class EnderecoRepository implements EnderecoInterface {

	private static final int COLUNA_ID = 1;
	private Connection conn;

	public EnderecoRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Endereco> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM endereco");
			rs = ps.executeQuery();

			List<Endereco> enderecos = new ArrayList<>();

			while (rs.next()) {
				enderecos.add(pegaColunasDaTabelaEndereco(rs));
			}

			if (enderecos.size() > 0) {
				return enderecos;
			}
			throw new DBException("Enderecos não encontrados");
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public Endereco findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Endereco findByCep(String cep) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM endereco WHERE cep = ?");
			ps.setString(1, cep);
			rs = ps.executeQuery();

			while (rs.next()) {
				Endereco endereco = pegaColunasDaTabelaEndereco(rs);
				return endereco;
			}
			throw new DBException("Erro ao encontrar endereco!");
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public void insert(Endereco endereco) {
		PreparedStatement ps = null;
		try {

			conn.setAutoCommit(false);

			ps = conn.prepareStatement("INSERT INTO endereco (Cep, Pais, Estado, Cidade, Bairro, Rua, Numero) VALUES "
					+ " (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			preecheInterrogacoesEndereco(endereco, ps);
			int linhasAdicionadas = ps.executeUpdate();

			conn.commit();

			if (linhasAdicionadas > 0) {
				ResultSet rs = setaIdDeTodosAdicionados(endereco, ps);
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
	public void update(Endereco endereco) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub

	}

	private Endereco pegaColunasDaTabelaEndereco(ResultSet rs) throws SQLException {
		Endereco endereco = new Endereco();
		endereco.setId(rs.getInt("Id"));
		endereco.setCep(rs.getString("Cep"));
		endereco.setPais(rs.getString("Pais"));
		endereco.setEstado(rs.getString("Estado"));
		endereco.setCidade(rs.getString("Cidade"));
		endereco.setBairro(rs.getString("Bairro"));
		endereco.setRua(rs.getString("Rua"));
		endereco.setNumero(rs.getString("Numero"));
		return endereco;
	}

	private ResultSet setaIdDeTodosAdicionados(Endereco endereco, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			int id = rs.getInt(COLUNA_ID);
			endereco.setId(id);
		}
		return rs;
	}

	private void preecheInterrogacoesEndereco(Endereco endereco, PreparedStatement ps) throws SQLException {
		ps.setString(1, endereco.getCep());
		ps.setString(2, endereco.getPais());
		ps.setString(3, endereco.getEstado());
		ps.setString(4, endereco.getCidade());
		ps.setString(5, endereco.getBairro());
		ps.setString(6, endereco.getRua());
		ps.setString(7, endereco.getNumero());
	}

}
