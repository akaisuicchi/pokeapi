package work.theapp.pokeapi.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import work.theapp.pokeapi.model.external.pokeapi.EvolutionChain;
import work.theapp.pokeapi.model.external.pokeapi.Pokemon;
import work.theapp.pokeapi.model.external.pokeapi.PokemonSpecies;
import work.theapp.pokeapi.model.external.pokeapi.Species;
import work.theapp.pokeapi.service.external.pokeapi.PokeApiService;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {PokeApiService.class})
public class PokeApiServiceTest extends AbstractServiceTest {
    @Autowired
    private PokeApiService service;

    @Test
    public void shouldReturnFirstBatchOfSpecies() {
        Species species = this.service.getAllSpecies();

        assertNotNull(species);
        assertNull(species.getPrevious());
        assertEquals(this.maxLimit, species.getResults().size());
    }

    @Test
    public void shouldReturnNextBatchOfSpecies() {
        Species species = this.service.getAllSpecies(this.maxLimit);

        assertNotNull(species);
        assertNotNull(species.getPrevious());
        assertEquals(this.maxLimit, species.getResults().size());
    }

    @Test
    public void shouldReturnMaxLimitSpecies() {
        Species species = this.service.getAllSpecies(0, 40);

        assertNotNull(species);
        assertNull(species.getPrevious());
        assertEquals(this.maxLimit, species.getResults().size());
    }

    @Test
    public void shouldReturnRequestedPokemonById() {
        int id = 150;
        String name = "mewtwo";

        Pokemon pokemon = this.service.getOnePokemon(id);

        assertNotNull(pokemon);
        assertEquals(id, pokemon.getId());
        assertEquals(name, pokemon.getName().toLowerCase());
    }

    @Test
    public void shouldReturnRequestedPokemonSpecies() {
        int id = 150;
        String name = "mewtwo";

        PokemonSpecies pokemonSpecies = this.service.getPokemonSpecies(id);

        assertNotNull(pokemonSpecies);
        assertEquals(id, pokemonSpecies.getId());
        assertEquals(name, pokemonSpecies.getName());
        assertNotNull(pokemonSpecies.getEvolutionChain());
        assertNotNull(pokemonSpecies.getEvolutionChain().getUrl());
    }

    @Test
    public void shouldReturnRequestedPokemonEvolutionChain() {
        int id = 1;

        PokemonSpecies pokemonSpecies = this.service.getPokemonSpecies(id);
        EvolutionChain evolutionChain =  this.service.getPokemonSpeciesEvolutionChain(pokemonSpecies.getEvolutionChain().getUrl());

        assertNotNull(evolutionChain);
        assertNotNull(evolutionChain.getChain());
        assertFalse(evolutionChain.getChain().getEvolvesTo().isEmpty());
    }

    @Test
    public void whenLimitLowerThanZeroThenReturnMaxLimit() {
        Species species = this.service.getAllSpecies(0, 0);

        assertNotNull(species);
        assertNull(species.getPrevious());
        assertEquals(this.maxLimit, species.getResults().size());
    }

    @Test
    public void whenOffsetGreaterThanAvailableThenReturnEmptyResults() {
        Species species = this.service.getAllSpecies(1000);

        assertNotNull(species);
        assertTrue(species.getResults().isEmpty());
        assertNull(species.getNext());
        assertNotNull(species.getPrevious());
    }

    @Test
    public void whenPokemonByIdDoesNotExistsThenReturnNull() {
        Pokemon pokemon = this.service.getOnePokemon(1000);

        assertNull(pokemon);
    }

    @Test
    public void whenIncomplete8thGenerationPokemonThenShouldSafelyReturnPokemon() {
        PokemonSpecies pokemonSpecies = this.service.getPokemonSpecies(890);

        assertNotNull(pokemonSpecies);
        assertNull(pokemonSpecies.getEvolutionChain());
    }

    @Test
    public void whenSpeciesByIdDoesNotExistsThenReturnNull() {
        PokemonSpecies pokemonSpecies = this.service.getPokemonSpecies(1000);

        assertNull(pokemonSpecies);
    }
}
