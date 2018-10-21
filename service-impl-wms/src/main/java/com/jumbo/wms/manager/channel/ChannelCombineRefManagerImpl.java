/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
 * 
 */
package com.jumbo.wms.manager.channel;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.BiChannelSpecialActionDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.warehouse.BiChannelCombineRefDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelCombineRef;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialActionType;
import com.jumbo.wms.model.baseinfo.BiChannelStatus;
import com.jumbo.wms.model.baseinfo.BiChannelType;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.warehouse.BiChannelCombineRefCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;

@Transactional
@Service("channelRefManager")
public class ChannelCombineRefManagerImpl extends BaseManagerImpl implements ChannelCombineRefManager {

    private static final long serialVersionUID = -1598252586812598808L;

    @Autowired
    private BiChannelCombineRefDao biChannelCombineRefDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private BiChannelSpecialActionDao biChannelSpecialActionDao;
    @Autowired
    private ChannelWhRefRefDao cwrrDao;

    /**
     * 查询合并发货渠道信息
     * 
     * @param start
     * @param pagesize
     * @param type
     * @param shopName
     * @param ouid
     * @param sorts
     * @return
     */
    public Pagination<BiChannelCombineRefCommand> findBiChannelCombineRef(int start, int pagesize, Sort[] sorts, Long ouId) {
        return biChannelCombineRefDao.findBiChannelCombineRef(start, pagesize, new BeanPropertyRowMapperExt<BiChannelCombineRefCommand>(BiChannelCombineRefCommand.class), sorts, ouId);
    }

    /**
     * 查询合并发货子渠道信息
     * 
     * @param start
     * @param pagesize
     * @param type
     * @param shopName
     * @param ouid
     * @param sorts
     * @return
     */
    public List<BiChannelCombineRefCommand> findBiChannelCombineChildrenRef(Long id, Long whId, Sort[] sorts, Long ouId) {
        return biChannelCombineRefDao.findBiChannelCombineChildrenRef(id, whId, new BeanPropertyRowMapperExt<BiChannelCombineRefCommand>(BiChannelCombineRefCommand.class), sorts, ouId);
    }

    /**
     * 新增单渠道
     */

    @Override
    public void addSingelChannels(Long whId, Date expDate, String chId) {
        String[] arrChId = chId.split("/");
        for (int i = 0; i < arrChId.length; i++) {
            BiChannelCombineRef bc = biChannelCombineRefDao.getByWhIdAndChId(whId, Long.parseLong(arrChId[i]));
            if (bc == null) {
                BiChannelCombineRef ref = new BiChannelCombineRef();
                BiChannel l1 = new BiChannel();
                BiChannel l2 = new BiChannel();
                OperationUnit u = new OperationUnit();
                u.setId(whId);
                u.setLastModifyTime(new Date());
                l1.setId(Long.parseLong(arrChId[i]));
                l2.setId(Long.parseLong(arrChId[i]));
                ref.setChannelId(l1);
                ref.setChildChannelId(l2);
                ref.setWhOuId(u);
                ref.setExpTime(expDate);
                ref.setCreateTime(new Date());
                biChannelCombineRefDao.save(ref);
            } else {
                throw new BusinessException(ErrorCode.CHANNEL_COMBINE_EXIST_ERROR);
            }
        }
        biChannelCombineRefDao.flush();
    }

    /**
     * 保存多渠道
     * 
     * @param defId 默认渠道ID
     * @param whId 仓库ID
     * @param expDate 过期时间
     * @param code 渠道唯一对接码
     * @param name 店铺名称
     * @param chId 子渠道ID集合
     */
    public void addMoreChannels(Long defId, Long whId, Date expDate, String code, String name, String zxShopName, String ydShopName, String chId) {
        // 保存渠道表
        BiChannelCommand bc = biChannelDao.findBiChannelById(defId, new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class));
        BiChannel bcl = new BiChannel();
        Customer r = new Customer();
        r.setId(Long.parseLong(bc.getCustomerId()));
        bcl.setCustomer(r);// 设置客户ID
        BiChannel bcode = biChannelDao.getByCode(code);
        BiChannel bname = biChannelDao.getByName(name);
        if (bcode == null) {
            bcl.setCode(code);
        } else {
            throw new BusinessException(ErrorCode.CHANNEL_COMBINE_CODE_EXIST_ERROR);
        }
        if (bname == null) {
            bcl.setName(name);
        } else {
            throw new BusinessException(ErrorCode.CHANNEL_COMBINE_NAME_EXIST_ERROR);
        }
        bcl.setTelephone(bc.getTelephone());
        bcl.setVersion(0);
        bcl.setAddress(bc.getAddress());
        bcl.setZipcode(bc.getZipcode());
        bcl.setRtnWarehouseAddress(bc.getRtnWarehouseAddress());
        bcl.setType(BiChannelType.VIRTUAL);
        bcl.setStatus(BiChannelStatus.NORMAL);
        bcl.setExpTime(expDate);
        bcl.setCreateTime(new Date());
        if (bc.getIsJdolOrder() == null || bc.getIsJdolOrder() == false) {
            bcl.setIsJdolOrder(false);
        } else {
            bcl.setIsJdolOrder(true);
        }
        if (bc.getIsSms() == null || bc.getIsSms() == false) {
            bcl.setIsSms(false);
        } else {
            bcl.setIsSms(true);
        }
        bcl.setLastModifyTime(new Date());
        biChannelDao.save(bcl);
        // 根据渠道ID保存渠道行为
        List<BiChannelSpecialAction> bsa = biChannelSpecialActionDao.getBiChannelSpecialActionByChId(defId, new BeanPropertyRowMapperExt<BiChannelSpecialAction>(BiChannelSpecialAction.class));
        BiChannel b2 = biChannelDao.getByCode(code);
        Long chanId = null;
        if (b2 == null) {
            throw new BusinessException(ErrorCode.CHANNEL_COMBINE_SAVE_ERROR);
        } else {
            // 建立渠道和仓库绑定关系
            chanId = b2.getId();
            ChannelWhRef ref = new ChannelWhRef();
            BiChannel shop = new BiChannel();
            OperationUnit ut = new OperationUnit();
            shop.setId(chanId);
            ut.setId(whId);
            ut.setLastModifyTime(new Date());
            ref.setShop(shop);
            ref.setWh(ut);
            ref.setIsDefaultInboundWh(false);
            cwrrDao.save(ref);
            // 保存渠道行为
            if (bsa.size() > 0) {
                for (BiChannelSpecialAction biChannelSpecialAction : bsa) {
                    String tempName1 = biChannelSpecialAction.getShopName();
                    if (!zxShopName.trim().equals("")) {
                        if (biChannelSpecialAction.getType() == BiChannelSpecialActionType.PRINT_PACKING_PAGE) {
                            tempName1 = zxShopName;
                        }
                    }
                    if (!ydShopName.trim().equals("")) {
                        if (biChannelSpecialAction.getType() == BiChannelSpecialActionType.PRINT_EXPRESS_BILL) {
                            tempName1 = ydShopName;
                        }
                    }
                    BiChannelSpecialAction ba = new BiChannelSpecialAction();
                    ba.setShopName(tempName1);
                    ba.setType(biChannelSpecialAction.getType());
                    ba.setIsDefaultSpecialPackage(biChannelSpecialAction.getIsDefaultSpecialPackage());
                    ba.setIsNotDisplaySum(biChannelSpecialAction.getIsNotDisplaySum());
                    ba.setInvoiceType(biChannelSpecialAction.getInvoiceType());
                    ba.setTemplateType(biChannelSpecialAction.getTemplateType());
                    ba.setRtnAddress(biChannelSpecialAction.getRtnAddress());
                    ba.setContactsPhone(biChannelSpecialAction.getContactsPhone());
                    ba.setContactsPhone1(biChannelSpecialAction.getContactsPhone1());
                    ba.setContacts(biChannelSpecialAction.getContacts());
                    ba.setRouteType(biChannelSpecialAction.getRouteType());
                    ba.setChanncel(b2);
                    biChannelSpecialActionDao.save(ba);
                }
            }
        }
        // 保存渠道合并信息
        String[] arrChId = chId.split("/");
        for (int i = 0; i < arrChId.length; i++) {
            BiChannelSpecialAction ba1 = biChannelSpecialActionDao.getBiChannelSpecialActionByType(Long.parseLong(arrChId[i]), new BeanPropertyRowMapperExt<BiChannelSpecialAction>(BiChannelSpecialAction.class));
            BiChannelSpecialAction ba2 = null;
            if (i + 1 < arrChId.length) {
                ba2 = biChannelSpecialActionDao.getBiChannelSpecialActionByType(Long.parseLong(arrChId[i + 1]), new BeanPropertyRowMapperExt<BiChannelSpecialAction>(BiChannelSpecialAction.class));
            }
            if (ba1 != null && ba2 != null) {
                if (ba1.getInvoiceType() != ba2.getInvoiceType()) {
                    throw new BusinessException(ErrorCode.CHANNEL_COMBINE_TYPE_ERROR);
                }
            }
            BiChannelCombineRef ref = new BiChannelCombineRef();
            BiChannel l1 = new BiChannel();
            BiChannel l2 = new BiChannel();
            OperationUnit u = new OperationUnit();
            u.setId(whId);
            l1.setId(chanId);
            l2.setId(Long.parseLong(arrChId[i]));
            ref.setChannelId(l1);
            ref.setChildChannelId(l2);
            ref.setWhOuId(u);
            ref.setExpTime(expDate);
            ref.setCreateTime(new Date());
            biChannelCombineRefDao.save(ref);
        }
        biChannelDao.flush();
    }

    /**
     * 修改单渠道
     */
    public void updateSingelChannels(Long id, Long chanId, Long whId, Date expDate, Long isCombine) {
        if (isCombine == 0) { // 不合并则删除数据
            biChannelCombineRefDao.deleteBiChannelCombineRefByChanId(chanId);
        } else {
            biChannelCombineRefDao.updateBiChannelCombineRefByChanId(whId, expDate, id);
        }
    }

    /**
     * 修改多渠道
     */
    public void updateMoreChannels(Long id, Long chanId, Long whId, String childChannelIdList, Date expDate, Long isCombine) {
        if (isCombine == 0) { // 不合并则删除数据
            biChannelCombineRefDao.deleteBiChannelCombineRefByChanId(chanId);
            biChannelDao.updateChannelStatusById(chanId); // 修改渠道类型为0，作废
        } else {
            biChannelCombineRefDao.updateBiChannelRefByChanId(whId, expDate, chanId);// 修改子渠道
            List<BiChannelCombineRefCommand> list = biChannelCombineRefDao.getBiChannelCombineRefByChanId(chanId, new BeanPropertyRowMapperExt<BiChannelCombineRefCommand>(BiChannelCombineRefCommand.class));
            Map<Long, Long> map1 = new HashMap<Long, Long>(); // 存放子渠道数据，默认删除
            Map<String, String> map2 = new HashMap<String, String>();// 存放子渠道数据，默认新增
            for (BiChannelCombineRefCommand bi : list) {
                map1.put(bi.getHbChId(), bi.getId()); // key: 子渠道id
            }
            String arrayChild[] = childChannelIdList.split("/");
            for (int i = 0; i < arrayChild.length; i++) {
                Long chId = Long.parseLong(arrayChild[i]);
                if (map1.containsKey(chId)) {
                    map1.remove(chId);
                } else {
                    map2.put(arrayChild[i], "temp");
                }
            }
            if (map1.size() > 0) { // 删除子渠道数据
                Iterator<Long> it = map1.keySet().iterator();
                while (it.hasNext()) {
                    biChannelCombineRefDao.deleteByPrimaryKey(map1.get(it.next()));
                }
            }
            if (map2.size() > 0) { // 新增子渠道数据
                Iterator<String> itor = map2.keySet().iterator();
                while (itor.hasNext()) {
                    BiChannelCombineRef ref = new BiChannelCombineRef();
                    BiChannel l1 = new BiChannel();
                    BiChannel l2 = new BiChannel();
                    OperationUnit u = new OperationUnit();
                    u.setId(whId);
                    u.setLastModifyTime(new Date());
                    l1.setId(chanId);
                    l2.setId(Long.parseLong(itor.next()));
                    ref.setChannelId(l1);
                    ref.setChildChannelId(l2);
                    ref.setWhOuId(u);
                    ref.setExpTime(expDate);
                    ref.setCreateTime(new Date());
                    biChannelCombineRefDao.save(ref);
                }
            }
            cwrrDao.updateChannelWhRefByChannelId(whId, chanId);
        }
        biChannelDao.flush();
    }

}
