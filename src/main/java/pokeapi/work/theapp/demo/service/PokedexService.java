package pokeapi.work.theapp.demo.service;

import org.springframework.stereotype.Service;
import pokeapi.work.theapp.demo.model.response.PokedexResponse;
import pokeapi.work.theapp.demo.service.external.pokeapi.PokeApiService;
import pokeapi.work.theapp.demo.util.Utilities;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PokedexService {
    private final PokeApiService service;

    public PokedexService(PokeApiService service) {
        this.service = service;
    }

    public List<PokedexResponse> getPaginatedResult(String from, String until) {
        int fromIndex = Utilities.stringToInt(from);
        int limit = Utilities.stringToInt(until);

        return this.service.getAllSpecies(fromIndex, limit)
                .getResults()
                .stream()
                .map(PokedexResponse::fromSpecies)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
