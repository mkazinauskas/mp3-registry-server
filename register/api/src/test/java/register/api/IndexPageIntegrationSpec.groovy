package register.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
class IndexPageIntegrationSpec extends Specification {
    @Autowired
    private TestRestTemplate restTemplate

    def 'should load index page'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/', String)
        then:
            response.statusCode == HttpStatus.OK
    }
}