package register.api.entries.jamendo.json

import groovy.transform.CompileStatic
import org.hibernate.validator.constraints.NotBlank

@CompileStatic
class CreateJamendoJsonEntries {

    @NotBlank
    String jsonResponse
}
