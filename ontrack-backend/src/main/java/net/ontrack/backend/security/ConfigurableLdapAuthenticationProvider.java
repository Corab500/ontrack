package net.ontrack.backend.security;

import net.ontrack.backend.LDAPConfigurationListener;
import net.ontrack.service.AdminService;
import net.ontrack.service.model.LDAPConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ConfigurableLdapAuthenticationProvider implements AuthenticationProvider, LDAPConfigurationListener {

    private final AdminService adminService;
    private final LdapAuthoritiesPopulator authoritiesPopulator;

    private LdapAuthenticationProvider ldapAuthenticationProvider;

    @Autowired
    public ConfigurableLdapAuthenticationProvider(AdminService adminService, LdapAuthoritiesPopulator authoritiesPopulator) {
        this.adminService = adminService;
        this.authoritiesPopulator = authoritiesPopulator;
    }

    @PostConstruct
    public void init() {
        onLDAPConfigurationChanged(adminService.getLDAPConfiguration());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // If not enabled, cannot authenticate!
        if (ldapAuthenticationProvider == null) {
            return null;
        }
        // LDAP connection
        else {
            return ldapAuthenticationProvider.authenticate(authentication);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public synchronized void onLDAPConfigurationChanged(LDAPConfiguration configuration) {
        if (configuration.isEnabled()) {
            // LDAP URL
            String ldapUrl = String.format("ldap://%s:%s", configuration.getHost(), configuration.getPort());
            // LDAP context
            DefaultSpringSecurityContextSource ldapSource = new DefaultSpringSecurityContextSource(ldapUrl);
            ldapSource.setUserDn(configuration.getUser());
            ldapSource.setPassword(configuration.getPassword());
            // User search
            FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(
                    configuration.getSearchBase(),
                    configuration.getSearchFilter(),
                    ldapSource);
            userSearch.setSearchSubtree(true);
            // Bind authenticator
            BindAuthenticator bindAuthenticator = new BindAuthenticator(ldapSource);
            bindAuthenticator.setUserSearch(userSearch);
            // Provider
            LdapAuthenticationProvider ldap = new LdapAuthenticationProvider(bindAuthenticator, authoritiesPopulator);
            // OK
            ldapAuthenticationProvider = ldap;
        } else {
            ldapAuthenticationProvider = null;
        }
    }
}
