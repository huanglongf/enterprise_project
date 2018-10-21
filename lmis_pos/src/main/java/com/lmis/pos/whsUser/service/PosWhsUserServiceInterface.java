package com.lmis.pos.whsUser.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: PosWhsUserServiceInterface
 * @Description: TODO(仓库用户业务层接口类)
 * @author codeGenerator
 * @date 2018-05-29 16:31:04
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosWhsUserServiceInterface<T extends PersistentObject> {

    LmisResult<?> whsUserSearch(T t) throws Exception;

    LmisResult<?> whsBindingUser(String whsCode, String userList) throws Exception;

    LmisResult<?> deleteWhsUser(T t) throws Exception;

    LmisResult<?> getAllUser() throws Exception;
}
