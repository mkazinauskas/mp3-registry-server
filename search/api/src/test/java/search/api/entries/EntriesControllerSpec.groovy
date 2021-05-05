package search.api.entries

import groovy.json.JsonSlurper
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import search.domain.entry.commands.CreateEntry
import search.domain.entry.commands.CreateEntryHandler
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EntriesControllerSpec extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private CreateEntryHandler handler

    def 'should work with no results'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/entries', String)
        then:
            response.statusCode == HttpStatus.OK
            def body = new JsonSlurper().parseText(response.body)
            body != null
    }

    def 'should find entries'() {
        given:
            handler.handle(new CreateEntry(
                    id: RandomStringUtils.randomAlphanumeric(40),
                    name: 'name',
                    artistName: 'artistName',
                    licenseUrl: 'licenseUrl'
            ))
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/entries', String)
        then:
            response.statusCode == HttpStatus.OK
            def body = new JsonSlurper().parseText(response.body)
            body != null
    }

    def 'should find entries by search query'() {
        given:
            handler.handle(
                    new CreateEntry(
                            id: RandomStringUtils.randomAlphanumeric(40),
                            name: 'test',
                            artistName: 'artistName',
                            licenseUrl: 'licenseUrl',
                    )
            )
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/entries/search?query=test', String)
        then:
            response.statusCode == HttpStatus.OK
            def body = new JsonSlurper().parseText(response.body)
            body != null
    }
}
