package com.lucas.lojasql.controller.utils;

public class Decoder {

	public static String inserirPontuacoesCPF(String cpf) {
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
	}

	public static String inserirPontuacoesRG(String rg) {
		return rg.substring(0, 2) + "." + rg.substring(2, 5) + "." + rg.substring(5, 8) + "-" + rg.substring(8);
	}

	public static String inserirPontuacoesCEP(String cep) {
		return cep.substring(0, 5) + "-" + cep.substring(5);
	}

	public static String removerPontuacoes(String documento) {
		documento = documento.replace(".", "");
		documento = documento.replace("-", "");
		return documento;
	}
}
