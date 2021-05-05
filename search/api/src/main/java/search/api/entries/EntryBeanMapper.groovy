package search.api.entries

import org.springframework.stereotype.Component
import search.domain.entry.Entry

@Component
class EntryBeanMapper {

    EntryBean map(Entry entry) {
        return new EntryBean(
                id: entry.id,
                name: entry.name,
                artistName: entry.artistName,
                licenseUrl: entry.licenseUrl
        )
    }
}
