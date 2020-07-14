package eu.vrtime.bootsecurity.web.login;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import eu.vrtime.bootsecurity.web.AdminPage;
import eu.vrtime.bootsecurity.web.auth.SecureAuthenticatedWebSession;

public class LoginPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String LOGINFORM_ID = "loginForm";
	private static final String USERNAME_ID = "username";
	private static final String PASSWORD_ID = "password";
	private static final String ATTRIBUTE_PLACEHOLDER = "placeholder";
	private static final String SIGNIN_BTN = "signIn";

	private String username;
	private String password;

	public LoginPage() {
		super();
		add(createLoginForm(LOGINFORM_ID));
	}

	private StatelessForm<Void> createLoginForm(final String id) {

		StatelessForm<Void> form = new StatelessForm<>(id);
		form.setDefaultModel(new CompoundPropertyModel(this));

		TextField tfUsername = new TextField<>(USERNAME_ID);
		tfUsername.add(new AttributeModifier(ATTRIBUTE_PLACEHOLDER, "username"));
		form.add(tfUsername);

		PasswordTextField tfPassword = new PasswordTextField(PASSWORD_ID);
		tfPassword.add(new AttributeModifier(ATTRIBUTE_PLACEHOLDER, "password"));
		form.add(tfPassword);

		form.add(new Button(SIGNIN_BTN) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {

				System.out.println("INSIDE LoginForm onSubmit: " + username + " " + password);
				boolean authResult = getCurrentSession().signIn(username, password);

				if (authResult) {
					setResponsePage(AdminPage.class);
				}
			}

		});

		return form;

	}

	private SecureAuthenticatedWebSession getCurrentSession() {
		return (SecureAuthenticatedWebSession) AuthenticatedWebSession.get();

	}

}
