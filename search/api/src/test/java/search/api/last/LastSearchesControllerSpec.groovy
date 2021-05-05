package search.api.last

import groovy.json.JsonSlurper
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import search.domain.searches.commands.CreateSearchQuery
import search.domain.searches.commands.CreateSearchQueryHandler
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LastSearchesControllerSpec extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private CreateSearchQueryHandler handler

    def 'should find last queries'() {
        given:
            String query = RandomStringUtils.randomAlphanumeric(50)
        and:
            handler.handle(new CreateSearchQuery(query))
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/last-searches?size=1', String)
        then:
            response.statusCode == HttpStatus.OK
            def body = new JsonSlurper().parseText(response.body)
            body.content.findAll { it.query == query }
    }
}
