package com.lmis.pos.whsUser.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.whsUser.dao.PosWhsUserMapper;
import com.lmis.pos.whsUser.model.PosWhsUser;
import com.lmis.pos.whsUser.service.PosWhsUserServiceInterface;

/** 
 * @ClassName: PosWhsUserServiceImpl
 * @Description: TODO(仓库用户业务层实现类)
 * @author codeGenerator
 * @date 2018-05-29 16:31:04
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosWhsUserServiceImpl<T extends PosWhsUser> implements PosWhsUserServiceInterface<T> {
	
	@Autowired
	private PosWhsUserMapper<T> posWhsUserMapper;
	@Autowired
    private HttpSession session;
    @Override
    public LmisResult<?> whsUserSearch(T t) throws Exception{
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,posWhsUserMapper.whsUserSearch(t));
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> whsBindingUser(String whsCode, String userList) throws Exception{
        String [] users = userList.split(",");
        for(String userCode:users){
            PosWhsUser t = new PosWhsUser();
            t.setUserCode(userCode);
            t.setWhsCode(whsCode);
            if(posWhsUserMapper.retrieve((T) t).size()>0){
                throw new Exception("有用户已存在");
            }
            t.setCreateBy(session.getAttribute("lmisUserId").toString());
            t.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
            posWhsUserMapper.create((T) t);
        }
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,"");
    }

    @Override
    public LmisResult<?> deleteWhsUser(T t) throws Exception{
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,posWhsUserMapper.delete(t));
    }

    @Override
    public LmisResult<?> getAllUser() throws Exception{
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,posWhsUserMapper.getAllUser());
    }


}
