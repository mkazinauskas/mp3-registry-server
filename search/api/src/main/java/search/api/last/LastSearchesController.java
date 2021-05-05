package search.api.last;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import search.domain.searches.SearchQueries;
import search.domain.searches.SearchQuery;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/last-searches")
public class LastSearchesController {

    @Autowired
    private SearchQueries queries;

    @Autowired
    private SearchQueryBeanMapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resource<Page<SearchQueryBean>>> queries(Pageable pageable) {
        Page<SearchQuery> foundEntries = queries.findAll(
                new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "created")
        );
        return ResponseEntity.ok(new Resource<>(
                foundEntries.map(mapper::map),
                linkTo(methodOn(LastSearchesController.class).queries(pageable)).withSelfRel()
        ));
    }
}
