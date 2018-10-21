package com.lmis.jbasis.warehouse.service.impl;

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
import com.lmis.jbasis.area.dao.JbasisAreaMapper;
import com.lmis.jbasis.area.model.JbasisArea;
import com.lmis.jbasis.warehouse.dao.JbasisWarehouseMapper;
import com.lmis.jbasis.warehouse.model.JbasisWarehouse;
import com.lmis.jbasis.warehouse.service.JbasisWarehouseServiceInterface;

/**
 * @ClassName: JbasisWarehouseServiceImpl
 * @Description: TODO(仓库信息管理业务层实现类)
 * @author codeGenerator
 * @date 2018-04-17 09:52:14
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class JbasisWarehouseServiceImpl<T extends JbasisWarehouse> implements JbasisWarehouseServiceInterface<T>{

    @Resource(name = "dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<JbasisWarehouse> dynamicSqlService;

    @Autowired
    private JbasisWarehouseMapper<T> jbasisWarehouseMapper;

    @Autowired
    private JbasisAreaMapper<JbasisArea> jbasisAreaMapper;

    @Autowired
    private HttpSession session;

    @Override
    public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam,LmisPageObject pageObject) throws Exception{
        return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
    }

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam,Integer status) throws Exception{

        // TODO(业务校验)
        JbasisWarehouse warehouse = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisWarehouse>) dynamicSqlParam, new JbasisWarehouse()).getTableModel();
        // 仓库编码唯一校验
        if (ObjectUtils.isNull(warehouse.getWarehouseCode())){
            return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "仓库编码不能为空");
        }else{
            JbasisWarehouse checkWarehouse = new JbasisWarehouse();
            checkWarehouse.setIsDeleted(false);
            checkWarehouse.setWarehouseCode(warehouse.getWarehouseCode());
            if (jbasisWarehouseMapper.retrieve((T) checkWarehouse).size() > 0){
                return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "当前仓库编码已存在");
            }
        }
        // 仓库名称唯一校验
        if (ObjectUtils.isNull(warehouse.getWarehouseName())){
            return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "仓库名称不能为空");
        }else{
            JbasisWarehouse checkWarehouse = new JbasisWarehouse();
            checkWarehouse.setIsDeleted(false);
            checkWarehouse.setWarehouseName(warehouse.getWarehouseName());
            if (jbasisWarehouseMapper.retrieve((T) checkWarehouse).size() > 0){
                return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "当前仓库名称已存在");
            }
        }
        //批量导入判断省市区名称是否正确
        if (status==1){
            if (ObjectUtils.isNull(warehouse.getProvince()) || ObjectUtils.isNull(warehouse.getCity()) || ObjectUtils.isNull(warehouse.getState())){
                return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "省市区名称不能为空");
            }else{
                List<JbasisArea> areaList = jbasisAreaMapper.retrieveByNames(warehouse);
                if (areaList.size() == 3){
                    warehouse.setProvince(areaList.get(0).getId());
                    warehouse.setCity(areaList.get(1).getId());
                    warehouse.setState(areaList.get(2).getId());
                }else{
                    return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "省市区名称输入错误");
                }
            }
        }
        // 插入操作
        warehouse.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
        warehouse.setCreateBy(session.getAttribute("lmisUserId").toString());
        if (jbasisWarehouseMapper.create((T) warehouse) == 0){
            return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "插入仓库数据失败");
        }
        return new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS, "插入仓库数据成功");
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception{

        // TODO(业务校验)
        JbasisWarehouse warehouse = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisWarehouse>) dynamicSqlParam, new JbasisWarehouse()).getTableModel();
        // 仓库名称唯一校验
        if (ObjectUtils.isNull(warehouse.getWarehouseName())){
            return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "仓库名称不能为空");
        }else{
            JbasisWarehouse checkWarehouse = new JbasisWarehouse();
            checkWarehouse.setIsDeleted(false);
            checkWarehouse.setWarehouseName(warehouse.getWarehouseName());
            List<T> list = jbasisWarehouseMapper.retrieve((T) checkWarehouse);
            if (list.size() > 0 && !list.get(0).getWarehouseCode().equals(warehouse.getWarehouseCode())){
                return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "当前仓库名称已存在");
            }
        }
        // 更新操作
        warehouse.setUpdateBy(session.getAttribute("lmisUserId").toString());
        if (jbasisWarehouseMapper.update((T) warehouse) == 0){
            return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "更新仓库数据失败");
        }
        return new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS, "更新仓库数据成功");
    }

    @Override
    public LmisResult<?> deleteJbasisWarehouse(T t) throws Exception{

        // 更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());

        // 删除操作
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisWarehouseMapper.logicalDelete(t));
    }

    @Override
    public LmisResult<?> switchJbasisWarehouse(T t) throws Exception{

        // 更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());

        // 启用店铺/禁用店铺
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisWarehouseMapper.shiftValidity(t));
    }

}
