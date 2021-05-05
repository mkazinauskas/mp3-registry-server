package search.domain.entry.commands

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import search.domain.entry.Entry
import search.domain.entry.EntryRepository

@Component
class CreateEntryHandler {
    @Autowired
    private EntryRepository repository

    @Autowired
    private CreateEntryValidator validator

    @Transactional
    String handle(CreateEntry createEntry) {
        validator.validate(createEntry)

        Entry existingEntry = repository.findOne(createEntry.id)

        if (existingEntry) {
            Entry savedEntry = repository.save(existingEntry.with {
                name = createEntry.name
                artistName = createEntry.artistName
                licenseUrl = createEntry.licenseUrl
                it
            })
            return savedEntry.id


        } else {
            Entry savedEntry = repository.save(new Entry(
                    id: createEntry.id,
                    name: createEntry.name,
                    artistName: createEntry.artistName,
                    licenseUrl: createEntry.licenseUrl
            ))
            return savedEntry.id
        }
    }
}
