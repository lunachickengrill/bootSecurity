package eu.vrtime.bootsecurity;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import eu.vrtime.bootsecurity.web.AdminPage;

public class WicketApplication extends WebApplication{

	@Override
	public Class<? extends Page> getHomePage() {
		return AdminPage.class;
	}

	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		mountPage("admin",AdminPage.class);
	}
	
	public static WicketApplication get() {
		return (WicketApplication) Application.get();
	}

}
