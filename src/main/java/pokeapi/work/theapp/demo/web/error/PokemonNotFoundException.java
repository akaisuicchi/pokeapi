package pokeapi.work.theapp.demo.web.error;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException() {
        super();
    }

    public PokemonNotFoundException(String message) {
        super(message);
    }
}
