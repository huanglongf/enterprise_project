/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.warehouse.AutoPickingListRoleDao;
import com.jumbo.dao.warehouse.AutoPickingListRoleDetailDao;
import com.jumbo.dao.warehouse.SkuSizeConfigDao;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRole;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleDetail;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleDetailCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationPickingType;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("autoPickingListRoleManager")
public class AutoPickingListRoleManagerImpl implements AutoPickingListRoleManager {
    private static final long serialVersionUID = 1L;
    @Autowired
    private AutoPickingListRoleDao autoPickingListRoleDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;
    @Autowired
    private SkuSizeConfigDao skuSizeConfigDao;
    @Autowired
    private AutoPickingListRoleDetailDao autoPickingListRoleDetailDao;

    @Override
    public Pagination<AutoPickingListRoleCommand> findAutoPLRoleByPagination(AutoPickingListRole role, Integer status, int start, int pageSize, Sort[] sorts) {
        List<Integer> statusList = new ArrayList<Integer>();
        if (null == status) {
            statusList.add(AutoPickingListRoleStatus.NORMAL.getValue());
            statusList.add(AutoPickingListRoleStatus.FORBID.getValue());
        } else
            statusList.add(status);
        String roleName = null;
        String roleCode = null;
        if (null != role) {
            if (null != role.getCode() && !"".equals(role.getCode())) roleCode = role.getCode();
            if (null != role.getName() && !"".equals(role.getName())) roleName = role.getName();
        }
        return autoPickingListRoleDao.findAutoPLRoleByPagination(start, pageSize, roleCode, roleName, statusList, sorts, new BeanPropertyRowMapper<AutoPickingListRoleCommand>(AutoPickingListRoleCommand.class));
    }

    @Override
    public boolean findAutoPLRoleExistByCode(String code) {
        boolean ret = false;
        String roleCode = code;
        if (null != roleCode || !"".equals(roleCode)) roleCode = roleCode.toUpperCase();
        long i = autoPickingListRoleDao.findAutoPLRoleCountByCode(roleCode, new SingleColumnRowMapper<Long>(Long.class));
        if (i > 0) ret = true;
        return ret;
    }

    @Override
    public void saveAutoPLRole(AutoPickingListRole role, Integer status) {
        long id = role.getId();
        if (id > 0) {
            AutoPickingListRole plRole = autoPickingListRoleDao.getByPrimaryKey(id);
            if (null != plRole) {
                plRole.setName(role.getName());
                plRole.setStatus(AutoPickingListRoleStatus.valueOf(status));
                autoPickingListRoleDao.save(plRole);
                autoPickingListRoleDao.flush();
            } else {
                AutoPickingListRole plR = new AutoPickingListRole();
                String code = role.getCode();
                code = code.toUpperCase();
                plR.setCode(code);
                plR.setName(role.getName());
                plR.setStatus(AutoPickingListRoleStatus.valueOf(status));
                plR.setCreateTime(new Date());
                autoPickingListRoleDao.save(plR);
                autoPickingListRoleDao.flush();
            }
        } else {
            AutoPickingListRole plRole = new AutoPickingListRole();
            String code = role.getCode();
            code = code.toUpperCase();
            plRole.setCode(code);
            plRole.setName(role.getName());
            plRole.setStatus(AutoPickingListRoleStatus.valueOf(status));
            plRole.setCreateTime(new Date());
            autoPickingListRoleDao.save(plRole);
            autoPickingListRoleDao.flush();
        }
    }

    @Override
    public AutoPickingListRole findAutoPLRoleByCode(String code) {
        if (null != code && !"".equals(code)) code = code.toUpperCase();
        return autoPickingListRoleDao.findAutoPLRoleByCode(code);
    }

    @Override
    public Pagination<AutoPickingListRoleDetailCommand> findAllPLRoleDetailByRoleId(Long roleId, int start, int pageSize, Sort[] sorts) {
        if (null == roleId) roleId = -1L;
        return autoPickingListRoleDao.findAllPLRoleDetailByRoleId(start, pageSize, roleId, sorts, new BeanPropertyRowMapper<AutoPickingListRoleDetailCommand>(AutoPickingListRoleDetailCommand.class));
    }

    @Override
    public void saveAutoPLRoleDetail(AutoPickingListRole role, Integer status, List<AutoPickingListRoleDetailCommand> roleDetailList) {
        saveAutoPLRole(role, status);
        // autoPickingListRoleDao.deletePLRoleDetail(role.getId());
        // 原规则明细
        String oldAprd = "";// 原规则明细
        String newAprd = "";//
        // if (null != roleDetailList) {
        List<AutoPickingListRoleDetailCommand> aplrdList = autoPickingListRoleDao.getAutoPickingListRoleDetial(role.getId(), new BeanPropertyRowMapper<AutoPickingListRoleDetailCommand>(AutoPickingListRoleDetailCommand.class));
        for (AutoPickingListRoleDetailCommand a : aplrdList) {
            oldAprd += a.getId() + "-";
        }
        if (null != roleDetailList) {
            for (AutoPickingListRoleDetailCommand d : roleDetailList) {
                if (d.getId() != null) {
                    newAprd += d.getId() + "-";
                }
            }
        }
        String delAprd = "";
        if (aplrdList.size() > 0) {
            // 获得需要删除的明细
            if (!newAprd.equals("")) {
                delAprd = romove(toArrayList(oldAprd.substring(0, oldAprd.length() - 1).split("-")), toArrayList(newAprd.substring(0, newAprd.length() - 1).split("-"))).toString();
                String[] delAprdList = delAprd.substring(1, delAprd.length() - 1).split(",");
                for (String string : delAprdList) {
                    if (!string.equals("")) {
                        // 删除规则明细&规则明细对应物流商
                        autoPickingListRoleDao.deletePLRoleDetailById(Long.valueOf(string));
                        autoPickingListRoleDao.deletePLRoleDetailTranById(Long.valueOf(string));
                    }
                }
            } else {
                String[] delAprdList = oldAprd.substring(0, oldAprd.length() - 1).split("-");
                for (String string : delAprdList) {
                    if (!string.equals("")) {
                        // 删除规则明细&规则明细对应物流商
                        autoPickingListRoleDao.deletePLRoleDetailById(Long.valueOf(string));
                        autoPickingListRoleDao.deletePLRoleDetailTranById(Long.valueOf(string));
                    }
                }
            }
        }
        if (null != roleDetailList) {
            for (AutoPickingListRoleDetailCommand detail : roleDetailList) {
                if (detail.getId() == null) {
                    AutoPickingListRoleDetail aprd = new AutoPickingListRoleDetail();
                    Long roleId = detail.getRoleId();
                    Long skuSizeId = detail.getSkuSizeId();
                    Long skuCategoryId = detail.getSkuCategoryId();
                    Integer pickingType = detail.getType();
                    Integer sort = detail.getSort();
                    Integer maxOrder = detail.getMaxOrder();
                    Integer minOrder = detail.getMinOrder();
                    String sendCity = null;
                    if (StringUtils.hasText(detail.getCity())) {
                        sendCity = detail.getCity();
                    }
                    boolean isSn = false;
                    if (StringUtils.hasText(detail.getIsSnString())) {
                        if (detail.getIsSnString().equals("true")) {
                            isSn = true;
                            aprd.setIsSn(isSn);
                        }
                        if (detail.getIsSnString().equals("false")) {
                            aprd.setIsSn(isSn);
                        }
                    }
                    boolean isTransAfter = false;
                    if (detail.getIsTransAfter()) {
                        isTransAfter = true;
                    }
                    aprd.setSort(sort);
                    aprd.setCity(sendCity);
                    aprd.setIsTransAfter(isTransAfter);
                    aprd.setMaxOrder(maxOrder);
                    aprd.setMinOrder(minOrder);
                    aprd.setPickingType(StockTransApplicationPickingType.valueOf(pickingType));
                    aprd.setAutoPickingListRole(autoPickingListRoleDao.getByPrimaryKey(roleId));
                    if (skuCategoryId != null) {
                        aprd.setSkuCategories(skuCategoriesDao.getByPrimaryKey(skuCategoryId));
                    }
                    if (skuSizeId != null) {
                        aprd.setSkuSizeConfig(skuSizeConfigDao.getByPrimaryKey(skuSizeId));
                    }
                    autoPickingListRoleDetailDao.save(aprd);
                    if (StringUtils.hasText(detail.getMaTList())) {
                        String[] maT = detail.getMaTList().split(",");
                        for (int i = 0; i < maT.length; i++) {
                            autoPickingListRoleDao.insertRoleTran(aprd.getId(), Long.valueOf(maT[i]));
                        }
                    }
                    // autoPickingListRoleDao.insertPLRoleDetail(roleId, skuSizeId, skuCategoryId,
                    // pickingType, sort, maxOrder, minOrder, sendCity, isSn, isTransAfter);
                }
            }
        }
        // }
    }

    @Override
    public List<Transportator> findTransportatorList() {
        List<Transportator> list = transportatorDao.findTransportatorList(new BeanPropertyRowMapperExt<Transportator>(Transportator.class));
        return list;
    }

    @Override
    public List<Transportator> findTransportatorListAll() {
        List<Transportator> list = transportatorDao.findTransportatorListAll(new BeanPropertyRowMapperExt<Transportator>(Transportator.class));
        return list;
    }

    @Override
    public List<TransportatorCommand> findTransportatorListByAll(String lpCode, String lpName, Integer status, Integer isCod) {
        if (!StringUtils.hasText(lpCode)) {
            lpCode = null;
        }
        if (!StringUtils.hasText(lpName)) {
            lpName = null;
        }
        List<TransportatorCommand> list = transportatorDao.findTransportatorListByAll(lpCode, lpName, status, isCod, new BeanPropertyRowMapperExt<TransportatorCommand>(TransportatorCommand.class));
        return list;
    }


    public static List<String> toArrayList(String[] temp) {
        List<String> templist = new ArrayList<String>();
        for (int i = 0; i < temp.length; i++) {
            templist.add(temp[i]);
        }
        return templist;
    }

    public static List<String> romove(List<String> lista, List<String> listb) {
        lista.removeAll(listb);
        return lista;
    }

    public static List<String> compareArr(String[] a, String[] b) {
        List<String> commonlist = new ArrayList<String>();
        if (a.length < b.length) {
            for (int i = 0; i < a.length; i++) {
                if (a[i].equals(b[i])) commonlist.add(a[i]);
            }
        }
        return commonlist;
    }


}
