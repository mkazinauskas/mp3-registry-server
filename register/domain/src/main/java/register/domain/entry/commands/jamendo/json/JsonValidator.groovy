package register.domain.entry.commands.jamendo.json

import org.apache.commons.lang3.StringUtils

class JsonValidator {
    static void validate(String json){
        if (![
                StringUtils.isNotBlank(json),
                json.contains('id'),
                json.contains('artist_name'),
                json.contains('license_ccurl'),
                json.contains('name')

        ].every()) {
            throw new IllegalArgumentException('GodDamn!.!')
        }
    }
}
