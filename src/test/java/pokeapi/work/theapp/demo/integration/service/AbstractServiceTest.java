package pokeapi.work.theapp.demo.integration.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pokeapi.work.theapp.demo.configuration.RestTemplateConfiguration;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "work.theapp.external.pokeapi.uri=https://pokeapi.co/api/v2",
        "work.theapp.external.pokeapi.species=/pokemon-species",
        "work.theapp.external.pokeapi.pokemon-species=/pokemon-species/{id}",
        "work.theapp.external.pokeapi.pokemon=/pokemon/{id}",
        "work.theapp.external.pokeapi.limit=20",
})
@ContextConfiguration(classes = {RestTemplateConfiguration.class})
public class AbstractServiceTest {
    public final int maxLimit = 20;
}
