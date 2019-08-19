package eu.vrtime.bootsecurity.config;

import java.net.PasswordAuthentication;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.authentication.PasswordComparisonAuthenticator;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import eu.vrtime.bootsecurity.auth.CustomUserDetailsContextMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${ldap.url}")
	private String ldapUrl;

	@Value("${ldap.basedn}")
	private String ldapBaseDn;

	@Value("${ldap.user.dn}")
	private String ldapUserDn;

	@Value("${ldap.user.searchbase}")
	private String ldapUserSearchBase;

	@Value("${ldap.user.searchfilter}")
	private String ldapUserSearchFilter;

	@Value("${ldap.group.searchbase}")
	private String ldapGroupSearchBase;

	@Value("${ldap.group.searchfilter}")
	private String ldapGroupSearchFilter;

	@Value("${ldap.user.password}")
	private String ldapUserPassword;

	@Bean(name = "authenticationManager")
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.ldapAuthentication().userDnPatterns(ldapUserDn).contextSource(contextSource())
//				.userDetailsContextMapper(userDetailsContextMapper()).userSearchBase(ldapUserSearchBase)
//				.userSearchFilter(ldapUserSearchFilter).groupSearchBase(ldapGroupSearchBase)
//				.groupSearchFilter(ldapGroupSearchFilter).passwordCompare()
//				.passwordEncoder(new LdapShaPasswordEncoder()) // Need to check what encoder to use
//				.passwordAttribute(ldapUserPassword);
//
//	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
			.ldapAuthentication()
			.contextSource(contextSource())
			.userDetailsContextMapper(userDetailsContextMapper())
			.userDnPatterns(ldapUserDn)
			.groupSearchFilter(ldapGroupSearchFilter)
			.ldapAuthoritiesPopulator(ldapAuthoritiesPopulator());
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers("/admin").fullyAuthenticated()
		.antMatchers("/").fullyAuthenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/j_spring_security_check")
		.permitAll();
		


	}

	// The LdapContextSource configured with the LDAP URL
	@Bean
	public DefaultSpringSecurityContextSource contextSource() {
		return new DefaultSpringSecurityContextSource(Collections.singletonList(ldapUrl), ldapBaseDn);
	}

	
	// The LdapAuthoritiesPopulator to get the group
	@Bean
	public DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
		
		DefaultLdapAuthoritiesPopulator authoritiesPopulator = new DefaultLdapAuthoritiesPopulator(contextSource(), ldapGroupSearchBase);
		authoritiesPopulator.setGroupSearchFilter(ldapGroupSearchFilter);
		authoritiesPopulator.setSearchSubtree(true);
		authoritiesPopulator.setGroupRoleAttribute("cn");
		return authoritiesPopulator;

	}

	@Bean
	public UserDetailsContextMapper userDetailsContextMapper() {
		return new CustomUserDetailsContextMapper();
	}

}
