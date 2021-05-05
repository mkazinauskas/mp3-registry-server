package register.domain.entry.commands.jamendo.json

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import register.domain.entry.Entries
import register.domain.entry.Entry
import register.domain.entry.SourceType

@Component
class SaveJamendoJsonEntryHandler {
    @Autowired
    private Entries entryRepository

    @Transactional
    SaveJamendoJsonEntryResponse handle(SaveJamendoJsonEntry command) {

        String json = JsonCleaner.clean(command.jsonEntry)

        JsonValidator.validate(json)

        Entry entry = new Entry(
                value: json,
                sourceType: SourceType.JAMENDO_JSON
        )

        Entry savedEntry = entryRepository.save(entry)
        return new SaveJamendoJsonEntryResponse(uniqueId: savedEntry.uniqueId)
    }
}
