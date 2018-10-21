package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.warehouse.TransPrioritySelectionDao;
import com.jumbo.dao.warehouse.TransRoleDao;
import com.jumbo.dao.warehouse.TransRoleDetialDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.trans.TransAreaGroup;
import com.jumbo.wms.model.trans.TransAreaGroupStatus;
import com.jumbo.wms.model.trans.TransPriSelType;
import com.jumbo.wms.model.trans.TransPrioritySelection;
import com.jumbo.wms.model.trans.TransRole;
import com.jumbo.wms.model.trans.TransRoleCommand;
import com.jumbo.wms.model.trans.TransRoleDetial;
import com.jumbo.wms.model.trans.TransRoleDetialCommand;
import com.jumbo.wms.model.trans.TransportService;

/**
 * 物流规则
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
@Service("transRoleManager")
public class TransRoleManagerImpl extends BaseManagerImpl implements TransRoleManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Autowired
    private TransRoleDao transRoleDao;
    @Autowired
    private TransRoleDetialDao transRoleDetialDao;
    @Autowired
    private TransPrioritySelectionDao transPrioritySelectionDao;
    @Autowired
    private OperationUnitDao operationUnitDao;


    /**
     * 查询物流规则
     */
    @Override
    public Pagination<TransRoleCommand> findTransRole(int start, int pageSize, Sort[] sorts, Long chanId) {
        return transRoleDao.findTransRole(start, pageSize, sorts, new BeanPropertyRowMapper<TransRoleCommand>(TransRoleCommand.class), chanId);
    }

    /**
     * 查询商品信息
     */
    @Override
    public Pagination<TransRoleDetialCommand> findSkuRef(int start, int pageSize, Long roleDtalId, Sort[] sorts) {
        return transRoleDao.findSkuRef(start, pageSize, roleDtalId, new BeanPropertyRowMapper<TransRoleDetialCommand>(TransRoleDetialCommand.class), sorts);
    }

    /**
     * 查询商品信息
     */
    @Override
    public Pagination<TransRoleDetialCommand> findSkuCateRef(int start, int pageSize, Long roleDtalId, Sort[] sorts) {
        return transRoleDao.findSkuCateRef(start, pageSize, roleDtalId, new BeanPropertyRowMapper<TransRoleDetialCommand>(TransRoleDetialCommand.class), sorts);
    }

    /**
     * 查询商品信息
     */
    @Override
    public Pagination<TransRoleDetialCommand> findSkuTagRef(int start, int pageSize, Long roleDtalId, Sort[] sorts) {
        return transRoleDao.findSkuTagRef(start, pageSize, roleDtalId, new BeanPropertyRowMapper<TransRoleDetialCommand>(TransRoleDetialCommand.class), sorts);
    }

    @Override
    public Pagination<OperationUnitCommand> findTransRoleWH(int start, int pageSize, Long channelId, Long roleDtalId, Sort[] sorts) {
        return operationUnitDao.findChannelTransWh(start, pageSize, channelId, roleDtalId, new BeanPropertyRowMapper<OperationUnitCommand>(OperationUnitCommand.class), sorts);
    }



    /**
     * 保存物流规则
     */
    @Override
    public void saveTransRole(TransRoleCommand transRole, TransRoleDetial roleDetail, String skuList, String skuCateList, String skuTagList, String whList, Long isAdd, Long roleIds, Long roleDtalId) {
        TransportService service = new TransportService();
        TransRole roleCode = transRoleDao.findTransRoleByCode(transRole.getCode());
        if (isAdd == 1) {// 新增
            // 校验规则编码唯一
            if (roleCode != null) {
                throw new BusinessException(ErrorCode.TRANS_ROLE_IS_EXIT);
            } else {
                // 保存物流规则
                TransRole role = new TransRole();
                role.setCode(transRole.getCode());
                role.setName(transRole.getName());
                role.setSort(transRole.getSort());
                role.setCreateTime(new Date());
                if (transRole.getRoleStatus() == 1) {
                    role.setStatus(TransAreaGroupStatus.NORMAL);
                } else {
                    role.setStatus(TransAreaGroupStatus.DISABLE);
                }
                BiChannel channel = new BiChannel();
                channel.setId(transRole.getRoleChannelId());
                role.setChannel(channel);
                service.setId(transRole.getRoleServiceId());
                role.setTransService(service);
                transRoleDao.save(role);
                // 保存物流规则明细
                TransRole roleId = transRoleDao.findTransRoleByCode(transRole.getCode());
                TransRoleDetial roleDetials = new TransRoleDetial();
                roleDetials.setMinAmount(roleDetail.getMinAmount());
                roleDetials.setMaxAmount(roleDetail.getMaxAmount());
                roleDetials.setMinWeight(roleDetail.getMinWeight());
                roleDetials.setMaxWeight(roleDetail.getMaxWeight());
                roleDetials.setTimeType(roleDetail.getTimeType());// 设置明细时效类型

                roleDetials.setIsCod(roleDetail.getIsCod());
                roleDetials.setTransRole(roleId);
                roleDetials.setRemoveKeyword(StringUtil.isEmpty(roleDetail.getRemoveKeyword()) ? null : roleDetail.getRemoveKeyword().trim());
                TransAreaGroup group = new TransAreaGroup();
                if (transRole.getAreaGroupId() != null) {
                    group.setId(transRole.getAreaGroupId());
                    roleDetials.setAreaGroup(group);
                }
                transRoleDetialDao.save(roleDetials);
                TransRoleDetial roleDetialId = transRoleDetialDao.findRoleDetialByRoleId(roleId.getId());
                // 保存引用中间表信息
                savaRefInfo(skuList, skuCateList, skuTagList, whList, roleDetialId.getId());
            }
        } else {
            // 修改物流规则
            roleCode.setName(transRole.getName());
            roleCode.setSort(transRole.getSort());
            if (transRole.getRoleStatus() == 1) {
                roleCode.setStatus(TransAreaGroupStatus.NORMAL);
            } else {
                roleCode.setStatus(TransAreaGroupStatus.DISABLE);
            }
            service.setId(transRole.getRoleServiceId());
            roleCode.setTransService(service);
            transRoleDao.save(roleCode);
            // 修改物流规则明细
            TransRoleDetial roleSub = transRoleDetialDao.findRoleDetialByRoleId(roleIds);
            roleSub.setMinAmount(roleDetail.getMinAmount());
            roleSub.setMaxAmount(roleDetail.getMaxAmount());
            roleSub.setMinWeight(roleDetail.getMinWeight());
            roleSub.setMaxWeight(roleDetail.getMaxWeight());
            roleSub.setTimeType(roleDetail.getTimeType());// 设置明细时效类型


            roleSub.setRemoveKeyword(StringUtil.isEmpty(roleDetail.getRemoveKeyword()) ? null : roleDetail.getRemoveKeyword().trim());
            roleSub.setIsCod(roleDetail.getIsCod());
            roleSub.setTransRole(roleCode);
            TransAreaGroup group = new TransAreaGroup();
            if (transRole.getAreaGroupId() != null) {
                group.setId(transRole.getAreaGroupId());
                roleSub.setAreaGroup(group);
            }
            transRoleDetialDao.save(roleSub);
            // 中间表操作-- 先删除后新增
            transRoleDetialDao.deleteSkuRef(roleDtalId);
            transRoleDetialDao.deleteSkuCateRef(roleDtalId);
            transRoleDetialDao.deleteSkuTagRef(roleDtalId);
            transRoleDetialDao.deleteWhRef(roleDtalId);
            savaRefInfo(skuList, skuCateList, skuTagList, whList, roleDtalId);
        }
        transRoleDao.flush();
        transRoleDetialDao.flush();
    }

    /**
     * 保存渠道引用中间表信息
     * 
     * @param skuList
     * @param skuCateList
     * @param skuTagList
     * @param roleDId
     */
    public void savaRefInfo(String skuList, String skuCateList, String skuTagList, String whList, Long roleDId) {
        // 保存商品信息
        if (!skuList.equals("")) {
            String[] skuId = skuList.split("/");
            for (int i = 0; i < skuId.length; i++) {
                transRoleDetialDao.insertSkuRef(Long.parseLong(skuId[i]), roleDId);
            }
        }
        // 保存商品分类
        if (!skuCateList.equals("")) {
            String[] skuCateId = skuCateList.split("/");
            for (int j = 0; j < skuCateId.length; j++) {
                transRoleDetialDao.insertSkuCateRef(Long.parseLong(skuCateId[j]), roleDId);
            }
        }
        // 保存商品标签
        if (!skuTagList.equals("")) {
            String[] skuTagId = skuTagList.split("/");
            for (int k = 0; k < skuTagId.length; k++) {
                transRoleDetialDao.insertSkuTagRef(Long.parseLong(skuTagId[k]), roleDId);
            }
        }
        // 指定仓库
        if (!whList.equals("")) {
            String[] whIds = whList.split("/");
            for (int k = 0; k < whIds.length; k++) {
                transRoleDetialDao.insertWhRef(Long.parseLong(whIds[k]), roleDId);
            }
        }
    }

    /**
     * 保存默认物流规则
     */
    @Override
    public void saveDefaultTransRole(TransRoleCommand transRole, Long isAdd) {
        TransportService service = new TransportService();
        TransRole roleCode = transRoleDao.findTransRoleByCode(transRole.getCode());
        // 校验规则编码唯一
        if (isAdd == 3) {// 新增
            if (roleCode != null) {
                throw new BusinessException(ErrorCode.TRANS_ROLE_IS_EXIT);
            } else {
                // 保存物流规则
                TransRole role = new TransRole();
                role.setCode(transRole.getCode());
                role.setName(transRole.getName());
                role.setSort(9999);
                role.setCreateTime(new Date());
                if (transRole.getRoleStatus() == 1) {
                    role.setStatus(TransAreaGroupStatus.NORMAL);
                } else {
                    role.setStatus(TransAreaGroupStatus.DISABLE);
                }
                BiChannel channel = new BiChannel();
                channel.setId(transRole.getRoleChannelId());
                role.setChannel(channel);
                service.setId(transRole.getRoleServiceId());
                role.setTransService(service);
                transRoleDao.save(role);
            }
        } else {
            // 修改物流规则
            roleCode.setName(transRole.getName());
            if (transRole.getRoleStatus() == 1) {
                roleCode.setStatus(TransAreaGroupStatus.NORMAL);
            } else {
                roleCode.setStatus(TransAreaGroupStatus.DISABLE);
            }
            service.setId(transRole.getRoleServiceId());
            roleCode.setTransService(service);
            transRoleDao.save(roleCode);
        }

        transRoleDao.flush();
    }

    public void saveKeyWord(String includeKey, Long channelId) {
        String includeArray[] = includeKey.split(";");
        // String excludeArray[] = excludeKey.split(",");
        transPrioritySelectionDao.deleteAllTrasPriSel(channelId);
        for (int i = 0; i < includeArray.length; i++) {
            TransPrioritySelection transSel = new TransPrioritySelection();
            transSel.setKeyword(includeArray[i].trim());
            transSel.setChannelId(channelId);
            // transSel.setType(TransPriSelType.INCLUDE);
            transPrioritySelectionDao.save(transSel);
        }
        /*
         * for (int j = 0; j < excludeArray.length; j++) { TransPrioritySelection transSels = new
         * TransPrioritySelection(); transSels.setKeyword(excludeArray[j].trim());
         * transSels.setType(TransPriSelType.EXCLUDE); transPrioritySelectionDao.save(transSels); }
         */
        transPrioritySelectionDao.flush();
    }

    public String queryKeyWord() {
        List<TransPrioritySelection> transSel = transPrioritySelectionDao.findTrasPriSel(null, new BeanPropertyRowMapperExt<TransPrioritySelection>(TransPrioritySelection.class));
        String keyCode = "", inClude = "", exClude = "";
        for (TransPrioritySelection each : transSel) {
            if (each.getType().getValue() == TransPriSelType.INCLUDE.getValue()) {// 包含
                inClude += each.getKeyword() + ",";
            } else if (each.getType().getValue() == TransPriSelType.EXCLUDE.getValue()) {// 排除
                exClude += each.getKeyword() + ",";
            }
        }
        if (!StringUtil.isEmpty(inClude)) {
            inClude = inClude.substring(0, inClude.length() - 1);
        }
        if (!StringUtil.isEmpty(exClude)) {
            exClude = exClude.substring(0, exClude.length() - 1);
        }
        keyCode = inClude + "/" + exClude;
        return keyCode;
    }

    public String queryKeyWordForChannlId(Long channelId) {
        StringBuffer sb = new StringBuffer();
        List<TransPrioritySelection> transSel = transPrioritySelectionDao.findTrasPriSel(channelId, new BeanPropertyRowMapperExt<TransPrioritySelection>(TransPrioritySelection.class));
        for (TransPrioritySelection transSelList : transSel) {
            if (null != transSelList.getKeyword() && !"".equals(transSelList.getKeyword())) {
                sb.append(transSelList.getKeyword() + ";");
            }
        }
        String inClude = sb.toString();
        /*
         * if (!StringUtil.isEmpty(inClude)) { inClude = inClude.substring(0, inClude.length() - 1);
         * }
         */
        return inClude;
    }
}
