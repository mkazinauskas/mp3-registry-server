package register.api.entries.jamendo.xml

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import register.RequestTemplate
import register.domain.entry.Entries
import register.domain.entry.Entry
import register.domain.entry.SourceType
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.BAD_REQUEST

@ContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateJamendoXmlEntriesIntegrationSpec extends Specification {

    @Autowired
    private Entries repository

    @Shared
    private File file = new File('src/test/resources/jamendo/xml/sample-jamendo-result-formatted.xml')

    @Autowired
    private RequestTemplate requestTemplate

    def 'should fail to execute request to api'() {
        when:
            ResponseEntity<String> response = requestTemplate.post(
                    '/register/entries/jamendo-xml', [:]
            )
        then:
            response.statusCode == BAD_REQUEST
            def responseJson = new JsonSlurper().parseText(response.body)
            responseJson.path == '/register/entries/jamendo-xml'
            Map<String, HashMap> errors = responseJson.errors.collectEntries { [it.field, it] }
            errors.xmlResponse.rejectedValue == null
    }

    def 'should persist jamendo xml changes to api'() {
        given:
            def request = [
                    xmlResponse: file.getText('UTF-8')
            ]
        when:
            ResponseEntity<String> response = requestTemplate.post(
                    '/register/entries/jamendo-xml',
                    request
            )
        then:
            response.statusCode == HttpStatus.CREATED
            response.body == null
            response.headers.getLocation().path.startsWith('/register/entries/')

            String uniqueId = response.headers.getLocation().path.replace('/register/entries/', '')

            Entry entry = repository.findByUniqueId(uniqueId).get()
            entry.value != null
            validateValue(entry.value)
            entry.created != null
            entry.checksum != null
            entry.sourceType == SourceType.JAMENDO_JSON
    }

    void validateValue(String value) {
        def text = new JsonSlurper().parseText(value)
        assert text.id == '501675'
        assert text.artist_name == 'roninbeat'
        assert text.license_ccurl == 'http://creativecommons.org/licenses/by-sa/3.0/'
        assert text.name == 'Test3'
    }
}