package register.api.entries

import register.domain.entry.Entry
import register.domain.entry.SourceType

class EntryBean {

    long id

    Date created

    String uniqueId

    SourceType sourceType

    String value

    EntryBean(Entry entry) {
        this.id = entry.id
        this.created = entry.created
        this.uniqueId = entry.uniqueId
        this.sourceType = entry.sourceType
        this.value = entry.value
    }
}
