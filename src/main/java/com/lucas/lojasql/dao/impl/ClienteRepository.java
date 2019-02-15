package com.lucas.lojasql.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lucas.lojasql.dao.ClienteInterface;
import com.lucas.lojasql.database.DB;
import com.lucas.lojasql.database.DBException;
import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Endereco;

public class ClienteRepository implements ClienteInterface {

	private static final int COLUNA_ID = 1;
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
				clientes.add(getColunasDaTabelaCliente(rs));
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
				Cliente cliente = getColunasDaTabelaCliente(rs);
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
				Cliente cliente = getColunasDaTabelaCliente(rs);
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
			ps = conn.prepareStatement(
					"INSERT INTO cliente " + "(Nome, DataNascimento, Cpf, Rg, Email, Telefone, id_endereco) VALUES "
							+ " (?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			setaClienteDaRequisicao(cliente, ps);
			int linhasAdicionadas = ps.executeUpdate();

			if (linhasAdicionadas > 0) {
				ResultSet rs = setaIdDeTodosClientesAdicionados(cliente, ps);
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
	public void update(Cliente cliente) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE cliente SET Nome = ?, DataNascimento = ?, Cpf = ?"
					+ ", Rg = ?, Email = ?, Telefone = ?, id_endereco = ? WHERE id = ?");
			setaClienteDaRequisicao(cliente, ps);
			ps.setInt(8, cliente.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM cliente WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	private ResultSet setaIdDeTodosClientesAdicionados(Cliente cliente, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			cliente.setId(rs.getInt(COLUNA_ID));
		}
		return rs;
	}

	private Cliente getColunasDaTabelaCliente(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getInt("Id"));
		cliente.setNome(rs.getString("Nome"));
		cliente.setDataNascimento(rs.getDate("DataNascimento"));
		cliente.setCpf(rs.getString("Cpf"));
		cliente.setRg(rs.getString("Rg"));
		cliente.setEmail(rs.getString("Email"));
		cliente.setTelefone(rs.getString("Telefone"));
		cliente.setEndereco(new Endereco(rs.getString("Cep"), rs.getString("Pais"), rs.getString("Estado"),
				rs.getString("Cidade"), rs.getString("Bairro"), rs.getString("Rua"), rs.getString("Numero")));
		cliente.getEndereco().setId(rs.getInt("id_endereco"));

		return cliente;
	}

	private void setaClienteDaRequisicao(Cliente cliente, PreparedStatement ps) throws SQLException {
		ps.setString(1, cliente.getNome());
		ps.setDate(2, new java.sql.Date(cliente.getDataNascimento().getTime()));
		ps.setString(3, cliente.getCPF());
		ps.setString(4, cliente.getRG());
		ps.setString(5, cliente.getEmail());
		ps.setString(6, cliente.getTelefone());
		ps.setInt(7, cliente.getEndereco().getId());
	}

}
