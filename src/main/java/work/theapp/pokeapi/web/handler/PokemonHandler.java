package work.theapp.pokeapi.web.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import work.theapp.pokeapi.model.response.PokemonResponse;
import work.theapp.pokeapi.service.PokemonService;
import work.theapp.pokeapi.web.error.PokemonNotFoundException;
import work.theapp.pokeapi.web.resource.PokedexResource;

import java.net.URI;
import java.util.Collections;

import static org.springframework.web.servlet.function.ServerResponse.permanentRedirect;

@Component
public class PokemonHandler extends BaseHandler implements DefaultResourceHandler {
    private final PokemonService service;

    public PokemonHandler(PokemonService service) {
        this.service = service;
    }

    @Override
    public ServerResponse index(ServerRequest request) {
        return permanentRedirect(URI.create(PokedexResource.ENDPOINT)).build();
    }

    @Override
    public ServerResponse show(ServerRequest request) {
        String id = request.pathVariable("id");

        PokemonResponse response = this.service.getPokemonDetails(id);

        if (response == null) {
            throw new PokemonNotFoundException();
        }

        return renderView("show", Collections.singletonMap("pokemon", response));
    }

    @Override
    public String views() {
        return "pokemon";
    }
}
