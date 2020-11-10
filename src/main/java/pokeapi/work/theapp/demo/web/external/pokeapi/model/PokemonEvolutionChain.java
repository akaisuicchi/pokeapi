package pokeapi.work.theapp.demo.web.external.pokeapi.model;

import lombok.Data;

import java.util.List;

@Data
public class PokemonEvolutionChain {
    private List<EvolutionChain> chain;
}
