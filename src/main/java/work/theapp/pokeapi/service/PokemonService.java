package work.theapp.pokeapi.service;

import org.springframework.stereotype.Service;
import work.theapp.pokeapi.model.external.pokeapi.EvolutionChain;
import work.theapp.pokeapi.model.external.pokeapi.Pokemon;
import work.theapp.pokeapi.model.external.pokeapi.PokemonSpecies;
import work.theapp.pokeapi.model.response.PokemonResponse;
import work.theapp.pokeapi.service.external.pokeapi.PokeApiService;
import work.theapp.pokeapi.util.Utilities;

@Service
public class PokemonService {
    private final PokeApiService service;

    public PokemonService(PokeApiService service) {
        this.service = service;
    }

    public PokemonResponse getPokemonDetails(String id) {
        int pokemonId = Utilities.stringToInt(id);

        Pokemon pokemon = this.service.getOnePokemon(pokemonId);

        if (pokemon == null) {
            return null;
        }

        PokemonSpecies pokemonSpecies = this.service.getPokemonSpecies(pokemonId);
        EvolutionChain evolutionChain;

        PokemonResponse response = PokemonResponse.fromPokemon(pokemon);

        if (pokemonSpecies != null && pokemonSpecies.getEvolutionChain() != null) {
            evolutionChain = this.service.getPokemonSpeciesEvolutionChain(pokemonSpecies.getEvolutionChain().getUrl());
            response.addEvolutionChain(evolutionChain.getChain());
        }

        return response;
    }
}
