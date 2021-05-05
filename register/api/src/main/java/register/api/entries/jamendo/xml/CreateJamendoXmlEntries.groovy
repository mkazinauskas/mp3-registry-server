package register.api.entries.jamendo.xml

import groovy.transform.CompileStatic
import org.hibernate.validator.constraints.NotBlank

@CompileStatic
class CreateJamendoXmlEntries {

    @NotBlank
    String xmlResponse
}
