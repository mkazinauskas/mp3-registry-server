package search.domain.searches.commands

import com.google.common.base.Preconditions
import org.springframework.stereotype.Component
import search.domain.searches.SearchQueries

@Component
class CreateSearchQueryValidator {

    private final SearchQueries searchQueries

    CreateSearchQueryValidator(SearchQueries searchQueries) {
        this.searchQueries = searchQueries
    }

    void validate(CreateSearchQuery createSearchQuery) {
        Preconditions.checkArgument(!searchQueries.findByQuery(createSearchQuery.query).isPresent(),
                "Query `${createSearchQuery.query}` already exists")
    }
}
