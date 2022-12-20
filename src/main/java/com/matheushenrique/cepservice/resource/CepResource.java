package com.matheushenrique.cepservice.resource;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.matheushenrique.cepservice.model.Endereco;
import com.matheushenrique.cepservice.service.CepService;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheManager;
import io.quarkus.cache.CacheName;
import io.quarkus.cache.CaffeineCache;

@Path("/cep")
@RequestScoped
public class CepResource {
	
	@Inject
	CepService service;
	
	@Inject
	@CacheName("cep-cache")
	Cache cache;
	
	@Inject
    CacheManager cacheManager;

    @GET
    @Path("/{cep}")
    @Produces(MediaType.APPLICATION_JSON)
    public Endereco getEndereco(@PathParam("cep") String cep) {
        return service.buscaEndereco(cep);
    }
    
    @GET
    @Path("/clearCache")
    @Produces(MediaType.TEXT_PLAIN)
    @CacheInvalidateAll(cacheName = "cep-cache")
    public String clearCache() {
        return "LIMPANDO CACHE";
    }
    
    @GET
    @Path("/getAllCacheKeys")
    @Produces(MediaType.TEXT_PLAIN)
    public Set<Object> getAllCacheKeys() {
    	return cache.as(CaffeineCache.class).keySet();
    }
    
    @GET
    @Path("/getCache")
    @Produces(MediaType.TEXT_PLAIN)
    public void getCache() {
    	Optional<Cache> cache = cacheManager.getCache("cep-cache");
        if (cache.isPresent()) {
            cache.get().invalidateAll().await().indefinitely();
        }
    }
    
    
}