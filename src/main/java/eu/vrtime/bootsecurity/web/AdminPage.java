package eu.vrtime.bootsecurity.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

import eu.vrtime.bootsecurity.auth.CustomUserDetails;

public class AdminPage extends WebPage {

	private static final String LABEL1_ID = "label1";
	private static final String TEXTAREA_ID = "textBox";
	private static final String FORM_ID = "form";
	private static final String BUTTON_ID = "btn";
	private static final String FEEDBACK_ID = "feedback";
	private static final String USERNAME_ID = "userName";
	private static final String USERMAIL_ID = "userMail";
	private static final String SIGNOUT_ID = "signOut";

	private Label label1 = new Label(LABEL1_ID, "Spring Security LDAP");
	private TextArea textBox;
	private Form form = new Form<Void>(FORM_ID);
	private FeedbackPanel feedback = new FeedbackPanel(FEEDBACK_ID);
	private String text;
	private String message;

	private CustomUserDetails userDetails;
	
	@SpringBean
	private AuthenticationManager manager;

	public AdminPage() {

		textBox = new TextArea<>(TEXTAREA_ID, new PropertyModel<>(this, "text"));

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		feedback.setOutputMarkupId(true);

		add(feedback);
		add(label1);

		form.add(textBox);
		form.add(new AjaxButton(BUTTON_ID) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				super.onSubmit(target);

				if (textBox.getModelObject() == null) {
					message = new String("default");
				} else {
					message = (String) textBox.getModelObject();
				}

				feedback.info("Feedback: " + message);
				System.out.println("Feedback" + message);

				target.add(feedback);

			}

		});

		add(form);

		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		add(new Label(USERNAME_ID, userDetails.getUsername()));
		add(new Label(USERMAIL_ID, userDetails.getMail()));
		add(new Label("dn", userDetails.getDn().toString()));
		
		add(createLogoutLink(SIGNOUT_ID));

	}

	private Link<Void> createLogoutLink(final String id) {
		Link<Void> link = new Link<Void>(id) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6468672249778276514L;

			@Override
			public void onClick() {
				WebSession.get().invalidate();

			}

		};

		return link;
	}

}
