package work.theapp.pokeapi.model.response;

import lombok.Data;
import work.theapp.pokeapi.model.external.pokeapi.Resource;

import java.util.Arrays;

@Data
public class PokedexResponse {
    private int id;
    private String name;

    public static PokedexResponse fromSpecies(Resource resource) {
        if (resource.getName() == null || resource.getUrl() == null) {
            return null;
        }

//        TODO
        Object[] paths = Arrays.stream(resource.getUrl().split("/")).filter(s -> !s.isEmpty()).toArray();
        int id = Integer.parseInt(paths[paths.length - 1].toString());

        PokedexResponse response = new PokedexResponse();
        response.setName(resource.getName());
        response.setId(id);

        return response;
    }
}
