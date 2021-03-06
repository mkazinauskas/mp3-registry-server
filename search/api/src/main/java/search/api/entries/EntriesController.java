package search.api.entries;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import search.domain.entry.Entry;
import search.domain.entry.EntryRepository;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/entries")
public class EntriesController {

    @Autowired
    private EntryRepository entries;

    @Autowired
    private EntryBeanMapper mapper;

    @Autowired
    private AsyncSearchQueryHandler searchQueryAsyncSave;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resource<Page<EntryBean>>> entries(Pageable pageable) {
        Page<Entry> foundEntries = entries.findAll(pageable);
        return ResponseEntity.ok(new Resource<>(
                foundEntries.map(mapper::map),
                linkTo(methodOn(EntriesController.class).entries(null)).withSelfRel()
        ));
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ResponseEntity<Resource<Page<EntryBean>>> searchEntries(
            @Param(value = "query") String query,
            Pageable pageable
    ) {
        BoolQueryBuilder builder =
                boolQuery()
                        .should(QueryBuilders.queryStringQuery(query).field("name"))
                        .should(QueryBuilders.queryStringQuery(query).field("artistName"));

        Page<Entry> foundEntries = entries.search(builder, pageable);
        if (foundEntries.getTotalElements() > 0) {
            searchQueryAsyncSave.save(query);
        }
        return ResponseEntity.ok(new Resource<>(
                foundEntries.map(mapper::map),
                linkTo(methodOn(EntriesController.class).entries(pageable)).withSelfRel()
        ));
    }


}
