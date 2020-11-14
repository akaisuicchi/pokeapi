package work.theapp.pokeapi.model.external.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Ability {
    private Resource ability;
    @JsonProperty(value = "is_hidden")
    private boolean isHidden;
}
