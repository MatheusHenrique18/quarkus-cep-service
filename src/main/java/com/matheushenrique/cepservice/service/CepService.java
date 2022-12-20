package com.matheushenrique.cepservice.service;

import com.matheushenrique.cepservice.model.Endereco;

public interface CepService {

	Endereco buscaEndereco(String cep);
}
