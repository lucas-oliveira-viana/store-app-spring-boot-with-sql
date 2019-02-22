package com.lucas.lojasql.repository;

import static com.lucas.lojasql.utils.repository.MapeamentoColunas.setColunasTabelaEndereco;
import static com.lucas.lojasql.utils.repository.MapeamentoColunas.setaIdDeCadaEndereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.exception.db.DBException;
import com.lucas.lojasql.interfaces.EnderecoInterface;
import com.lucas.lojasql.jdbc.DB;

public class EnderecoRepository implements EnderecoInterface {
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
				enderecos.add(setColunasTabelaEndereco(rs));
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
	public Endereco findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM endereco WHERE Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				Endereco endereco = setColunasTabelaEndereco(rs);
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
	public Endereco findByCep(String cep) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM endereco WHERE cep = ?");
			ps.setString(1, cep);
			rs = ps.executeQuery();

			while (rs.next()) {
				Endereco endereco = setColunasTabelaEndereco(rs);
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

			preencheInterrogacoesEndereco(endereco, ps);
			int linhasAdicionadas = ps.executeUpdate();

			conn.commit();

			if (linhasAdicionadas > 0) {
				ResultSet rs = setaIdDeCadaEndereco(endereco, ps);
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
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE endereco SET Cep = ?, Pais = ?, Estado = ?"
					+ ", Cidade = ?, Bairro = ?, Rua = ?, Numero = ? WHERE id = ?");
			preencheInterrogacoesEndereco(endereco, ps);
			ps.setInt(8, endereco.getId());
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

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("DELETE FROM endereco WHERE id = ?");
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

	private void preencheInterrogacoesEndereco(Endereco endereco, PreparedStatement ps) throws SQLException {
		ps.setString(1, endereco.getCep());
		ps.setString(2, endereco.getPais());
		ps.setString(3, endereco.getEstado());
		ps.setString(4, endereco.getCidade());
		ps.setString(5, endereco.getBairro());
		ps.setString(6, endereco.getRua());
		ps.setString(7, endereco.getNumero());
	}

}
