package iyt.security;

import iyt.models.User;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class AuthProvider extends AbstractUserDetailsAuthenticationProvider
{
	// Find user and check password
	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException
	{
		final String password = authentication.getCredentials().toString();
    	
		Objectify ofy = ObjectifyService.begin();
		try {
			ObjectifyService.register(User.class);
		}
		catch (Exception e) { }
		User user = ofy.get(User.class, username);
		System.out.println(user.toString());
		System.out.println(user.getPassword());
		System.out.println(password);
		
		if(user == null || !user.getPassword().equals(password))		
			throw new BadCredentialsException("Username/Password does not match for " 
					+ authentication.getPrincipal());
		return user; 
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0,
			UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
	}
}