package search.domain.entry

import org.hibernate.validator.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = 'entry', type = 'entry', refreshInterval = '-1')
class Entry {

    @Id
    String id

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    String name

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    String artistName

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    String licenseUrl

    Entry() {
    }
}
