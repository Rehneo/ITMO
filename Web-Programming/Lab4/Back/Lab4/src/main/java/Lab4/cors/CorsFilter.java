package Lab4.cors;


import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext context) throws IOException {
        context.getHeaders().add(
                "Access-Control-Allow-Origin", "*");
        context.getHeaders().add(
                "Access-Control-Allow-Credentials", "true");
        context.getHeaders().add(
                "Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization, a-token");
        context.getHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
