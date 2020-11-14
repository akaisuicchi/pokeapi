package work.theapp.pokeapi.service;

import org.springframework.stereotype.Service;
import work.theapp.pokeapi.model.response.PokedexResponse;
import work.theapp.pokeapi.service.external.pokeapi.PokeApiService;
import work.theapp.pokeapi.util.Utilities;

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
