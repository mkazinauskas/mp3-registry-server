package search.api

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
class IndexControllerSpec extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    def 'should show index page'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/', String)
        then:
            response.statusCode == HttpStatus.OK
            def body = new JsonSlurper().parseText(response.body)
            body != null
    }
}
