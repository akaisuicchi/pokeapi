package work.theapp.pokeapi.model.response;

import lombok.Data;
import work.theapp.pokeapi.model.external.pokeapi.Stat;

@Data
public class StatResponse {
    private String name;
    private int value;

    public static StatResponse fromStat(Stat stat) {
        if (stat == null) {
            return null;
        }

        StatResponse response = new StatResponse();
        response.setName(stat.getStat().getName());
        response.setValue(stat.getBaseStat());

        return response;
    }
}
