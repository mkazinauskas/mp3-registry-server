package register.api.entries

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import register.RequestTemplate
import register.domain.entry.Entries
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Stepwise
class EntriesIndexPageSpec extends Specification {
    @Autowired
    private RequestTemplate requestTemplate

    @Autowired
    private Entries entries

    @Shared
    private File file1 = new File('src/test/resources/jamendo/xml/sample-jamendo-result-for-entries-retrieval.xml')

    def 'should load entries page'() {
        given:
            ResponseEntity<String> responseFile = requestTemplate.post(
                    '/register/entries/jamendo-xml',
                    [xmlResponse: file1.getText('UTF-8')]
            )
        when:
            ResponseEntity<String> response = requestTemplate.get('/register/entries?sort=id')
        then:
            response.statusCode == HttpStatus.OK
        and:
            response.body.contains(responseFile.headers.getLocation().path.split('/').last())
    }

    def 'should find entry'() {
        given:
            String uniqueId = entries.findAll().first().uniqueId
        when:
            ResponseEntity<String> response = requestTemplate.get('/register/entries/' + uniqueId)
        then:
            response.statusCode == HttpStatus.OK
        and:
            response.body.contains(uniqueId)
    }
}