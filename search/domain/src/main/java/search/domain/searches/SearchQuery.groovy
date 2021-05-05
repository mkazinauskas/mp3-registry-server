package search.domain.searches

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = 'search_query', type = 'search_query', refreshInterval = '-1')
class SearchQuery {

    @Id
    String uniqueId = RandomStringUtils.randomAlphanumeric(40)

    @Field(type = FieldType.Date, index = FieldIndex.analyzed)
    Date created = new Date()

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    String query

    SearchQuery() {
    }

    SearchQuery(String query) {
        this.query = query
    }
}
