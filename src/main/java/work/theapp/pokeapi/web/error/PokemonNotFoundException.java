package work.theapp.pokeapi.web.error;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException() {
        super();
    }

    public PokemonNotFoundException(String message) {
        super(message);
    }
}
