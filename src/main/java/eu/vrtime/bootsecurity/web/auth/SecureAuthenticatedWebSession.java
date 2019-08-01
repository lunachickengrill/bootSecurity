package eu.vrtime.bootsecurity.web.auth;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import eu.vrtime.bootsecurity.auth.CustomUserDetails;

public class SecureAuthenticatedWebSession extends AuthenticatedWebSession {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = -5174537839833660312L;

	private CustomUserDetails user;

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

	@Override
	public Roles getRoles() {
		throw new UnsupportedOperationException("Not implemented");
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

}
