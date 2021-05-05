package register

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class RequestTemplate {

    @Autowired
    private TestRestTemplate restTemplate

    ResponseEntity<String> post(String url, Map<String, String> request) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        HttpEntity<String> entity = new HttpEntity<String>(JsonOutput.toJson(request), headers)

        return restTemplate.postForEntity(url, entity, String)
    }


    ResponseEntity<String> get(String url) {
        HttpEntity<String> entity = new HttpEntity<String>(getHeaders())

        return restTemplate.exchange(url, HttpMethod.GET, entity, String)
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders(
                contentType: MediaType.APPLICATION_JSON
        )
        return headers
    }
}