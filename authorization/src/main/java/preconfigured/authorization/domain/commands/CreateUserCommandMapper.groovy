package preconfigured.authorization.domain.commands

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import preconfigured.authorization.domain.User

@Component
@CompileStatic
class CreateUserCommandMapper {

    @Autowired
    private PasswordEncoder passwordEncoder

    User mapFrom(CreateUserCommand command) {
        new User().with {
            email = command.email
            encodedPassword = passwordEncoder.encode(command.password)
            authorities.addAll command.authorities
            it
        }
    }
}
