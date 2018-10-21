package com.jumbo.wms.model.command.authorization;

import java.io.Serializable;
import java.util.List;

import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.authorization.UserRole;

public class UserDetailsCmd implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5768438527903148241L;
	private User user;
	private UserRole currentUserRole;
	private List<String> authorities;

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

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

}
