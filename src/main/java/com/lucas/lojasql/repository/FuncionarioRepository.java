package com.lucas.lojasql.repository;

import static com.lucas.lojasql.utils.repository.MapeamentoColunas.setColunasTabelaFuncionario;
import static com.lucas.lojasql.utils.repository.MapeamentoColunas.setaIdDeCadaFuncionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.exception.db.DBException;
import com.lucas.lojasql.interfaces.FuncionarioInterface;
import com.lucas.lojasql.jdbc.DB;

public class FuncionarioRepository implements FuncionarioInterface {
	
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
				funcionarios.add(setColunasTabelaFuncionario(rs));
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
				Funcionario funcionario = setColunasTabelaFuncionario(rs);
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
				Funcionario funcionario = setColunasTabelaFuncionario(rs);
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
	public Funcionario findByRg(String rg) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = conn.prepareStatement("SELECT * FROM funcionario WHERE Rg = ?");
			ps.setString(1, rg);
			rs = ps.executeQuery();

			while (rs.next()) {
				Funcionario funcionario = setColunasTabelaFuncionario(rs);
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
				Funcionario funcionario = setColunasTabelaFuncionario(rs);
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

			conn.setAutoCommit(false);

			ps = conn.prepareStatement(
					"INSERT INTO funcionario " + "(Nome, DataNascimento, Cpf, Rg, Email, Telefone, Cargo, id_endereco) "
							+ "VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			preencheInterrogacoesFuncionario(funcionario, ps);
			int linhasAdicionadas = ps.executeUpdate();

			conn.commit();

			if (linhasAdicionadas > 0) {
				ResultSet rs = setaIdDeCadaFuncionario(funcionario, ps);
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
	public void update(Funcionario funcionario) {
		PreparedStatement ps = null;
		try {

			conn.setAutoCommit(false);

			ps = conn.prepareStatement("UPDATE funcionario SET Nome = ?, DataNascimento = ?, Cpf = ?"
					+ ", Rg = ?, Email = ?, Telefone = ?, Cargo = ?, id_endereco = ? WHERE id = ?");
			preencheInterrogacoesFuncionario(funcionario, ps);
			ps.setInt(9, funcionario.getId());
			ps.executeUpdate();

			conn.commit();

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

			conn.setAutoCommit(false);

			ps = conn.prepareStatement("DELETE FROM funcionario WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
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
}
