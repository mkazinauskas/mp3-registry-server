package register.domain.entry.commands.oldest

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import register.domain.entry.Entries
import register.domain.entry.Entry

@Component
class GetOldestEntryHandler {
    private final Entries entries

    GetOldestEntryHandler(Entries entries) {
        this.entries = entries
    }

    @Transactional(readOnly = false)
    GetOldestEntryResponse handle(GetOldestEntry command) {
        Page<Entry> foundData = entries.findAll(new PageRequest(0, 1, new Sort(new Sort.Order("created"))))
        if (foundData.getContent()) {
            return new GetOldestEntryResponse(Optional.of(foundData.getContent().first()))
        }
        return new GetOldestEntryResponse(Optional.empty())
    }
}
