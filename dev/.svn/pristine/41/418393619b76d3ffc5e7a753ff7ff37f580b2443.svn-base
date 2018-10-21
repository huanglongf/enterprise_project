package com.bt.login.service;

import java.util.Map;

import com.bt.login.controller.form.UserQueryParam;
import com.bt.login.model.User;
import com.bt.orderPlatform.page.QueryResult;

public interface UserService<T> {

	QueryResult<Map<String, Object>> findUser(UserQueryParam queryParam);

	User selectById(String id);

	void insertUser(UserQueryParam queryParam);

	void updateuser(UserQueryParam queryParam);

}
