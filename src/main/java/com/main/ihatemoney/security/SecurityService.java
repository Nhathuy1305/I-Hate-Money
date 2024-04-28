package com.main.ihatemoney.security;

import com.main.ihatemoney.data.service.PfmService;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class SecurityService {

    private final AuthenticationContext authenticationContext;

    // Constructor
    public SecurityService(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public UserDetails getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class).get();
    }

    public Long getCurrentUserID(PfmService service) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails userPrincipal) {
            String usernameEmail = userPrincipal.getUsername();
            return service.findUserByEmail(usernameEmail).getId();
        } else {
            logout();
            return null;
        }
    }

    public void logout() {
        authenticationContext.logout();
    }
}
