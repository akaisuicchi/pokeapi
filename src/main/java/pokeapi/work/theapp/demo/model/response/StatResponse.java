package pokeapi.work.theapp.demo.model.response;

import lombok.Data;
import pokeapi.work.theapp.demo.model.external.pokeapi.Stat;

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
