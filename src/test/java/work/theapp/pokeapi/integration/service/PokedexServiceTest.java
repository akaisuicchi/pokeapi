package work.theapp.pokeapi.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import work.theapp.pokeapi.model.response.PokedexResponse;
import work.theapp.pokeapi.service.PokedexService;
import work.theapp.pokeapi.service.external.pokeapi.PokeApiService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {PokedexService.class, PokeApiService.class})
public class PokedexServiceTest extends AbstractServiceTest {
    @Autowired
    private PokedexService service;

    @Test
    public void shouldReturnPokedexListResult() {
        String offset = "0";
        String limit = this.maxLimit + "";

        List<PokedexResponse> response = this.service.getPaginatedResult(offset, limit);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(this.maxLimit, response.size());
    }

    @Test
    public void whenOffsetGreaterThanAvailableThenReturnEmptyList() {
        String offset = "1000";
        String limit = this.maxLimit + "";

        List<PokedexResponse> response = this.service.getPaginatedResult(offset, limit);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    public void whenLimitNotSpecifiedOrLowerThenReturnMaxLimitList() {
        String offset = "0";
        String limit = "0";

        List<PokedexResponse> response = this.service.getPaginatedResult(offset, limit);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(this.maxLimit, response.size());
    }

    @Test
    public void whenFilterInvalidThenReturnList() {
        String offset = "offset";
        String limit = "limit";

        List<PokedexResponse> response = this.service.getPaginatedResult(offset, limit);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(20, response.size());
    }
}
