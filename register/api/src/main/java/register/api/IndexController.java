package register.api;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import register.api.entries.EntriesController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class IndexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ResourceSupport> getIndex() {
        ResourceSupport response = new ResourceSupport();
        response.add(linkTo(methodOn(IndexController.class).getIndex()).withSelfRel());
        response.add(linkTo(methodOn(EntriesController.class)
                .getEntries(null))
                .withRel("entries")
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
