package com.lmis.jbasis.warePark.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.jbasis.warePark.dao.JbasisWareParkMapper;
import com.lmis.jbasis.warePark.model.JbasisWarePark;
import com.lmis.jbasis.warePark.service.JbasisWareParkServiceInterface;

/**
 * @ClassName: JbasisWareParkServiceImpl
 * @Description: (业务层实现类)
 * @author codeGenerator
 * @date 2018-04-12 09:57:26
 * 
 * @param <T>
 */
@SuppressWarnings("unchecked")
@Transactional(rollbackFor = Exception.class)
@Service
public class JbasisWareParkServiceImpl<T extends JbasisWarePark> implements JbasisWareParkServiceInterface<T>{

    @Resource(name = "dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<JbasisWarePark> dynamicSqlService;

    @Autowired
    private JbasisWareParkMapper<T> jbasisWareParkMapper;

    @Autowired
    private HttpSession session;

    @Override
    public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam,LmisPageObject pageObject) throws Exception{
        return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
    }

    @Override
    public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception{
        LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
        if (LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode()))
            return _lmisResult;
        List<Map<String, Object>> result = (List<Map<String, Object>>) _lmisResult.getData();
        if (ObjectUtils.isNull(result))
            throw new Exception("查无记录，数据异常");
        if (result.size() != 1)
            throw new Exception("记录存在多条，数据异常");
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
    }

    @Override
    public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception{
        //唯一校验
        JbasisWarePark store = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisWarePark>) dynamicSqlParam, new JbasisWarePark()).getTableModel();
        JbasisWarePark checkStore = new JbasisWarePark();
        checkStore.setIsDeleted(false);
        checkStore.setParkCode(store.getParkCode());

        if (jbasisWareParkMapper.retrieve((T) checkStore).size() > 0)
            throw new Exception("当前园区编码已存在");

        store.setUpdateBy(session.getAttribute("lmisUserId").toString());
        store.setCreateBy(session.getAttribute("lmisUserId").toString());
        store.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
        LmisResult<?> lmisResult = new LmisResult<>();
        if (jbasisWareParkMapper.create((T) store) > 0){
            lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
            lmisResult.setMessage("成功");
        }else{
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            lmisResult.setMessage("失败");
        }

        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);

        return lmisResult;
    }

    @Override
    public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception{
        //存在校验
        JbasisWarePark store = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisWarePark>) dynamicSqlParam, new JbasisWarePark()).getTableModel();
        JbasisWarePark checkStore = new JbasisWarePark();
        checkStore.setIsDeleted(false);
        checkStore.setParkCode(store.getParkCode());
        if (jbasisWareParkMapper.retrieve((T) checkStore).size() == 0)
            throw new Exception("当前园区编码不存在");
        //更新人
        dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
        // 更新操作
        return dynamicSqlService.executeUpdate(dynamicSqlParam);
    }

    @Override
    public LmisResult<?> deleteJbasisWarePark(T t) throws Exception{
        //存在校验
        JbasisWarePark checkStore = new JbasisWarePark();
        checkStore.setIsDeleted(false);
        checkStore.setParkCode(t.getParkCode());
        if (jbasisWareParkMapper.retrieve((T) checkStore).size() == 0)
            throw new Exception("当前园区编码不存在");
        //更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());

        // 删除操作
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisWareParkMapper.logicalDelete(t));
    }

    /*
     * @Override
     * public LmisResult<?> exportCostCenter() throws Exception {
     * 
     * //返回参数
     * LmisResult<List<ViewBasisCostCenter>> lmisResult=new LmisResult<>();
     * List<ViewBasisCostCenter> exportList=new ArrayList<>();
     * 
     * ViewBasisCostCenter costCenter=new ViewBasisCostCenter();
     * costCenter.setIsDeleted(false);
     * costCenter.setIsDisabled(false);
     * costCenter.setPid("0");
     * List<ViewBasisCostCenter> costCenterList = jbasisWareParkMapper.retrieveViewBasisCostCenter(costCenter);
     * for (ViewBasisCostCenter a : costCenterList) {
     * exportList.add(a);
     * exportRecursive(a,exportList);
     * }
     * lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
     * lmisResult.setData(exportList);
     * 
     * return lmisResult;
     * }
     */
}
