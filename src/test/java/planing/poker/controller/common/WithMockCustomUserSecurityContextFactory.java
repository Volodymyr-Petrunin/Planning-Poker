package planing.poker.controller.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import planing.poker.domain.User;
import planing.poker.security.UserDetailsImpl;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomAuthenticationPrincipal> {

    @Override
    public SecurityContext createSecurityContext(final WithCustomAuthenticationPrincipal customUser) {
        final SecurityContext context = SecurityContextHolder.createEmptyContext();

        final UserDetailsImpl userDetails = new UserDetailsImpl(
                new User()
                        .setEmail(customUser.username())
                        .setPassword(customUser.password())
                        .setSecurityRole(customUser.role())
        );

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        context.setAuthentication(authentication);
        return context;
    }
}

