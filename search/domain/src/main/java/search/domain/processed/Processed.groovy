package search.domain.processed

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = 'processed', type = 'processed', refreshInterval = '-1')
class Processed {

    @Id
    String uniqueId

    @Field(type = FieldType.Date, index = FieldIndex.analyzed)
    Date created = new Date()

    Processed() {
    }
}
