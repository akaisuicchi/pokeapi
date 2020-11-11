package pokeapi.work.theapp.demo.service.external.pokeapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pokeapi.work.theapp.demo.model.external.pokeapi.EvolutionChain;
import pokeapi.work.theapp.demo.model.external.pokeapi.Pokemon;
import pokeapi.work.theapp.demo.model.external.pokeapi.PokemonSpecies;
import pokeapi.work.theapp.demo.model.external.pokeapi.Species;

import java.util.Collections;

@Service
@Slf4j
public class PokeApiService {
    @Value("${work.theapp.external.pokeapi.species}")
    private String speciesEndpoint;
    @Value("${work.theapp.external.pokeapi.pokemon-species}")
    private String pokemonSpeciesEndpoint;
    @Value("${work.theapp.external.pokeapi.pokemon}")
    private String pokemonEndpoint;
    @Value("${work.theapp.external.pokeapi.limit}")
    private int limit;

    private final RestTemplate restTemplate;

    public PokeApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Species getAllSpecies() {
        return this.getAllSpecies(0, this.limit);
    }

    public Species getAllSpecies(int offset) {
        return this.getAllSpecies(offset, this.limit);
    }

    public Species getAllSpecies(int offset, int limit) {
        int maxLimit = limit;

        if (limit > this.limit) {
            maxLimit = this.limit;
        }

        log.debug(this.speciesEndpoint);
        log.debug("offset {} limit {}", offset, maxLimit);

        try {
            ResponseEntity<Species> response = this.restTemplate
                    .exchange(String.format("%s?offset={offset}&limit={limit}", this.speciesEndpoint), HttpMethod.GET, null, Species.class, offset, maxLimit);


            if (this.isInvalidResponse(response)) {
                throw new RuntimeException("Didn't get a response");
            }

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pokemon getOnePokemon(int id) {
        try {
            ResponseEntity<Pokemon> response = this.restTemplate
                    .exchange(this.pokemonEndpoint, HttpMethod.GET, null, Pokemon.class, Collections.singletonMap("id", id));

            if (this.isInvalidResponse(response)) {
                throw new RuntimeException("Didn't get a response");
            }

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PokemonSpecies getPokemonSpecies(int id) {
        try {
            ResponseEntity<PokemonSpecies> response = this.restTemplate
                    .exchange(this.pokemonSpeciesEndpoint, HttpMethod.GET, null, PokemonSpecies.class, Collections.singletonMap("id", id));

            if (this.isInvalidResponse(response)) {
                throw new RuntimeException("Didn't get a response");
            }

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public EvolutionChain getPokemonSpeciesEvolutionChain(String url) {
        if (url == null) {
            return null;
        }

        try {
            ResponseEntity<EvolutionChain> response = this.restTemplate
                    .exchange(url, HttpMethod.GET, null, EvolutionChain.class);

            if (this.isInvalidResponse(response)) {
                throw new RuntimeException("Didn't get a response");
            }

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> boolean isInvalidResponse(ResponseEntity<T> response) {
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            log.debug("status {}", response.getStatusCodeValue());
            return true;
        }

        return false;
    }
}
