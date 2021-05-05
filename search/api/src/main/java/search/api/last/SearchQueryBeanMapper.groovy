package search.api.last

import org.springframework.stereotype.Component
import search.domain.searches.SearchQuery

@Component
class SearchQueryBeanMapper {

    SearchQueryBean map(SearchQuery searchQuery) {
        return new SearchQueryBean(
                id: searchQuery.uniqueId,
                created: searchQuery.created,
                query: searchQuery.query
        )
    }
}
