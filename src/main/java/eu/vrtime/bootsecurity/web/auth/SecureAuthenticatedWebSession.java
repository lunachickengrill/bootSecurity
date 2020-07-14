package eu.vrtime.bootsecurity.web.auth;

import java.util.Collection;

import javax.inject.Inject;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.util.Assert;

import eu.vrtime.bootsecurity.auth.CustomRoles;
import eu.vrtime.bootsecurity.auth.CustomUserDetails;

public class SecureAuthenticatedWebSession extends AuthenticatedWebSession {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = -5174537839833660312L;
	private static final String ADMIN_ROLE = "ROLE_APP_TMNGX-ADMIN";
	private static final String SUPPORT_ROLE = "ROLE_APP_TMNGX-SUPPORT";
	
	private CustomUserDetails user;
	private Collection<? extends GrantedAuthority> authorities;

	@Inject
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;

	public SecureAuthenticatedWebSession(Request request) {
		super(request);
		Injector.get().inject(this);
	}

	@Override
	protected boolean authenticate(String username, String password) {
		Assert.notNull(username, "no username given");
		Assert.notNull(password, "no password given");

		return auth(username, password);

	}

	private boolean auth(String username, String password) {
		boolean isAuth = false;

		try {
			Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			if (auth.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(auth);
				isAuth = true;
			}
		} catch (AuthenticationException ex) {
			logger.info("Authentication failed", ex);
			return false;
		}

		return isAuth;

	}

	@Override
	public Roles getRoles() {

		CustomRoles roles = new CustomRoles();
		getRolesIfSignedIdn(roles);

//		throw new UnsupportedOperationException("Not implemented");
		return roles;
	}

	private void getRolesIfSignedIdn(CustomRoles roles) {
		if (isSignedIn()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			addRolesFromAuthentication(roles, authentication);
		}
	}

	private void addRolesFromAuthentication(CustomRoles roles, Authentication authentication) {
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			roles.add(authority.getAuthority());
			if (authority.getAuthority().contains(SUPPORT_ROLE)) {
				roles.add(CustomRoles.USER_BASIC);
			}
			if (authority.getAuthority().contains(ADMIN_ROLE)) {
				roles.add(CustomRoles.USER_ADVANCED);
			}
		}

	}

}
