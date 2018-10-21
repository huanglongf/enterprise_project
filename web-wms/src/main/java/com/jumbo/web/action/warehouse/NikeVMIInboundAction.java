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
 * 
 */
package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WhmanagerNotTransactional;
import com.jumbo.wms.model.command.NikeVMIInboundCommand;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;


/**
 * nike Vmi创单
 * 
 * @author dailingyun
 * 
 */
public class NikeVMIInboundAction extends BaseJQGridProfileAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = 433887006269649461L;

    @Autowired
    private WhmanagerNotTransactional whmanagerNotTransactional;
    
    private NikeVMIInboundCommand vmi;
    
    private Long shopId;
    
    private File file;

    
    public String nikeVmiInbound() {
        return SUCCESS;
    }

    /**
     * 查询NIKE调整同步信息
     * @return
     */
    public String findNikeVMIInbound() {
        setTableConfig();
        return JSON;
    }

    /**
     * 入库确认数量导入
     * 
     * @return
     * @throws Exception
     */
    public String importCreateNikeVMIInbound() throws Exception {
        String msg = SUCCESS;
        Map<String, List<NikeVMIInboundCommand>> errorMap = new HashMap<String, List<NikeVMIInboundCommand>>();
        Map<String, List<NikeVMIInboundCommand>> successMap  = new HashMap<String, List<NikeVMIInboundCommand>>();
        request.put("msg", msg);
        if (file == null) {
            msg = " The upload file must not be null";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                whmanagerNotTransactional.importCreateNikeVMIInbound(file, userDetails.getCurrentOu().getId(),userDetails.getUser(),shopId,errorMap,successMap);
            } catch (BusinessException e) {
                request.put("msg", "error");
                log.error("",e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", "error");
                log.error("",e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        List<NikeVMIInboundCommand> errorList = getComInfo(errorMap);
        request.put("errors", JsonUtil.collection2json(errorList));
        List<NikeVMIInboundCommand> successList = getComInfo(successMap);
        request.put("successList", JsonUtil.collection2json(successList));
        return SUCCESS;
    }
    
    private List<NikeVMIInboundCommand> getComInfo(Map<String, List<NikeVMIInboundCommand>> map){
        List<NikeVMIInboundCommand> result = new ArrayList<NikeVMIInboundCommand>();
        for(String key : map.keySet()){
            List<NikeVMIInboundCommand> list = map.get(key);
            for(int i = 0; i < list.size(); i++){
                getErrorInfo(list.get(i));
                result.add(list.get(i));
            }
        }
        return result;
    }
    
    private void getErrorInfo(NikeVMIInboundCommand com){
        StringBuffer sb = new StringBuffer();
        Map<Integer, Object[]> errors = com.getErrors();
        for(Integer key : errors.keySet()){
            sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + key,errors.get(key))+"　");
        }
        if(sb.length() > 0){
            com.setErrorMsg(sb.toString());
        } 
    }
    

    public NikeVMIInboundCommand getVmi() {
        return vmi;
    }

    public void setVmi(NikeVMIInboundCommand vmi) {
        this.vmi = vmi;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    
}
