package search.register

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate

@Component
class DefaultRegisterResource implements RegisterResource {
    @Autowired
    private RestTemplate restTemplate

    @Value('${register.url}')
    private String url

    @Override
    RegisterEntryResponse latest(@RequestParam('uniqueId') String uniqueId) {
        ResponseEntity<String> response =
                restTemplate.getForEntity(
                        "${url}/register/queries/later?uniqueId=" + uniqueId,
                        String
                )

        return new JsonSlurper().parseText(response.getBody()).with { res ->
            new RegisterEntryResponse(
                    id: res.id as long,
                    created: new Date(res.created),
                    uniqueId: res.uniqueId,
                    sourceType: res.sourceType,
                    value: res.value
            )
        }
    }

    @Override
    RegisterEntryResponse first() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(
                        "${url}/register/queries",
                        String
                )

        return new JsonSlurper().parseText(response.getBody()).content.first().with { res ->
            new RegisterEntryResponse(
                    id: res.id as long,
                    created: new Date(res.created),
                    uniqueId: res.uniqueId,
                    sourceType: res.sourceType,
                    value: res.value
            )
        }
    }
}
