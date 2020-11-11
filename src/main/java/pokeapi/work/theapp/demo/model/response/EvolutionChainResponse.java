package pokeapi.work.theapp.demo.model.response;

import lombok.Data;

@Data
public class EvolutionChainResponse {
    private int id;
    private String name;
    private Integer requiredLevel;
    private Integer minHappiness;
    private String itemName;
    private String trigger;
}
