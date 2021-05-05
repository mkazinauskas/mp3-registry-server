package preconfigured.authorization.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new StandardPasswordEncoder();
    }
}
