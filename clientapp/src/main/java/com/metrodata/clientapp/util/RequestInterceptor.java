package com.metrodata.clientapp.util;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class RequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!request.getURI().getPath().equals("/login")) {
            request.getHeaders().add("Authorization", "Basic " + BasicHeader.createBasicToken(
                authentication.getPrincipal().toString(), 
                authentication.getCredentials().toString()));
        }
        ClientHttpResponse clientHttpResponse = execution.execute(request, body);
        return clientHttpResponse;
    }
    
}
