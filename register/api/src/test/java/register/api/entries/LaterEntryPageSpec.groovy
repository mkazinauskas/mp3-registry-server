package register.api.entries

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import register.RequestTemplate
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
class LaterEntryPageSpec extends Specification {
    @Autowired
    private RequestTemplate requestTemplate

    private File currentFile = new File('src/test/resources/jamendo/xml/sample-jamendo-result-for-later-entry-1.xml')

    private File laterFile = new File('src/test/resources/jamendo/xml/sample-jamendo-result-for-later-entry-2.xml')

    def 'should load later entry'() {
        given:
            ResponseEntity<String> currentFile = requestTemplate.post(
                    '/register/entries/jamendo-xml',
                    [xmlResponse: this.currentFile.getText('UTF-8')]
            )
            String currentFileUniqueId = currentFile.headers.getLocation().path.split('/').last()
            assert currentFileUniqueId != null
        and:
            ResponseEntity<String> laterFile = requestTemplate.post(
                    '/register/entries/jamendo-xml',
                    [xmlResponse: this.laterFile.getText('UTF-8')]
            )
        and:
            String laterFileUniqueId = laterFile.headers.getLocation().path.split('/').last()
            assert laterFileUniqueId != null
        when:
            ResponseEntity<String> response = requestTemplate
                    .get('/register/entries/later?uniqueId=' + currentFileUniqueId)
        then:
            response.statusCode == HttpStatus.OK
        and:
            response.body.contains(laterFileUniqueId)
            log.info('Later file: \n' + new JsonSlurper().parseText(response.body).toString())
    }

    def 'should not fail for entry'() {
        when:
            ResponseEntity<String> response = requestTemplate
                    .get('/register/entries/later?uniqueId=' + 'xxxxxxx')
        then:
            response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
    }

}
