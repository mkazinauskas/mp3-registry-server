package search.domain.searches.commands

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import search.domain.searches.SearchQueries
import search.domain.searches.SearchQuery

@Component
class CreateSearchQueryHandler {
    @Autowired
    private SearchQueries searchQueries

    @Autowired
    private CreateSearchQueryValidator validator

    @Transactional
    String handle(CreateSearchQuery createSearchQuery) {
        validator.validate(createSearchQuery)
        SearchQuery savedEntry = searchQueries.save(new SearchQuery(
                createSearchQuery.query
        ))
        return savedEntry.uniqueId
    }
}
