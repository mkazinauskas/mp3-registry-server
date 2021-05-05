package search.api.last

import com.fasterxml.jackson.annotation.JsonFormat

class SearchQueryBean {
    String id

    @JsonFormat(pattern = 'yyyy-MM-dd HH:mm:ss')
    Date created

    String query
}
