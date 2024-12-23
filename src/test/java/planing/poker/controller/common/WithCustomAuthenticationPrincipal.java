package planing.poker.controller.common;

import org.springframework.security.test.context.support.WithSecurityContext;
import planing.poker.domain.SecurityRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithCustomAuthenticationPrincipal {

    String username() default "user";

    String password() default "password";

    SecurityRole role() default SecurityRole.ROLE_USER;
}
