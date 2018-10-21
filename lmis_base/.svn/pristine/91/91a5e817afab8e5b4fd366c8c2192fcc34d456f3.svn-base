package com.lmis.jbasis.area.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.basis.costCenter.model.BasisCostCenter;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.jbasis.area.dao.JbasisAreaMapper;
import com.lmis.jbasis.area.model.JbasisArea;
import com.lmis.jbasis.area.model.ViewJbasisArea;
import com.lmis.jbasis.area.service.JbasisAreaServiceInterface;

/** 
 * @ClassName: JbasisAreaServiceImpl
 * @Description: TODO(地址库表（基础数据）业务层实现类)
 * @author codeGenerator
 * @date 2018-04-11 14:23:33
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class JbasisAreaServiceImpl<T extends JbasisArea> implements JbasisAreaServiceInterface<T> {
    
    @Resource(name="dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<JbasisArea> dynamicSqlService;
    @Autowired
    private JbasisAreaMapper<T> jbasisAreaMapper;
    @Autowired
    private HttpSession session;

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeSelect(T t) throws Exception {
        LmisResult<T> _lmisResult = new LmisResult<>();
        List<JbasisArea> retrieve = jbasisAreaMapper.retrieveJbasisArea(t);
        _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        _lmisResult.setData(retrieve);
        return _lmisResult;
    }
    

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
        LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
        if(LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode())) return _lmisResult;
        List<Map<String, Object>> result =  (List<Map<String, Object>>) _lmisResult.getData();
        if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
        if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
    }

    @Override
    public LmisResult<?> executeInsert(T t) throws Exception {
            
        // TODO(业务校验)

        JbasisArea area = t;
        JbasisArea ja = new JbasisArea();
        ja.setPid(area.getPid());
        List<JbasisArea> retrieveByPid = jbasisAreaMapper.retrieveByCode(ja);
        if(retrieveByPid.size()<1) throw new Exception("该父节点不存在");
        ja.setPid(null);
        ja.setAreaCode(area.getAreaCode());
        List<JbasisArea> retrieveByCode = jbasisAreaMapper.retrieveByCode(ja);
        if(retrieveByCode.size()>0)throw new Exception("该数据已存在");
        ja.setAreaCode(null);
        ja.setAreaName(area.getAreaName());
        List<JbasisArea> retrieveByName = jbasisAreaMapper.retrieveByCode(ja);
        //校验同一父节点下name是否相同
        for (int i = 0; i < retrieveByName.size(); i++){
            if(retrieveByName.get(i).getPid().equals(retrieveByPid.get(0).getAreaCode())) throw new Exception("该数据已存在");
        }
        
        if(retrieveByPid.get(0).getHaschild()==0){//修改父节点hasChild字段
            ja.setPid(null);
            
            //更新人
            ja.setUpdateBy(session.getAttribute("lmisUserId").toString());
            //所属机构
            ja.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
            ja.setId(area.getPid()+"");
            ja.setHaschild(1);
            ja.setPid(null);
            jbasisAreaMapper.update((T) ja);
        }
        area.setLevel(retrieveByPid.get(0).getLevel()+1);
        area.setHaschild(0);
        // 插入操作
        //创建人
        area.setCreateBy(session.getAttribute("lmisUserId").toString());
        //更新人
        area.setUpdateBy(session.getAttribute("lmisUserId").toString());
        //所属机构
        area.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisAreaMapper.create((T) area));
    }

    @Override
    public LmisResult<?> executeUpdate(T t) throws Exception {
        
            
        JbasisArea area = t;
        String areaCode = area.getAreaCode();
        if(areaCode==null||area.getAreaName()==null||area.getId()==null||"".equals(areaCode)||"".equals(area.getAreaName())||"".equals(area.getId())){
            throw new Exception("数据缺失");
        }
        JbasisArea ja = new JbasisArea();
        ja.setId(area.getId());
        List<JbasisArea> retrieveById = jbasisAreaMapper.retrieveByCode(ja);
        ja.setId(null);
        ja.setAreaCode(areaCode);
        List<JbasisArea> retrieveByCode = jbasisAreaMapper.retrieveByCode(ja);
        if(retrieveByCode.size()>0&&!area.getId().equals(retrieveByCode.get(0).getId()))throw new Exception("该数据已存在");
        ja.setAreaCode(null);
        ja.setAreaName(area.getAreaName());
        List<JbasisArea> retrieveByName = jbasisAreaMapper.retrieveByCode(ja);
        //校验同一父节点下name是否相同 
        for (int i = 0; i < retrieveByName.size(); i++){
            if(retrieveByName.get(i).getPid().equals(retrieveById.get(0).getPid())&&!area.getId().equals(retrieveByName.get(i).getId())) throw new Exception("该数据已存在");
        }
        
        // 更新操作
        //更新人
        area.setUpdateBy(session.getAttribute("lmisUserId").toString());
        //所属机构
        area.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
        area.setPid(areaCode);
        area.setAreaCode(retrieveById.get(0).getAreaCode());
        jbasisAreaMapper.updateByPid((T) area);
        area.setAreaCode(area.getPid());
        area.setPid(null);
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisAreaMapper.update((T) area));
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> deleteJbasisArea(T t) throws Exception {
        // TODO(业务校验)
        jbasisAreaMapper.delete(t);
        //删除子节点
        recursionDelete(t.getAreaCode());  
        LmisResult<T> _lmisResult = new LmisResult<>();
        _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return _lmisResult;
    }
    
    private void recursionDelete(String areaCode){
       
        JbasisArea jat = new JbasisArea();
        jat.setPid(areaCode);
        List<T> retrieve = jbasisAreaMapper.retrieve((T) jat);
        if(retrieve.size()>0){
            for (int i = 0; i < retrieve.size(); i++){
                if(retrieve.get(i).getHaschild()==1){
                    recursionDelete(retrieve.get(i).getAreaCode());
                }
            }
            jbasisAreaMapper.delete((T)jat); 
              
        }
      
    }

}
