package work.theapp.pokeapi.model.external.pokeapi;

import lombok.Data;

import java.util.List;

@Data
public class Pokemon {
    private int id;
    private String name;
    private List<Ability> abilities;
    private List<Move> moves;
    private List<Type> types;
    private List<Stat> stats;
}