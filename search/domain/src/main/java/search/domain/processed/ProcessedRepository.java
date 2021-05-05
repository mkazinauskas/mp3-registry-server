package search.domain.processed;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ProcessedRepository extends ElasticsearchRepository<Processed, String> {
    Optional<Processed> findLast1ByCreated();
}
