package preconfigured.authorization.domain

import groovy.transform.CompileStatic
import org.apache.commons.lang3.RandomStringUtils
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.*
import javax.validation.constraints.NotNull

@CompileStatic
@Entity
@Table(name = 'users')
class User {
    @Id
    @GeneratedValue
    @Column(name = 'id')
    Long id

    @Version
    @Column(name = 'version', nullable = false)
    long version

    @Email
    @NotNull
    @Column(name = 'email', unique = true)
    String email

    @NotNull
    @Column(name = 'encoded_password')
    String encodedPassword

    @Column(name = 'enabled')
    boolean enabled

    @Column(name = 'account_not_expired')
    boolean accountNotExpired

    @Column(name = 'credentials_not_expired')
    boolean credentialsNonExpired

    @Column(name = 'account_not_locked')
    boolean accountNotLocked

    @NotBlank
    @Column(name = 'unique_id', unique = true, length = 32)
    String uniqueId = RandomStringUtils.randomAlphanumeric(32)

    @Column(name = 'password_reset_code', length = 32)
    String passwordResetCode

    @Column(name = 'activation_code', length = 32)
    String activationCode

    @NotEmpty
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = 'user_authorities', joinColumns = @JoinColumn(name = 'user_id', nullable = false))
    @Column(name = 'authority')
    final Set<UserAuthorityType> authorities = []

    User() {
    }
}

