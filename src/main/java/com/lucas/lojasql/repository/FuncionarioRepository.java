package com.lucas.lojasql.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.interfaces.FuncionarioInterface;
import com.lucas.lojasql.jdbc.DB;
import com.lucas.lojasql.jdbc.DBException;

public class FuncionarioRepository implements FuncionarioInterface {

	private static final int COLUNA_ID = 1;
	private Connection conn;

	public FuncionarioRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Funcionario> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM funcionario f JOIN endereco e ON f.id_endereco = e.id");
			rs = ps.executeQuery();

			List<Funcionario> funcionarios = new ArrayList<>();

			while (rs.next()) {
				funcionarios.add(getColunasDaTabelaFuncionario(rs));
			}
			if (funcionarios.size() > 0) {
				return funcionarios;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public Funcionario findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM funcionario WHERE Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				Funcionario funcionario = getColunasDaTabelaFuncionario(rs);
				return funcionario;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}
	
	@Override
	public Funcionario findByCpf(String cpf) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT * FROM funcionario c JOIN endereco e ON c.id_endereco = e.id WHERE Cpf = ?");
			ps.setString(1, cpf);
			rs = ps.executeQuery();

			while (rs.next()) {
				Funcionario funcionario = getColunasDaTabelaFuncionario(rs);
				return funcionario;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public void insert(Funcionario funcionario) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO funcionario " + "(Nome, DataNascimento, Cpf, Rg, Email, Telefone, Cargo, id_endereco) "
							+ "VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			preencheInterrogacoesFuncionario(funcionario, ps);
			int linhasAdicionadas = ps.executeUpdate();

			if (linhasAdicionadas > 0) {
				ResultSet rs = setaIdDeTodosAdicionados(funcionario, ps);
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

	private ResultSet setaIdDeTodosAdicionados(Funcionario funcionario, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			int id = rs.getInt(COLUNA_ID);
			funcionario.setId(id);
		}
		return rs;
	}

	@Override
	public void update(Funcionario funcionario) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE funcionario SET Nome = ?, DataNascimento = ?, Cpf = ?"
					+ ", Rg = ?, Email = ?, Telefone = ?, Cargo = ?, id_endereco = ? WHERE id = ?");
			preencheInterrogacoesFuncionario(funcionario, ps);
			ps.setInt(9, funcionario.getId());
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
			ps = conn.prepareStatement("DELETE FROM funcionario WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	private Funcionario getColunasDaTabelaFuncionario(ResultSet rs) throws SQLException {
		Funcionario funcionario = new Funcionario();
		funcionario.setId(rs.getInt("Id"));
		funcionario.setNome(rs.getString("Nome"));
		funcionario.setDataNascimento(rs.getDate("DataNascimento"));
		funcionario.setCpf(rs.getString("Cpf"));
		funcionario.setRg(rs.getString("Rg"));
		funcionario.setEmail(rs.getString("Email"));
		funcionario.setTelefone(rs.getString("Telefone"));
		funcionario.setEndereco(new Endereco(rs.getString("Cep"), rs.getString("Pais"), rs.getString("Estado"),
				rs.getString("Cidade"), rs.getString("Bairro"), rs.getString("Rua"), rs.getString("Numero")));
		funcionario.getEndereco().setId(rs.getInt("id_endereco"));
		funcionario.setCargo(rs.getString("Cargo"));

		return funcionario;
	}

	private void preencheInterrogacoesFuncionario(Funcionario funcionario, PreparedStatement ps) throws SQLException {
		ps.setString(1, funcionario.getNome());
		ps.setDate(2, new java.sql.Date(funcionario.getDataNascimento().getTime()));
		ps.setString(3, funcionario.getCPF());
		ps.setString(4, funcionario.getRG());
		ps.setString(5, funcionario.getEmail());
		ps.setString(6, funcionario.getTelefone());
		ps.setString(7, funcionario.getCargo());
		ps.setInt(8, funcionario.getEndereco().getId());
	}

	@Override
	public Funcionario findByRg(String rg) {
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = conn.prepareStatement("SELECT * FROM funcionario WHERE Rg = ?");
				ps.setString(1, rg);
				rs = ps.executeQuery();

				while (rs.next()) {
					Funcionario funcionario = getColunasDaTabelaFuncionario(rs);
					return funcionario;
				}
				return null;
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			} finally {
				DB.fecharConexoes(ps, rs);
			}
		}

	@Override
	public Funcionario findByEmail(String email) {
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = conn.prepareStatement("SELECT * FROM funcionario WHERE Email = ?");
				ps.setString(1, email);
				rs = ps.executeQuery();

				while (rs.next()) {
					Funcionario funcionario = getColunasDaTabelaFuncionario(rs);
					return funcionario;
				}
				return null;
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			} finally {
				DB.fecharConexoes(ps, rs);
			}
		}

}
