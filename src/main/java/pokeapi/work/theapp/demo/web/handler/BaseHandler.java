package pokeapi.work.theapp.demo.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.servlet.function.ServerResponse.ok;
import static org.springframework.web.servlet.function.ServerResponse.status;

@Slf4j
public abstract class BaseHandler implements DefaultResourceHandler {
    public BaseHandler() {
    }

    public abstract String views();

    public ServerResponse renderView(String view) {
        return response(ok(), view, new HashMap<>());
    }

    public ServerResponse renderView(String view, Map<String, Object> model) {
        return response(ok(), view, model);
    }

    public ServerResponse redirectView(URI uri, String view) {
        return redirectView(uri, view, new HashMap<>());
    }

    public ServerResponse redirectView(URI uri, String view, Map<String, Object> model) {
        return redirectView(HttpStatus.FOUND, uri, view, model);
    }

    public ServerResponse redirectView(HttpStatus status, URI uri, String view, Map<String, Object> model) {
        return response(
                status(status).header("Turbolinks-Location", uri.getPath()).location(uri),
                view, model);
    }

    public String resourceView(String view) {
        String trailingSlashView = view.startsWith("/") ? view.substring(1) : view;
        return String.format("%s/%s", this.views(), trailingSlashView);
    }

    private ServerResponse response(ServerResponse.BodyBuilder builder, String view, Map<String, Object> model) {
        return builder.render(resourceView(view), model);
    }

    public void validate(Validator validator, Object request, String name) {
        Errors errors = new BeanPropertyBindingResult(request, name);
        validator.validate(request, errors);

        if (errors.hasErrors()) {
            log.error(errors.getAllErrors().toString());
            log.error(errors.getFieldErrors().toString());
            throw new ServerWebInputException(errors.toString());
        }
    }
}
