package register.domain.entry.commands.jamendo.json

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class JsonCleaner {
    static String clean(String jsonAsText) {
        def oldJson = new JsonSlurper().parseText(jsonAsText)
        return JsonOutput.toJson([
                id           : oldJson.id,
                artist_name  : oldJson.artist_name,
                license_ccurl: oldJson.license_ccurl,
                name         : oldJson.name,
                album_name   : oldJson.album_name
        ])
    }
}
