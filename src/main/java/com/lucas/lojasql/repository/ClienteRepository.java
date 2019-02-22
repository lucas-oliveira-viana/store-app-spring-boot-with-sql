package com.lucas.lojasql.repository;

import static com.lucas.lojasql.utils.repository.MapeamentoColunas.setColunasTabelaCliente;
import static com.lucas.lojasql.utils.repository.MapeamentoColunas.setaIdDeCadaCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.exception.db.DBException;
import com.lucas.lojasql.interfaces.ClienteInterface;
import com.lucas.lojasql.jdbc.DB;
import com.lucas.lojasql.utils.repository.MapeamentoColunas;

public class ClienteRepository implements ClienteInterface {
	
	private Connection conn;

	public ClienteRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Cliente> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM cliente c JOIN endereco e ON c.id_endereco = e.id");
			rs = ps.executeQuery();

			List<Cliente> clientes = new ArrayList<>();

			while (rs.next()) {
				clientes.add(setColunasTabelaCliente(rs));
			}

			if (clientes.size() > 0) {
				return clientes;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public Cliente findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM cliente WHERE Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = setColunasTabelaCliente(rs);
				return cliente;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public Cliente findByCpf(String cpf) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM cliente c JOIN endereco e ON c.id_endereco = e.id WHERE Cpf = ?");
			ps.setString(1, cpf);
			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = setColunasTabelaCliente(rs);
				return cliente;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}
	
	@Override
	public Cliente findByRg(String rg) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM cliente c JOIN endereco e ON c.id_endereco = e.id WHERE Rg = ?");
			ps.setString(1, rg);
			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = setColunasTabelaCliente(rs);
				return cliente;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public Cliente findByEmail(String email) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT * FROM cliente c JOIN endereco e ON c.id_endereco = e.id WHERE Email = ?");
			ps.setString(1, email);
			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = MapeamentoColunas.setColunasTabelaCliente(rs);
				return cliente;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public void insert(Cliente cliente) {
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement(
					"INSERT INTO cliente " + "(Nome, DataNascimento, Cpf, Rg, Email, Telefone, id_endereco) VALUES "
							+ " (?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			preencheInterrogacoesCliente(cliente, ps);
			int linhasAdicionadas = ps.executeUpdate();
			
			conn.commit();

			if (linhasAdicionadas > 0) {
				ResultSet rs = setaIdDeCadaCliente(cliente, ps);
				DB.closeResultSet(rs);
			} else {
				throw new DBException("Erro inesperado! Nenhuma linha adicionada!");
			}
		}  catch (SQLException e) {
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
	public void update(Cliente cliente) {
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE cliente SET Nome = ?, DataNascimento = ?, Cpf = ?"
					+ ", Rg = ?, Email = ?, Telefone = ?, id_endereco = ? WHERE id = ?");
			preencheInterrogacoesCliente(cliente, ps);
			ps.setInt(8, cliente.getId());
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
			
			ps = conn.prepareStatement("DELETE FROM cliente WHERE id = ?");
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

	private void preencheInterrogacoesCliente(Cliente cliente, PreparedStatement ps) throws SQLException {
		ps.setString(1, cliente.getNome());
		ps.setDate(2, new java.sql.Date(cliente.getDataNascimento().getTime()));
		ps.setString(3, cliente.getCPF());
		ps.setString(4, cliente.getRG());
		ps.setString(5, cliente.getEmail());
		ps.setString(6, cliente.getTelefone());
		ps.setInt(7, cliente.getEndereco().getId());
	}
}
