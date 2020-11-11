package pokeapi.work.theapp.demo.model.response;

import lombok.Data;
import pokeapi.work.theapp.demo.model.external.pokeapi.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PokemonResponse {
    private int id;
    private String name;
    private List<EvolutionChainResponse> evolutions = new ArrayList<>();
    private List<String> abilities;
    private List<String> moves;
    private List<TypeResponse> types;
    private List<StatResponse> stats;

    public static PokemonResponse fromPokemon(Pokemon pokemon) {
        if (pokemon == null) {
            return null;
        }

        List<String> abilities = pokemon.getAbilities() != null ? pokemon.getAbilities()
                .stream()
                .map(Ability::getAbility)
                .map(Resource::getName)
                .collect(Collectors.toList()) : Collections.emptyList();

        List<String> moves = pokemon.getMoves() != null ? pokemon.getMoves()
                .stream()
                .map(Move::getMove)
                .map(Resource::getName)
                .collect(Collectors.toList()) : Collections.emptyList();

        List<TypeResponse> types = pokemon.getTypes() != null ? pokemon.getTypes()
                .stream()
                .map(Type::getType)
                .map(Resource::getName)
                .map(TypeResponse::new)
                .collect(Collectors.toList()) : Collections.emptyList();

        List<StatResponse> stats = pokemon.getStats() != null ? pokemon.getStats()
                .stream()
                .map(StatResponse::fromStat)
                .collect(Collectors.toList()) : Collections.emptyList();

        PokemonResponse response = new PokemonResponse();
        response.setId(pokemon.getId());
        response.setName(pokemon.getName());
        response.setAbilities(abilities);
        response.setMoves(moves);
        response.setTypes(types);
        response.setStats(stats);

        return response;
    }

    public void addEvolutionChain(Chain chain) {
        if (chain == null) {
            return;
        }

        Resource species = chain.getSpecies();

        if (species == null) {
            return;
        }

        EvolutionChainResponse response = chain.getEvolutionDetails()
                .stream()
                .map(this::buildEvolutionChainResponse)
                .findFirst()
                .orElse(new EvolutionChainResponse());

//        TODO
        Object[] paths = Arrays.stream(species.getUrl().split("/")).filter(s -> !s.isEmpty()).toArray();
        int id = Integer.parseInt(paths[paths.length - 1].toString());

        response.setId(id);
        response.setName(species.getName());

        this.evolutions.add(response);

        if (chain.getEvolvesTo().isEmpty()) {
            return;
        }

        this.addEvolutionChain(chain.getEvolvesTo().get(0));
    }

    private EvolutionChainResponse buildEvolutionChainResponse(EvolutionDetails details) {
        EvolutionChainResponse response = new EvolutionChainResponse();
        response.setTrigger(details.getTrigger().getName());
        response.setRequiredLevel(details.getMinLevel());
        response.setMinHappiness(details.getMinHappiness());

        if (details.getItem() != null) {
            response.setItemName(details.getItem().getName());
        }

        return response;
    }
}
