package com.lucas.lojasql.utils.repository;

import static com.lucas.lojasql.utils.repository.MapeamentoColunasSelectCompra.instanciaCliente;
import static com.lucas.lojasql.utils.repository.MapeamentoColunasSelectCompra.instanciaFuncionario;
import static com.lucas.lojasql.utils.repository.PosicaoDasColunas.COLUNA_ID;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.entities.ProdutoComprado;

public class MapeamentoColunas {

	public static ResultSet setIdDeCadaCompra(Compra compra, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			compra.setId(rs.getInt(COLUNA_ID));
		}
		return rs;
	}
	
	public static ResultSet setaIdDeCadaCliente(Cliente cliente, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			cliente.setId(rs.getInt(COLUNA_ID));
		}
		return rs;
	}
	
	public static ResultSet setaIdDeCadaEndereco(Endereco endereco, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			int id = rs.getInt(COLUNA_ID);
			endereco.setId(id);
		}
		return rs;
	}
	
	public static ResultSet setaIdDeCadaProduto(Estoque estoque, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			estoque.setId(rs.getInt(COLUNA_ID));
		}
		return rs;
	}
	
	public static ResultSet setaIdDeCadaFuncionario(Funcionario funcionario, PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			int id = rs.getInt(COLUNA_ID);
			funcionario.setId(id);
		}
		return rs;
	}
	
	public static Compra setColunasTabelaCompra(List<ProdutoComprado> produtosComprados, ResultSet rs) throws SQLException {
		Compra compra = new Compra();
		compra.setId(rs.getInt(PosicaoDasColunas.COLUNA_ID_COMPRA));
		compra.setFormaPagamento(rs.getString(PosicaoDasColunas.COLUNA_FORMA_PGTO));
		compra.setValorTotal(rs.getDouble(PosicaoDasColunas.COLUNA_VALOR_TOTAL));
		compra.setProdutosComprados(produtosComprados);
		compra.setCliente(instanciaCliente(rs));
		compra.setFuncionario(instanciaFuncionario(rs));
		return compra;
	}
	
	public static Cliente setColunasTabelaCliente(ResultSet rs) throws SQLException {
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
	
	public static Endereco setColunasTabelaEndereco(ResultSet rs) throws SQLException {
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
	
	public static Funcionario setColunasTabelaFuncionario(ResultSet rs) throws SQLException {
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
	
	public static Estoque setColunasTabelaEstoque(ResultSet rs) throws SQLException {
		Estoque estoque = new Estoque();
		estoque.setId(rs.getInt("Id"));
		estoque.setNome(rs.getString("Nome"));
		estoque.setValor(rs.getDouble("Valor"));
		estoque.setCodigoBarras(rs.getInt("CodigoBarras"));
		estoque.setEstoque(rs.getInt("Estoque"));
		return estoque;
	}
}
