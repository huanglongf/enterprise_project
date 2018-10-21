package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.SkuTagDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.trans.SkuTag;
import com.jumbo.wms.model.trans.SkuTagCommand;
import com.jumbo.wms.model.trans.SkuTagStatus;
import com.jumbo.wms.model.trans.SkuTagType;

/**
 * 商品标签
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
@Service("skuTagManager")
public class SkuTagManagerImpl extends BaseManagerImpl implements SkuTagManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Autowired
    private SkuTagDao skuTagDao;
    @Autowired
    private SkuDao skuDao;

    /**
     * 查找所有正常商品标签
     */
    @Override
    public Pagination<SkuTagCommand> findSkuTag(int start, int pageSize, SkuTag tag, Sort[] sorts) {
        String tagCode = null;
        String tagName = null;
        if (tag != null) {
            if (tag.getCode() != null && !tag.getCode().equals("")) {
                tagCode = tag.getCode();
            }
            if (tag.getName() != null && !tag.getName().equals("")) {
                tagName = tag.getName();
            }
        }
        return skuTagDao.findSkuTag(start, pageSize, tagCode, tagName, sorts, new BeanPropertyRowMapper<SkuTagCommand>(SkuTagCommand.class));
    }

    /** 
	 *
	 */
    @Override
    public Pagination<SkuTagCommand> findSkuTagByPagination(SkuTag tag, Integer status, int start, int pageSize, Sort[] sorts) {
        List<Integer> statusList = new ArrayList<Integer>();
        if (null == status) {
            statusList.add(SkuTagStatus.NORMAL.getValue());
            statusList.add(SkuTagStatus.CANCEL.getValue());
        } else
            statusList.add(status);
        String tagName = null;
        String tagCode = null;
        if (null != tag) {
            if (null != tag.getCode() && !"".equals(tag.getCode())) tagCode = tag.getCode();
            if (null != tag.getName() && !"".equals(tag.getName())) tagName = tag.getName();
        }

        return skuTagDao.findSkuTagByPagination(start, pageSize, tagCode, tagName, statusList, sorts, new BeanPropertyRowMapper<SkuTagCommand>(SkuTagCommand.class));
    }

    /**
     * 商品标签编码是否存在
     */
    @Override
    public boolean findSkuTagExistByCode(SkuTag tag) {
        boolean ret = false;
        String tagCode = tag.getCode();
        if (null != tagCode || !"".equals(tagCode)) tagCode = tagCode.toUpperCase();
        long i = skuTagDao.findSkuTagCountByCode(tagCode, new SingleColumnRowMapper<Long>(Long.class));
        if (i > 0) ret = true;
        return ret;
    }

    /**
     * 保存商品标签
     */
    @Override
    public void saveSkuTag(SkuTag tag, Integer status, Integer type) {
        long id = tag.getId();
        if (id > 0) {
            SkuTag st = skuTagDao.getByPrimaryKey(id);
            if (null != st) {
                st.setName(tag.getName());
                st.setStatus(SkuTagStatus.valueOf(status));
                st.setType(SkuTagType.valueOf(type));
                skuTagDao.save(st);
                skuTagDao.flush();
            } else {
                SkuTag skuTag = new SkuTag();
                String tagCode = tag.getCode();
                tagCode = tagCode.toUpperCase();
                skuTag.setCode(tagCode);
                skuTag.setName(tag.getName());
                skuTag.setStatus(SkuTagStatus.valueOf(status));
                skuTag.setType(SkuTagType.valueOf(type));
                skuTag.setCreateTime(new Date());
                skuTagDao.save(skuTag);
                skuTagDao.flush();
            }
        } else {
            SkuTag skuTag = new SkuTag();
            String tagCode = tag.getCode();
            tagCode = tagCode.toUpperCase();
            skuTag.setCode(tagCode);
            skuTag.setName(tag.getName());
            skuTag.setStatus(SkuTagStatus.valueOf(status));
            skuTag.setType(SkuTagType.valueOf(type));
            skuTag.setCreateTime(new Date());
            skuTagDao.save(skuTag);
            skuTagDao.flush();
        }
    }

    /**
     * 获取关联Sku
     */
    @Override
    public Pagination<SkuCommand> findAllSkuByTagId(SkuTag tag, int start, int pageSize, Sort[] sorts) {
        long tagId = -1;
        if (null != tag.getId()) tagId = tag.getId();
        return skuDao.findAllSkuByTagId(start, pageSize, tagId, sorts, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }

    /**
     * 查询商品
     */
    public Pagination<SkuCommand> findAllSkuRef(SkuTag tag, SkuCommand skuCmd, int start, int pageSize, Sort[] sorts) {
        Map<String, Object> param = (null == skuCmd ? new HashMap<String, Object>() : skuCmd.getSkuInfoMap());
        if (null != skuCmd) {
            Customer customer = skuCmd.getCustomer();
            if (null != customer && null != customer.getId()) {
                long customerId = customer.getId();
                param.put("customerId", customerId);
            }
        }
        if (null != tag && null != tag.getId()) {
            long tagId = tag.getId();
            param.put("tagId", tagId);
        }
        return skuDao.findAllSkuRef(start, pageSize, param, sorts, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }

    /**
     * 根据编码查询商品标签
     */
    @Override
    public SkuTag findSkuTagByCode(String tagCode) {
        if (null != tagCode && !"".equals(tagCode)) tagCode = tagCode.toUpperCase();
        return skuTagDao.findSkuTagByCode(tagCode);
    }

}
