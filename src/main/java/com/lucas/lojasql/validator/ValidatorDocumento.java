package com.lucas.lojasql.validator;

import static com.lucas.lojasql.utils.Decoder.inserirPontuacoesCEP;
import static com.lucas.lojasql.utils.Decoder.inserirPontuacoesCPF;
import static com.lucas.lojasql.utils.Decoder.inserirPontuacoesRG;
import static com.lucas.lojasql.utils.Decoder.removerPontuacoes;
import static com.lucas.lojasql.validator.TipoDocumento.CEP;
import static com.lucas.lojasql.validator.TipoDocumento.CPF;
import static com.lucas.lojasql.validator.TipoDocumento.RG;

import com.lucas.lojasql.exception.DocumentException;

public class ValidatorDocumento {

	private static final int QUANTIDADE_DIGITOS_PADRAO_CEP = 8;
	private static final int QUANTIDADE_DIGITOS_PADRAO_RG = 9;
	private static final int QUANTIDADE_DIGITOS_PADRAO_CPF = 11;
	public static final String REGEX_LETRAS = ".*[a-zA-Z]+.*";

	public static String validarDocumento(String documento, String tipoDocumento) {
		if (contemPonto(documento) || contemHifen(documento)) {
			documento = removerPontuacoes(documento);
		}

		if (!contemLetra(documento)) {
			switch (documento) {
			case CPF:
				if (quantidadeDigitosDiferente(QUANTIDADE_DIGITOS_PADRAO_CPF, documento)) {
					throw new DocumentException(mensagemErroQuantidadeNumeros(CPF, QUANTIDADE_DIGITOS_PADRAO_CPF));
				}
				return inserirPontuacoesCPF(documento);
			case RG:
				if (quantidadeDigitosDiferente(QUANTIDADE_DIGITOS_PADRAO_RG, documento)) {
					throw new DocumentException(mensagemErroQuantidadeNumeros(RG, QUANTIDADE_DIGITOS_PADRAO_RG));
				}
				return inserirPontuacoesRG(documento);
			case CEP:
				if (quantidadeDigitosDiferente(QUANTIDADE_DIGITOS_PADRAO_CEP, documento)) {
					throw new DocumentException(mensagemErroQuantidadeNumeros(CEP, QUANTIDADE_DIGITOS_PADRAO_CEP));
				}
				return inserirPontuacoesCEP(documento);
			}
		}
		return documento;
	}

	private static String mensagemErroQuantidadeNumeros(String documento, Integer quantidadeumerosObrigatoria) {
		return "O " + documento + " deve conter " + quantidadeumerosObrigatoria + " numeros!";
	}

	private static boolean quantidadeDigitosDiferente(Integer quantidadeDigitosPadraoCpf, String cpf) {
		return cpf.length() != quantidadeDigitosPadraoCpf;
	}

	private static boolean contemPonto(String cpf) {
		return cpf.contains(".");
	}

	private static boolean contemHifen(String cpf) {
		return cpf.contains("-");
	}

	private static boolean contemLetra(String rg) {
		return rg.matches(REGEX_LETRAS);
	}
}
