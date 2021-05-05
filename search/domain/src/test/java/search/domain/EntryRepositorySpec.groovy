package search.domain

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import search.domain.entry.EntryRepository
import search.domain.entry.commands.CreateEntry
import search.domain.entry.commands.CreateEntryHandler
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class EntryRepositorySpec extends Specification {

    @Autowired
    private EntryRepository repository

    @Autowired
    private CreateEntryHandler handler

    def 'should find saved entry by name'() {
        given:
            CreateEntry createEntry = new CreateEntry(
                    id: RandomStringUtils.randomNumeric(10),
                    name: RandomStringUtils.randomAlphabetic(10),
                    artistName: RandomStringUtils.randomAlphabetic(10),
                    licenseUrl: RandomStringUtils.randomAlphabetic(10)
            )
        when:
            String entryId = handler.handle(createEntry)
        then:
            def entry = repository.findOne(entryId)
            entry.id == createEntry.id
            entry.name == createEntry.name
            entry.artistName == createEntry.artistName
            entry.licenseUrl == createEntry.licenseUrl
    }
}
