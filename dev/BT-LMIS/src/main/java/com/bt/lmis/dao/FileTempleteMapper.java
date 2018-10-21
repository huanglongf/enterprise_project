package com.bt.lmis.dao;

import java.util.List;

import com.bt.lmis.controller.form.FileTempleteQueryParam;
import com.bt.lmis.model.FileTemplete;

public interface FileTempleteMapper {
    int deleteByPrimaryKey(String id);

    int insert(FileTemplete record);

    int insertSelective(FileTemplete record);

    FileTemplete selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileTemplete record);

    int updateByPrimaryKey(FileTemplete record);

	FileTempleteQueryParam getByQueryParam(FileTempleteQueryParam fileTemplete);

	List<FileTempleteQueryParam> findByQueryParam(FileTempleteQueryParam fileTemplete);
}