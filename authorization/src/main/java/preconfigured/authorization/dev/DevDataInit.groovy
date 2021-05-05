package preconfigured.authorization.dev

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import preconfigured.authorization.domain.User
import preconfigured.authorization.domain.commands.CreateUserCommand
import preconfigured.authorization.domain.commands.CreateUserCommandHandler

@Component
public class DevDataInit {

    @Autowired
    private CreateUserCommandHandler createUserCommandHandler;

    private final List<User> USERS = [
            new CreateUserCommand(email: 'testUser1@mail.com', password: 'password'),
            new CreateUserCommand(email: 'testUser2@mail.com', password: 'password')
    ]

    @Bean
    public CommandLineRunner init() {
        return new CommandLineRunner() {
            @Override
            void run(String... args) throws Exception {
                USERS.each { createUserCommandHandler.createUser(it) }
            }
        }
    }
}
