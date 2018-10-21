package com.jumbo.manager.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jumbo.web.security.GrantedAuthorityImpl;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.model.command.authorization.UserDetailsCmd;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthorizationManager authorizationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        UserDetailsCmd cmd = authorizationManager.loadUserByUsername(username);
        return constructUserDetails(cmd);
    }

    private org.springframework.security.core.userdetails.UserDetails constructUserDetails(UserDetailsCmd cmd) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (cmd != null && cmd.getAuthorities() != null) {
            for (String acl : cmd.getAuthorities()) {
                GrantedAuthority auth = new GrantedAuthorityImpl(acl);
                authorities.add(auth);
            }
        }
        UserDetails userDetails = new com.jumbo.web.security.UserDetails(cmd.getCurrentUserRole(), authorities);
        return userDetails;
    }

}
