package pokeapi.work.theapp.demo.model.external.pokeapi;

import lombok.Data;

import java.util.List;

@Data
public class Species {
    private int count;
    private String next;
    private String previous;
    private List<Resource> results;
}
