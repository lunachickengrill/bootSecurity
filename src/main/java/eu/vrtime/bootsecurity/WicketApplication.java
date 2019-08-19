package eu.vrtime.bootsecurity;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import eu.vrtime.bootsecurity.web.AdminPage;
import eu.vrtime.bootsecurity.web.auth.SecureAuthenticatedWebSession;
import eu.vrtime.bootsecurity.web.login.LoginPage;

public class WicketApplication extends AuthenticatedWebApplication {

	@Override
	protected void init() {

		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		mountPage("/admin", AdminPage.class);
		mountPage("/login", LoginPage.class);
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return AdminPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return SecureAuthenticatedWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

	public static WicketApplication get() {
		return (WicketApplication) Application.get();
	}

}
