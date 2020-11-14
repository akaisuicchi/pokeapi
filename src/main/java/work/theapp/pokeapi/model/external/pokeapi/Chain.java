package work.theapp.pokeapi.model.external.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Chain {
    @JsonProperty(value = "evolution_details")
    private List<EvolutionDetails> evolutionDetails;
    @JsonProperty(value = "evolves_to")
    private List<Chain> evolvesTo;
    private Resource species;
}
