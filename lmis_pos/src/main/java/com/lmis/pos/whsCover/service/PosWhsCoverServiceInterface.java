package com.lmis.pos.whsCover.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: PosWhsCoverServiceInterface
 * @Description: TODO(仓库覆盖区域设置业务层接口类)
 * @author codeGenerator
 * @date 2018-05-29 16:30:53
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosWhsCoverServiceInterface<T extends PersistentObject> {

    LmisResult<?> whsAreaSearch(T t) throws Exception;

    LmisResult<?> whsBindingArea(String whsCode,String province,String citys) throws Exception;

    LmisResult<?> deleteBindingArea(T t) throws Exception;
}
