package search.api;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import search.api.entries.EntriesController;
import search.api.last.LastSearchesController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity<ResourceSupport> index() {
        ResourceSupport response = new ResourceSupport();
        response.add(linkTo(methodOn(IndexController.class).index()).withSelfRel());
        response.add(linkTo(methodOn(EntriesController.class)
                .entries(null))
                .withRel("entries")
        );
        response.add(linkTo(methodOn(LastSearchesController.class)
                .queries(null))
                .withRel("last-searches")
        );
        return ResponseEntity.ok(response);
    }


}
