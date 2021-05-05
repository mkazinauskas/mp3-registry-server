package register.domain.entry.commands.jamendo.xml

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import register.domain.entry.Entries
import register.domain.entry.Entry
import register.domain.entry.SourceType

@Component
class SaveJamendoXmlEntryHandler {
    @Autowired
    private Entries entryRepository

    @Transactional
    SaveJamendoXmlEntryResponse handle(SaveJamendoXmlEntry command) {

        String cleanJson = XmlToJson.clean(command.xmlEntry)

        XmlValidator.validate(cleanJson)

        Entry entry = new Entry(
                value: cleanJson,
                sourceType: SourceType.JAMENDO_JSON
        )

        Entry savedEntry = entryRepository.save(entry)
        return new SaveJamendoXmlEntryResponse(uniqueId: savedEntry.uniqueId)
    }
}
