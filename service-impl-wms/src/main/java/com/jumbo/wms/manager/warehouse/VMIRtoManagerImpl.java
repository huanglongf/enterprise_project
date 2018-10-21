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
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.defaultData.VmiRtoDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("vmiRtoManager")
public class VMIRtoManagerImpl extends BaseManagerImpl implements VMIRtoManager {

    private static final long serialVersionUID = -3711557113010831825L;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private VmiRtoDao vmiRtoDao;
    @Autowired
    private VmiRtoLineDao vmiRtoLineDao;

    /** 
     *
     */
    @Override
    public Pagination<VmiRtoCommand> findVmiRtoList(int start, int pageSize, VmiRtoCommand cmd, Long ouid, Sort[] sorts) {
        List<String> vmiCodeList = new ArrayList<String>();
        Long channelId = null;
        if (null != cmd) {
            channelId = cmd.getChannelId();
        } else {
            cmd = new VmiRtoCommand();
        }
        if (null == channelId) {
            List<BiChannel> channelList = biChannelDao.getAllRefShopByDefaultWhOuId(ouid);
            for (BiChannel c : channelList) {
                if (!StringUtil.isEmpty(c.getVmiCode())) {
                    vmiCodeList.add(c.getVmiCode());
                }
            }
        } else {
            BiChannel biChannel = biChannelDao.getByPrimaryKey(channelId);
            if (!StringUtil.isEmpty(biChannel.getVmiCode())) {
                vmiCodeList.add(biChannel.getVmiCode());
            }
        }
        String orderCode = StringUtil.isEmpty(cmd.getOrderCode()) ? null : StringUtils.trim(cmd.getOrderCode());
        return vmiRtoDao.findVmiRtoListByPage(start, pageSize, cmd.getStartDate(), cmd.getEndDate(), cmd.getIntStatus(), orderCode, vmiCodeList, new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
    }

    /** 
     *
     */
    @Override
    public List<VmiRtoLineCommand> findVmiRtoLineList(VmiRtoCommand cmd) {
        Long rtoId = cmd.getId();
        if (null == rtoId) {
            return null;
        }
        return vmiRtoLineDao.findRtoLineListByRtoId(rtoId, new BeanPropertyRowMapper<VmiRtoLineCommand>(VmiRtoLineCommand.class));
    }

}
