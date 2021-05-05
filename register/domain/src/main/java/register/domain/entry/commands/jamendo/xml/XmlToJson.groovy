package register.domain.entry.commands.jamendo.xml

import groovy.json.JsonOutput

class XmlToJson {
    static String clean(String xmlAsText) {
        def oldXml = new XmlSlurper().parseText(xmlAsText)
        return JsonOutput.toJson([
                id           : (oldXml.str.find { it.@name == 'id' } as String)?.trim(),
                artist_name  : (oldXml.str.find { it.@name == 'artist_name' } as String)?.trim(),
                license_ccurl: (oldXml.str.find { it.@name == 'license_ccurl' } as String)?.trim(),
                name         : (oldXml.str.find { it.@name == 'name' } as String)?.trim()
        ])
    }
}
