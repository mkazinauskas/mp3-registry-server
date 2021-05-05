package search.scheduled

import search.domain.entry.commands.CreateEntry
import search.register.RegisterEntryResponse
import spock.lang.Specification

class CreateEntryMapperSpec extends Specification {

    def 'should parse jamendo xml'() {
        given:
            RegisterEntryResponse response = new RegisterEntryResponse(
                    sourceType: 'JAMENDO_XML',
                    value: getClass().getResource('/data/jamendo_xml_value.xml').text
            )
        when:
            CreateEntry result = new CreateEntryMapper().map(response)
        then:
            result.name == 'My Urinary Anti Infection'
            result.artistName == 'MORE'
            result.licenseUrl == 'http://creativecommons.org/licenses/by-sa/3.0/'
            result.id == '391835'
    }

    def 'should parse jamendo json'() {
        given:
            RegisterEntryResponse response = new RegisterEntryResponse(
                    sourceType: 'JAMENDO_JSON',
                    value: getClass().getResource('/data/jamendo_json_value.json').text
            )
        when:
            CreateEntry result = new CreateEntryMapper().map(response)
        then:
            result.name == '09 Fubu - Entre Nos'
            result.artistName == 'Dj Fubu G our Fubu G'
            result.licenseUrl == 'http://creativecommons.org/licenses/by-sa/3.0/'
            result.id == '1172514'
    }
}
