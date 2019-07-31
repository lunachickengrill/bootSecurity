package eu.vrtime.bootsecurity.web.login;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.springframework.util.Assert;

import eu.vrtime.bootsecurity.auth.CustomUserDetails;

public class SecureAuthenticatedWebSession extends AuthenticatedWebSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5174537839833660312L;

	private CustomUserDetails user;
	
	public SecureAuthenticatedWebSession(Request request) {
		super(request);
		Injector.get().inject(this);
	}

	@Override
	protected boolean authenticate(String username, String password) {
		Assert.notNull(username, "no username given");
		Assert.notNull(password, "no password given");
		return false;
	}

	@Override
	public Roles getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

}
