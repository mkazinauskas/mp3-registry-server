package preconfigured.authorization.domain.commands

import preconfigured.authorization.domain.UserAuthorityType

import static preconfigured.authorization.domain.UserAuthorityType.USER

class CreateUserCommand {
    String email
    String password
    Set<UserAuthorityType> authorities = [USER]
}
