package com.matheushenrique.cepservice.service;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.matheushenrique.cepservice.model.Endereco;

import io.quarkus.cache.CacheResult;

public abstract class AbstractCepService implements CepService {

	private static final Logger LOG = Logger.getLogger(AbstractCepService.class.getName());
	
	private final String dominio;
	private final WebTarget target;
	
	public AbstractCepService(String dominio) {
		this.dominio = insertTrailingSlash(dominio);
		Client client = ClientBuilder.newClient();
		this.target = client.target(dominio);
	}

	protected final String insertTrailingSlash(String path) {
		return path.endsWith("/") ? path : path + "/";
	}
	
	protected abstract String buildPath(String cep);
	
//	protected String getFullPath(String cep) {
//		return this.dominio + buildPath(cep);
//	}
	
	@Override
	@CacheResult(cacheName = "cep-cache")
	public Endereco buscaEndereco(String cep) {
		try {
            Thread.sleep(2000L); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
		
		LOG.info(
					String.format("Buscando endereço para o CEP-> %s, usando o SERVIÇO-> %s", cep, dominio)
				);
		return target.path(buildPath(cep)).request().get(Endereco.class);
	}
	
	public Endereco findByKey(String key) {
		Endereco endereco = new Endereco();
		endereco.setBairro("Setor Habitacional Vicente Pires - Trecho 3");
		endereco.setCep("72001-305");
		endereco.setComplemento("");
		endereco.setLocalidade("Brasília");
		endereco.setLogradouro("Rua 4 Chácara 25");
		endereco.setUf("DF");
		return endereco;
	}

	public String getDominio() {
		return dominio;
	}

	public WebTarget getTarget() {
		return target;
	}

}
