package com.example.libraryapp.configuration;

import com.example.libraryapp.service.GoogleBooksApiExternalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class GoogleBooksApiClientConfiguration {

    @Bean
    GoogleBooksApiExternalService googleBooksApiExternalService(GoogleBooksApiConfiguration configuration) {
        RestClient restClient = RestClient.builder().baseUrl(configuration.url()).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GoogleBooksApiExternalService.class);
    }
}
