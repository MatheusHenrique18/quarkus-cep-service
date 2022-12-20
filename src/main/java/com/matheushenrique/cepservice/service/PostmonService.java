package com.matheushenrique.cepservice.service;

import javax.enterprise.context.Dependent;

//@Dependent
public class PostmonService extends AbstractCepService{

	public PostmonService() {
		super("https://api.postmon.com.br");
	}

	@Override
	protected String buildPath(String cep) {
		return String.format("v1/cep/%s", cep);
	}

}
