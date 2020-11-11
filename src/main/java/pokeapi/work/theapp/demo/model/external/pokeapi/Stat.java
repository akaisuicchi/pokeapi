package pokeapi.work.theapp.demo.model.external.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Stat {
    @JsonProperty(value = "base_stat")
    private int baseStat;
    private Resource stat;
}
