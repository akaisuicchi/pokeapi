package pokeapi.work.theapp.demo.web.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import pokeapi.work.theapp.demo.service.PokedexService;
import pokeapi.work.theapp.demo.web.resource.PokemonResource;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.springframework.web.servlet.function.ServerResponse.permanentRedirect;

@Component
public class PokedexHandler extends BaseHandler implements DefaultResourceHandler {
    private final PokedexService service;

    public PokedexHandler(PokedexService service) {
        this.service = service;
    }

    @Override
    public ServerResponse index(ServerRequest request) {
        String from = request.param("offset").orElse(null);
        String until = request.param("limit").orElse(null);
        String fragment = request.param("fragment").orElse(null);

        Map<String, Object> model = Collections.singletonMap("pokedex", this.service.getPaginatedResult(from, until));

        if (fragment != null) {
            return renderView("fragments/index :: list", model);
        }

        return renderView("index", model);
    }

    @Override
    public ServerResponse show(ServerRequest request) {
        String id = request.pathVariable("id");
        String uri = String.format("%s/%s", PokemonResource.ENDPOINT, id);

        return permanentRedirect(URI.create(uri)).build();
    }

    @Override
    public String views() {
        return "pokedex";
    }
}
