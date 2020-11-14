package work.theapp.pokeapi.model.external.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PokemonSpecies {
    private int id;
    private String name;
    @JsonProperty(value = "evolution_chain")
    private Resource evolutionChain;
}
