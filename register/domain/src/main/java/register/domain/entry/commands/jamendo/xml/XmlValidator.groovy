package register.domain.entry.commands.jamendo.xml

import org.apache.commons.lang3.StringUtils

class XmlValidator {
    static void validate(String xml){
        if (![
                StringUtils.isNotBlank(xml),
                xml.contains('id'),
                xml.contains('artist_name'),
                xml.contains('license_ccurl'),
                xml.contains('name')

        ].every()) {
            throw new IllegalArgumentException('GodDamn!.!')
        }
    }
}
