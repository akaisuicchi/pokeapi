package pokeapi.work.theapp.demo.service;

import org.springframework.stereotype.Service;
import pokeapi.work.theapp.demo.model.external.pokeapi.EvolutionChain;
import pokeapi.work.theapp.demo.model.external.pokeapi.Pokemon;
import pokeapi.work.theapp.demo.model.external.pokeapi.PokemonSpecies;
import pokeapi.work.theapp.demo.model.response.PokemonResponse;
import pokeapi.work.theapp.demo.service.external.pokeapi.PokeApiService;
import pokeapi.work.theapp.demo.util.Utilities;

@Service
public class PokemonService {
    private final PokeApiService service;

    public PokemonService(PokeApiService service) {
        this.service = service;
    }

    public PokemonResponse getPokemonDetails(String id) {
        int pokemonId = Utilities.stringToInt(id);

        Pokemon pokemon = this.service.getOnePokemon(pokemonId);
        PokemonSpecies pokemonSpecies = this.service.getPokemonSpecies(pokemonId);
        EvolutionChain evolutionChain;

        PokemonResponse response = PokemonResponse.fromPokemon(pokemon);

        if (pokemonSpecies != null) {
            evolutionChain = this.service.getPokemonSpeciesEvolutionChain(pokemonSpecies.getEvolutionChain().getUrl());
            response.addEvolutionChain(evolutionChain.getChain());
        }

        return response;
    }
}
