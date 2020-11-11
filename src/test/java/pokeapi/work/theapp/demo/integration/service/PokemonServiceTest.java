package pokeapi.work.theapp.demo.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import pokeapi.work.theapp.demo.model.response.PokemonResponse;
import pokeapi.work.theapp.demo.service.PokemonService;
import pokeapi.work.theapp.demo.service.external.pokeapi.PokeApiService;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {PokemonService.class, PokeApiService.class})
public class PokemonServiceTest extends AbstractServiceTest {
    @Autowired
    private PokemonService service;

    @Test
    public void shouldReturnPokemonDetails() {
        String id = "25";
        String name = "pikachu";

        PokemonResponse response = this.service.getPokemonDetails(id);

        assertNotNull(response);
        assertEquals(name, response.getName().toLowerCase());
        assertFalse(response.getAbilities().isEmpty());
        assertEquals(id, response.getId() + "");
        assertFalse(response.getEvolutions().isEmpty());
        assertFalse(response.getMoves().isEmpty());
        assertFalse(response.getTypes().isEmpty());
        assertFalse(response.getStats().isEmpty());
    }

    @Test
    public void shouldReturnPokemonWithTwoEvolutions() {
        String id = "1";
        String name = "bulbasaur";

        PokemonResponse response = this.service.getPokemonDetails(id);

        assertNotNull(response);
        assertEquals(name, response.getName().toLowerCase());
        assertFalse(response.getAbilities().isEmpty());
        assertEquals(id, response.getId() + "");
        assertFalse(response.getEvolutions().isEmpty());
        assertEquals(3, response.getEvolutions().size());
        assertFalse(response.getMoves().isEmpty());
        assertFalse(response.getTypes().isEmpty());
        assertFalse(response.getStats().isEmpty());
    }

    @Test
    public void whenIdDoesNotExistThenReturnNull() {
        String id = "1000";

        PokemonResponse response = this.service.getPokemonDetails(id);

        assertNull(response);
    }
}
