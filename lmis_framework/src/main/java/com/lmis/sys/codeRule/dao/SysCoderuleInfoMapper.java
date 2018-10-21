package com.lmis.sys.codeRule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.sys.codeRule.model.SysCoderuleInfo;
import com.lmis.sys.codeRule.model.ViewSysCoderuleInfo;
import com.lmis.sys.codeRule.vo.RuleDepositVo;
import com.lmis.sys.codeRule.vo.RuleInfoVo;

/**
 * @ClassName: SysCoderuleInfoMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-22 08:53:05
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysCoderuleInfoMapper<T extends SysCoderuleInfo> extends BaseMapper<T>{

    /**
     * @Title: retrieveViewSysCoderuleInfo
     * @Description: TODO(查询view_sys_coderule_info)
     * @param viewSysCoderuleInfo
     * @return: List<ViewSysCoderuleInfo>
     * @author: codeGenerator
     * @date: 2018-05-22 08:53:05
     */
    List<ViewSysCoderuleInfo> retrieveViewSysCoderuleInfo(ViewSysCoderuleInfo viewSysCoderuleInfo);

    /**
     * @author xuyisu
     * @date 2018/05/25
     * @param configCode
     * @return
     */
    List<RuleInfoVo> getInfoByConfigCode(String configCode);

    /**
     * @author xuyisu
     * @date 2018/05/25
     * @param configCode
     * @return
     */
    RuleDepositVo getDepositByConfigCode(String configCode);

    /**
     * @author xuyisu
     * @date 2018/05/25
     * @param id
     * @return
     */
    SysCoderuleInfo getCoderuleInfoById(@Param("id") String id);

}
