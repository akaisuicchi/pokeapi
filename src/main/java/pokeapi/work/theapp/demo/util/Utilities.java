package pokeapi.work.theapp.demo.util;

import java.util.stream.Stream;

public class Utilities {
    public static int stringToInt(String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        }

        try {
            return Stream.of(string).map(Integer::parseInt).findFirst().orElse(0);
        } catch (Exception e) {
            return 0;
        }
    }
}
