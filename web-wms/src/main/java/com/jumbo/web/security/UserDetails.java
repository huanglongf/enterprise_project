/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.web.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.Role;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.authorization.UserRole;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails, Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 9154433970518929187L;

    private User user;
    private UserRole currentUserRole;
    private Collection<GrantedAuthority> authorities;

    public UserDetails(User user) {
        this.user = user;
        this.currentUserRole = new UserRole();
        this.currentUserRole.setUser(user);
        this.currentUserRole.setOu(user == null ? null : user.getOu());
        this.authorities = new ArrayList<GrantedAuthority>();
    }

    public UserDetails(UserRole userRole, Collection<GrantedAuthority> authorities) {
        this.user = userRole == null ? null : userRole.getUser();
        this.currentUserRole = userRole;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRole getCurrentUserRole() {
        return currentUserRole;
    }

    public void setCurrentUserRole(UserRole currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    public OperationUnit getCurrentOu() {
        return this.currentUserRole == null ? null : currentUserRole.getOu();
    }

    public Role getCurrentRole() {
        return this.currentUserRole == null ? null : currentUserRole.getRole();
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getLoginName();
    }

    public boolean isAccountNonExpired() {
        return user.getIsAccNonExpired() == null ? false : true;
    }

    public boolean isAccountNonLocked() {
        if(null == user){
            return false;
        }
        return user.getIsAccNonLocked() == null ? false : true;
    }

    public boolean isCredentialsNonExpired() {
        return user.getIsPwdNonExpired() == null ? false : true;
    }

    public boolean isEnabled() {
        return user.getIsEnabled() == null ? false : true;
    }

    public boolean check(String[] acls) {
        if (this.authorities == null || this.authorities.size() == 0) {
            return false;
        }
        for (String acl : acls) {
            if (this.authorities.contains(acl)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (getUsername() == null) {
            return super.hashCode();
        } else {
            return getUsername().hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserDetails && getUsername() != null) {
            UserDetails us = (UserDetails) obj;
            return getUsername().equals(us.getUsername());
        }
        return super.equals(obj);
    }
}
