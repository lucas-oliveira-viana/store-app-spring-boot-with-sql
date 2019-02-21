package com.lucas.lojasql.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.interfaces.CompraInterface;
import com.lucas.lojasql.jdbc.DB;
import com.lucas.lojasql.jdbc.DBException;
import com.lucas.lojasql.utils.PosicaoDasColunas;

public class CompraRepository implements CompraInterface {
	
	private Connection conn;

	public CompraRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Compra> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM compra c " + 
									   "JOIN cesta s ON s.id_compra = c.id " +
									   "JOIN estoque e ON s.id_estoque = e.id " +
									   "JOIN cliente t ON c.id_cliente = t.id " + 
									   "JOIN endereco n ON t.id_endereco = n.id " + 
									   "JOIN funcionario f ON c.id_funcionario = f.id " +
									   "JOIN endereco x ON f.id_endereco = x.id;");
			rs = ps.executeQuery();

			List<Compra> compras = new ArrayList<>();
			List<ProdutoComprado> produtosComprados = new ArrayList<>();

			pegaTodosOsProdutosCompradosDoBanco(rs, produtosComprados);

			rs.first();

			while (rs.next()) {
				compras.add(getColunasDaTabelaCompra(produtosComprados, rs));
			}

			if (compras.size() > 0) {
				return compras;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}


	@Override
	public Compra findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM compra c " + 
					"JOIN cesta s ON s.id_compra = c.id " + 
					"JOIN estoque e ON s.id_estoque = e.id " + 
					"JOIN cliente t ON c.id_cliente = t.id " + 
					"JOIN endereco n ON t.id_endereco = n.id " + 
					"JOIN funcionario f ON c.id_funcionario = f.id " + 
					"JOIN endereco x ON f.id_endereco = x.id WHERE c.id = ?;");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			List<ProdutoComprado> produtosComprados = new ArrayList<>();
			
			pegaTodosOsProdutosCompradosDoBanco(rs, produtosComprados);
			
			rs.first();
			
			while (rs.next()) {
				Compra compra = getColunasDaTabelaCompra(produtosComprados, rs);
				return compra;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public List<Compra> findByProdutoComprado(String produtoComprado) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT id_compra FROM compra c " + 
									   "JOIN cesta s ON s.id_compra = c.id " +
									   "JOIN estoque e ON s.id_estoque = e.id " + 
									   "WHERE Nome LIKE ?");
			ps.setString(1, "%" + produtoComprado + "%");
			rs = ps.executeQuery();
			
			List<Integer> idsDaTabelaCompra = new ArrayList<>();
			
			while (rs.next()) {
				idsDaTabelaCompra.add(rs.getInt("id_compra"));
			}
			
			List<Compra> compras = new ArrayList<>();
			
			for (Integer id : idsDaTabelaCompra) {
				compras.add(findById(id));
			}
			
			if (compras.size() > 0) {
				return compras;
			}
			
			return null;
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fecharConexoes(ps, rs);
		}
	}

	@Override
	public void insert(Compra compra) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO compra "
					+ "(id_cliente, id_funcionario, FormaPagamento, ValorTotal) VALUES " + " (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			preencheInterrogacoesCompra(compra, ps);
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
	
	@Override
	public void update(Compra compraAtualizada) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE compra c JOIN cesta s ON s.id_compra = c.id " + 
									   "SET id_cliente = ?, id_funcionario = ?, FormaPagamento = ?, ValorTotal = ? " + 
									   "WHERE id_compra = ?");
			preencheInterrogacoesCompra(compraAtualizada, ps);
			ps.setInt(5, compraAtualizada.getId());
			
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
			ps = conn.prepareStatement("DELETE FROM compra WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}
	
	private void preencheInterrogacoesCompra(Compra compra, PreparedStatement ps) throws SQLException {
		ps.setInt(1, compra.getCliente().getId());
		ps.setInt(2, compra.getFuncionario().getId());
		ps.setString(3, compra.getFormaPagamento());
		ps.setDouble(4, compra.getValorTotal());
	}
	
	private ResultSet setaIdDeTodasComprasAdicionadas(Compra compra, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			compra.setId(rs.getInt(PosicaoDasColunas.COLUNA_ID));
		}
		return rs;
	}

	private Compra getColunasDaTabelaCompra(List<ProdutoComprado> produtosComprados, ResultSet rs) throws SQLException {
		Compra compra = new Compra();
		compra.setId(rs.getInt(PosicaoDasColunas.COLUNA_ID_COMPRA));
		compra.setFormaPagamento(rs.getString(PosicaoDasColunas.COLUNA_FORMA_PGTO));
		compra.setValorTotal(rs.getDouble(PosicaoDasColunas.COLUNA_VALOR_TOTAL));
		compra.setProdutosComprados(produtosComprados);
		compra.setCliente(instanciaCliente(rs));
		compra.setFuncionario(instanciaFuncionario(rs));
		return compra;
	}
	
	private Cliente instanciaCliente(ResultSet rs) throws SQLException {
		return new Cliente(rs.getInt(PosicaoDasColunas.COLUNA_ID_CLIENTE),
						   rs.getString(PosicaoDasColunas.COLUNA_NOME_CLIENTE),
						   rs.getDate(PosicaoDasColunas.COLUNA_DATA_NASCIMENTO_CLIENTE),
						   rs.getString(PosicaoDasColunas.COLUNA_CPF_CLIENTE),
						   rs.getString(PosicaoDasColunas.COLUNA_RG_CLIENTE), 
						   rs.getString(PosicaoDasColunas.COLUNA_EMAIL_CLIENTE), 
						   rs.getString(PosicaoDasColunas.COLUNA_TELEFONE_CLIENTE),
						   instanciaEnderecoCliente(rs));
	}
	
	private Funcionario instanciaFuncionario(ResultSet rs) throws SQLException {
		return new Funcionario(rs.getInt(PosicaoDasColunas.COLUNA_ID_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_NOME_FUNCIONARIO),
							   rs.getDate(PosicaoDasColunas.COLUNA_DATA_NASCIMENTO_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_CPF_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_RG_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_EMAIL_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_TELEFONE_FUNCIONARIO),
							   instanciaEnderecoFuncionario(rs),
							   rs.getString(PosicaoDasColunas.COLUNA_CARGO_FUNCIONARIO));
	}
	
	
	private Endereco instanciaEnderecoCliente(ResultSet rs) throws SQLException {
		return new Endereco(rs.getString(PosicaoDasColunas.COLUNA_CEP_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_PAIS_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_ESTADO_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_CIDADE_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_BAIRRO_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_RUA_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_NUMERO_CLIENTE));
	}
	
	private Endereco instanciaEnderecoFuncionario(ResultSet rs) throws SQLException {
		return new Endereco(rs.getString(PosicaoDasColunas.COLUNA_CEP_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_PAIS_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_ESTADO_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_CIDADE_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_BAIRRO_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_RUA_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_NUMERO_FUNCIONARIO));
	}
	

	private void pegaTodosOsProdutosCompradosDoBanco(ResultSet rs, List<ProdutoComprado> produtosComprados)
			throws SQLException {
		while (rs.next()) {
			produtosComprados
					.add(new ProdutoComprado(rs.getString(PosicaoDasColunas.COLUNA_NOME_PRODUTO), 
											 rs.getInt(PosicaoDasColunas.COLUNA_ID_COMPRA), 
											 rs.getInt(PosicaoDasColunas.COLUNA_ID_ESTOQUE),
											 rs.getDouble(PosicaoDasColunas.COLUNA_VALOR_PRODUTO), 
											 rs.getInt(PosicaoDasColunas.COLUNA_CODIGO_BARRAS), 
											 rs.getInt(PosicaoDasColunas.COLUNA_QUANTIDADE_ESTOQUE)));
		}
	}
}
