package work.theapp.pokeapi.web.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

@Configuration
public class IndexResource {
    @Bean
    public RouterFunction<ServerResponse> routes() {
        var server = RouterFunctions
                .route()
                .GET("/", (ServerRequest request) -> ServerResponse.temporaryRedirect(URI.create(PokedexResource.ENDPOINT)).build())
                .build();

        return RouterFunctions
                .route()
                .add(server)
                .build();
    }
}
