package pokeapi.work.theapp.demo.unit.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pokeapi.work.theapp.demo.model.response.PokedexResponse;
import pokeapi.work.theapp.demo.service.PokedexService;
import pokeapi.work.theapp.demo.web.handler.PokedexHandler;
import pokeapi.work.theapp.demo.web.resource.PokedexResource;
import pokeapi.work.theapp.demo.web.resource.PokemonResource;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {PokedexResource.class, PokedexHandler.class})
public class PokedexResourceTest extends AbstractWebTest {
    @MockBean
    private PokedexService service;

    @Test
    public void shouldRenderIndexView() throws Exception {
        this.client
                .perform(
                        get(PokedexResource.ENDPOINT)
                                .accept(MediaType.TEXT_HTML)
                )
                .andExpect(view().name(containsString("pokedex/index")))
                .andExpect(model().attributeExists("pokedex"))
        ;
    }

    @Test
    public void shouldRenderFragmentView() throws Exception {
        this.client
                .perform(
                        get(PokedexResource.ENDPOINT)
                                .accept(MediaType.TEXT_HTML)
                                .queryParam("fragment", "")
                )
                .andExpect(view().name(containsString("fragments/index")))
                .andExpect(model().attributeExists("pokedex"))
        ;
    }

    @Test
    public void shouldRenderRequestedPokedex() throws Exception {
        PokedexResponse mock = new PokedexResponse();
        mock.setId(150);
        mock.setName("mewtwo");
        List<PokedexResponse> mockResponse = Collections.singletonList(mock);

        when(this.service.getPaginatedResult(any(), any())).thenReturn(mockResponse);

        this.client
                .perform(
                        get(PokedexResource.ENDPOINT)
                                .accept(MediaType.TEXT_HTML)
                )
                .andExpect(view().name(containsString("pokedex/index")))
                .andExpect(model().attributeExists("pokedex"))
                .andExpect(content().string(containsString(mock.getName())))
        ;
    }

    @Test
    public void whenAddPathIdToUrlRedirectToPokemonId() throws Exception {
        int id = 150;

        this.client
                .perform(
                        get(PokedexResource.ENDPOINT + "/{id}", id)
                                .accept(MediaType.TEXT_HTML)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(String.format("%s/%s", PokemonResource.ENDPOINT, id)))
        ;
    }

    @Test
    public void whenNotImplementedThenRedirectToError() throws Exception {
        this.client
                .perform(
                        get(PokedexResource.ENDPOINT + "/{id}", "create")
                                .accept(MediaType.TEXT_HTML)
                )
                .andExpect(status().isNotImplemented())
        ;

        this.client
                .perform(
                        post(PokedexResource.ENDPOINT)
                                .accept(MediaType.TEXT_HTML)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isNotImplemented())
        ;

        this.client
                .perform(
                        put(PokedexResource.ENDPOINT + "/{id}", "150")
                                .accept(MediaType.TEXT_HTML)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isNotImplemented())
        ;

        this.client
                .perform(
                        patch(PokedexResource.ENDPOINT + "/{id}", "150")
                                .accept(MediaType.TEXT_HTML)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isNotImplemented())
        ;

        this.client
                .perform(
                        delete(PokedexResource.ENDPOINT + "/{id}", "150")
                                .accept(MediaType.TEXT_HTML)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isNotImplemented())
        ;
    }
}
