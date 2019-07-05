package eu.vrtime.bootsecurity.config;

import javax.servlet.Filter;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.vrtime.bootsecurity.WicketApplication;

@Configuration
public class SpringConfig {

	@Bean
	public Filter getWicketFilter() {
		WicketApplication application = new WicketApplication();
		application.setConfigurationType(RuntimeConfigurationType.DEVELOPMENT);
		WicketFilter filter = new WicketFilter(application);
		filter.setFilterPath("/");
		return filter;
	}

}