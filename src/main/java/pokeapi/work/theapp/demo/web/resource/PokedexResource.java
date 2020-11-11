package pokeapi.work.theapp.demo.web.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import pokeapi.work.theapp.demo.web.handler.PokedexHandler;

@Configuration
public class PokedexResource extends BaseResource {
    public static final String ENDPOINT = "/pokedex";

    public PokedexResource(PokedexHandler handler) {
        super(handler);
    }

    @Override
    public String endpoint() {
        return ENDPOINT;
    }

    @Bean
    public RouterFunction<ServerResponse> pokedexRoutes() {
        return super.resources();
    }
}
