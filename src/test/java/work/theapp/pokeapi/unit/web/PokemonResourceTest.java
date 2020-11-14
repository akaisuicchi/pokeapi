package work.theapp.pokeapi.unit.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import work.theapp.pokeapi.model.response.PokemonResponse;
import work.theapp.pokeapi.service.PokemonService;
import work.theapp.pokeapi.web.handler.PokemonHandler;
import work.theapp.pokeapi.web.resource.PokedexResource;
import work.theapp.pokeapi.web.resource.PokemonResource;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {PokemonResource.class, PokemonHandler.class})
public class PokemonResourceTest extends AbstractWebTest {
    @MockBean
    private PokemonService service;

    @Test
    public void shouldRenderRequestedPokemon() throws Exception {
        PokemonResponse mock = new PokemonResponse();
        mock.setId(150);
        mock.setName("mewtwo");

        when(this.service.getPokemonDetails(anyString())).thenReturn(mock);

        this.client
                .perform(
                        get(PokemonResource.ENDPOINT + "/{id}", mock.getId())
                                .accept(MediaType.TEXT_HTML)
                )
                .andExpect(view().name(containsString("pokemon/show")))
                .andExpect(model().attributeExists("pokemon"))
                .andExpect(content().string(containsString(mock.getName())))
        ;
    }

    @Test
    public void whenIndexPathThenRedirectToPokedex() throws Exception {
        this.client
                .perform(
                        get(PokemonResource.ENDPOINT)
                                .accept(MediaType.TEXT_HTML)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(PokedexResource.ENDPOINT))
        ;
    }

    @Test
    public void whenPokemonDoesNotExistsThenReturnNotFound() throws Exception {
        when(this.service.getPokemonDetails(anyString())).thenReturn(null);

        this.client
                .perform(
                        get(PokemonResource.ENDPOINT + "/{id}", 1000)
                                .accept(MediaType.TEXT_HTML)
                )
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void whenNotImplementedThenRedirectToError() throws Exception {
        this.client
                .perform(
                        get(PokemonResource.ENDPOINT + "/{id}", "create")
                                .accept(MediaType.TEXT_HTML)
                )
                .andExpect(status().isNotImplemented())
        ;

        this.client
                .perform(
                        post(PokemonResource.ENDPOINT)
                                .accept(MediaType.TEXT_HTML)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isNotImplemented())
        ;

        this.client
                .perform(
                        put(PokemonResource.ENDPOINT + "/{id}", "150")
                                .accept(MediaType.TEXT_HTML)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isNotImplemented())
        ;

        this.client
                .perform(
                        patch(PokemonResource.ENDPOINT + "/{id}", "150")
                                .accept(MediaType.TEXT_HTML)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isNotImplemented())
        ;

        this.client
                .perform(
                        delete(PokemonResource.ENDPOINT + "/{id}", "150")
                                .accept(MediaType.TEXT_HTML)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isNotImplemented())
        ;
    }
}
