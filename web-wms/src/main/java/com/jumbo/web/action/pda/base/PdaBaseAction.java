package com.jumbo.web.action.pda.base;

import com.jumbo.web.action.BaseJQGridAction;
import com.jumbo.web.security.UserDetails;

public class PdaBaseAction extends BaseJQGridAction{

	private static final long serialVersionUID = 2679806245982504694L;

	private UserDetails userDetails;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
