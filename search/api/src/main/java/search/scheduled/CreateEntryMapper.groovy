package search.scheduled

import groovy.json.JsonSlurper
import groovy.transform.PackageScope
import org.springframework.stereotype.Component
import search.domain.entry.commands.CreateEntry
import search.register.RegisterEntryResponse

@Component
@PackageScope
class CreateEntryMapper {

    CreateEntry map(RegisterEntryResponse response) {
        return new CreateEntry().with {
            if (response.sourceType == 'JAMENDO_XML') {
                parseXML(it, response.value)
            }

            if (response.sourceType == 'JAMENDO_JSON') {
                parseJSON(it, response.value)
            }

            it
        }
    }

    private void parseXML(CreateEntry createEntry, String rawXml) {
        def xml = new XmlSlurper().parseText(rawXml.replaceAll('\n', ''))
        createEntry.with {
            name = (xml.str.find { it.@name == 'name' } as String)?.trim()
            artistName = (xml.str.find { it.@name == 'artist_name' } as String)?.trim()
//            album = (xml.str.find { it.@name == 'album_name' } as String)?.trim()
//            albumId = (xml.str.find { it.@name == 'album_id' } as String)?.trim()
//            artistId = (xml.str.find { it.@name == 'artist_id' } as String)?.trim()
            licenseUrl = (xml.str.find { it.@name == 'license_ccurl' } as String)?.trim()
            id = (xml.str.find { it.@name == 'id' } as String)?.trim()
        }
    }

    private void parseJSON(CreateEntry createEntry, String rawJson) {
        def json = new JsonSlurper().parseText(rawJson)
        createEntry.with {
            name = json.name
            artistName = json.artist_name
//            album = json.album_name
//            albumId = json.album_id
//            artistId = json.artist_id
            licenseUrl = json.license_ccurl
            id = json.id
        }
    }
}
