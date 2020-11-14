package work.theapp.pokeapi.model.external.pokeapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resource {
    private String name;
    private String url;
}
