package search.domain.entry.commands

import com.google.common.base.Preconditions
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component

@Component
class CreateEntryValidator {
    void validate(CreateEntry command){
        Preconditions.checkArgument(StringUtils.isNotBlank(command.id), 'Id was blank')
        Preconditions.checkArgument(StringUtils.isNotBlank(command.artistName), 'Artist name was blank')
        Preconditions.checkArgument(StringUtils.isNotBlank(command.name), 'Name was blank')
//        Preconditions.checkArgument(StringUtils.isNotBlank(command.licenseUrl), 'License url was blank')
    }
}
