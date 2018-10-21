package com.bt.lmis.service;

import java.util.List;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.NvitationdataCollect;

public interface NvitationdataCollectService<T> extends BaseService<T> {
	public List<NvitationdataCollect> findByCBIDACYCLE(NvitationdataCollect data);
}
