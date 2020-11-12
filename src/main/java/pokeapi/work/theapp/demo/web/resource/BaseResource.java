package pokeapi.work.theapp.demo.web.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import pokeapi.work.theapp.demo.web.error.PokemonNotFoundException;
import pokeapi.work.theapp.demo.web.handler.DefaultResourceHandler;

import java.util.Collections;
import java.util.function.BiFunction;

import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;
import static org.springframework.web.servlet.function.ServerResponse.*;
import static org.springframework.web.servlet.function.ServerResponse.badRequest;

public abstract class BaseResource {
    protected final DefaultResourceHandler handler;

    public BaseResource(DefaultResourceHandler handler) {
        this.handler = handler;
    }

    public abstract String endpoint();

    public RouterFunction<ServerResponse> resources() {
        var api = RouterFunctions
                .route()
                .GET(this.endpoint(), handler::apiGetAll)
                .POST(this.endpoint(), contentType(MediaType.APPLICATION_JSON), handler::apiPost)
                .path(this.endpoint(),
                        builder -> builder
                                .GET("/{id}", handler::apiGetOne)
                                .PUT("/{id}", contentType(MediaType.APPLICATION_JSON), handler::apiPut)
                                .PATCH("/{id}", contentType(MediaType.APPLICATION_JSON), handler::apiPatch)
                                .DELETE("/{id}", contentType(MediaType.APPLICATION_JSON), handler::apiDelete)
                )
                .build();

        var server = RouterFunctions
                .route()
                .GET(this.endpoint(), accept(MediaType.TEXT_HTML), handler::index)
                .POST(this.endpoint(), accept(MediaType.TEXT_HTML).and(contentType(MediaType.APPLICATION_FORM_URLENCODED)), handler::store)
                .path(this.endpoint(),
                        builder -> builder
                                .GET("/create", accept(MediaType.TEXT_HTML), handler::create)
                                .GET("/{id}", accept(MediaType.TEXT_HTML), handler::show)
                                .GET("/{id}/edit", accept(MediaType.TEXT_HTML), handler::edit)
                                .PUT("/{id}", accept(MediaType.TEXT_HTML).and(contentType(MediaType.APPLICATION_FORM_URLENCODED)), handler::update)
                                .PATCH("/{id}", accept(MediaType.TEXT_HTML).and(contentType(MediaType.APPLICATION_FORM_URLENCODED)), handler::update)
                                .DELETE("/{id}", accept(MediaType.TEXT_HTML).and(contentType(MediaType.APPLICATION_FORM_URLENCODED)), handler::destroy)
                )
                .onError(e -> e instanceof PokemonNotFoundException, onPokemonNotFoundException())
                .build();

        return RouterFunctions
                .route()
                .add(server)
                .add(api)
                .build();
    }

    private BiFunction<Throwable, ServerRequest, ServerResponse> onPokemonNotFoundException() {
        return (throwable, request) -> status(HttpStatus.NOT_FOUND)
                .render("error/404", Collections.singletonMap("id", request.pathVariable("id")));
    }
}
