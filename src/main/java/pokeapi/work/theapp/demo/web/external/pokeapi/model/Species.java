package pokeapi.work.theapp.demo.web.external.pokeapi.model;

import lombok.Data;

import java.util.List;

@Data
public class Species {
    private int count;
    private String next;
    private String previous;
    private List<Resource> results;
}
