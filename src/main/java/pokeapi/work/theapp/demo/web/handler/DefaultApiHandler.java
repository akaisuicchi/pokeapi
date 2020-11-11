package pokeapi.work.theapp.demo.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

public interface DefaultApiHandler {
    default ServerResponse apiGetOne(final ServerRequest request) {
        return ServerResponse.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    default ServerResponse apiGetAll(final ServerRequest request) {
        return ServerResponse.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    default ServerResponse apiPost(final ServerRequest request) {
        return ServerResponse.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    default ServerResponse apiPut(final ServerRequest request) {
        return ServerResponse.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    default ServerResponse apiPatch(final ServerRequest request) {
        return ServerResponse.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    default ServerResponse apiDelete(final ServerRequest request) {
        return ServerResponse.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
