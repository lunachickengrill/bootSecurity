package eu.vrtime.bootsecurity.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

public class CustomUserDetails implements LdapUserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5467539837544631982L;
	private LdapUserDetails details;
	private String mail;

	public CustomUserDetails(LdapUserDetails details, String mail) {
		this.details = details;
		this.mail = mail;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return details.getAuthorities();
	}

	@Override
	public String getPassword() {
		return details.getPassword();
	}

	@Override
	public String getUsername() {
		return details.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return details.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return details.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return details.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return details.isEnabled();
	}

	@Override
	public void eraseCredentials() {
		details.eraseCredentials();

	}

	@Override
	public String getDn() {
		return details.getDn();
	}

	public String getMail() {
		return mail;
	}

}
