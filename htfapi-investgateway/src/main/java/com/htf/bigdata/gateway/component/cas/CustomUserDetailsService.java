package com.htf.bigdata.gateway.component.cas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户模型
 * @author wb-wuxiao
 */
//@Component
public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
	
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDetails loadUserDetails(CasAssertionAuthenticationToken authentication) throws UsernameNotFoundException {
		log.info("当前的用户名是：{}", authentication.getName());

		String login = authentication.getPrincipal().toString();
		String username = login.toLowerCase();
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
//		authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));

		UserDetails userDetails = new User(username, "", true, true, true, true, authList);
		
		return userDetails;
	}
}
