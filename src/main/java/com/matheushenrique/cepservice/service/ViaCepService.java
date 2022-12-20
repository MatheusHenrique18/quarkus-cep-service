package com.matheushenrique.cepservice.service;

import javax.enterprise.context.Dependent;

@Dependent
public final class ViaCepService extends AbstractCepService{

	public ViaCepService() {
		super("https://viacep.com.br");
	}

	@Override
	protected String buildPath(String cep) {
		return String.format("ws/%s/json", cep);
	}

}
