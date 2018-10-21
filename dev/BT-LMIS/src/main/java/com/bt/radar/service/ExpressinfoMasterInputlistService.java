package com.bt.radar.service;

import java.util.List;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.ExpressinfoMasterInputlistQueryParam;
import com.bt.radar.model.ExpressinfoMasterInputlist;

public interface ExpressinfoMasterInputlistService<T> extends BaseService<T> {

	QueryResult<ExpressinfoMasterInputlist> findAll(ExpressinfoMasterInputlistQueryParam queryParam);

	int checkDel(List<String> list);

	void del(List<String> list);

}
