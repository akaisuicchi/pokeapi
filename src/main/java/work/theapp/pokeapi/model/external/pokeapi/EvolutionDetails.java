package work.theapp.pokeapi.model.external.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EvolutionDetails {
    @JsonProperty(value = "min_level")
    private Integer minLevel;
    @JsonProperty(value = "min_happiness")
    private Integer minHappiness;
    private Resource item;
    private Resource trigger;
}
