package search.domain.entry;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EntryRepository extends ElasticsearchRepository<Entry, String> {
}
