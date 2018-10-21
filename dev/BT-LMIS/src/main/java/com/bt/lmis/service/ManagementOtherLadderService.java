package com.bt.lmis.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.model.ManagementOtherLadder;

public interface ManagementOtherLadderService {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagementOtherLadder record);

    int insertSelective(ManagementOtherLadder record);

    ManagementOtherLadder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagementOtherLadder record);

    int updateByPrimaryKey(ManagementOtherLadder record);

	JSONObject loadManECOther(HttpServletRequest request, JSONObject result) throws RuntimeException;

	List<ManagementOtherLadder> findList(ManagementOtherLadder managementOtherLadder);

	JSONObject deleteManECOther(HttpServletRequest request, JSONObject result) throws RuntimeException;

	JSONObject saveManECOther(HttpServletRequest request, JSONObject result) throws RuntimeException;
}