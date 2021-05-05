package search.domain.searches;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface SearchQueries extends ElasticsearchRepository<SearchQuery, String> {
    Optional<SearchQuery> findByQuery(String query);
}
