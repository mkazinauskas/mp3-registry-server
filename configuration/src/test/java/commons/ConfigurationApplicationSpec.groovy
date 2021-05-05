package commons

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class ConfigurationApplicationSpec extends Specification {

    @Autowired
    private ApplicationContext applicationContext

    def "should load context"() {
        expect:
            applicationContext != null
    }
}
