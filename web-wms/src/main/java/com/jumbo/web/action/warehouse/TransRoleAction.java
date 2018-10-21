package com.jumbo.web.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.TransRoleManager;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.trans.TransRoleCommand;
import com.jumbo.wms.model.trans.TransRoleDetial;
import com.jumbo.wms.model.trans.TransRoleDetialCommand;

/**
 * 物流规则
 * 
 * @author xiaolong.efi
 * 
 */
public class TransRoleAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3277769175547150094L;
    private TransRoleCommand transRole;// 物流规则
    private TransRoleDetial roleDetail;// 规则明细
    private String skuList;// 商品ID集合
    private String whList;// 关联仓库
    private String skuCateList;// 商品分类ID集合
    private String sktTagList;// 商品标签ID集合
    private Long chanId;// 渠道ID
    private String roleCode;// 规则编码
    private String status;// 状态
    private Long roleDtalId;// 物流规则明细ID
    private Long isAdd;// 判断新增或修改 1：新增，0：修改
    private Long roleId;// 规则ID
    private String includeKey;// 包含关键字
    private String exIncludeKey;// 排除关键字
    private String isCod;
    private Long channelId; // 店铺id
    @Autowired
    private TransRoleManager transRoleManager;


    public String findChannelByType() {
        return SUCCESS;
    }

    public String findExpress() {
        return SUCCESS;
    }

    /**
     * 查询物流规则
     * 
     * @return
     */
    public String findTransRole() {
        setTableConfig();
        Pagination<TransRoleCommand> role = transRoleManager.findTransRole(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), chanId);
        request.put(JSON, toJson(role));
        return JSON;
    }

    /**
     * 查询关联商品信息
     * 
     * @return
     */
    public String findSkuRef() {
        setTableConfig();
        Pagination<TransRoleDetialCommand> role = transRoleManager.findSkuRef(tableConfig.getStart(), tableConfig.getPageSize(), roleDtalId, tableConfig.getSorts());
        request.put(JSON, toJson(role));
        return JSON;
    }

    /**
     * 查询关联商品分类信息
     * 
     * @return
     */
    public String findSkuCateRef() {
        setTableConfig();
        Pagination<TransRoleDetialCommand> role = transRoleManager.findSkuCateRef(tableConfig.getStart(), tableConfig.getPageSize(), roleDtalId, tableConfig.getSorts());
        request.put(JSON, toJson(role));
        return JSON;
    }

    /**
     * 查询关联商品标签信息
     * 
     * @return
     */
    public String findSkuTagRef() {
        setTableConfig();
        Pagination<TransRoleDetialCommand> role = transRoleManager.findSkuTagRef(tableConfig.getStart(), tableConfig.getPageSize(), roleDtalId, tableConfig.getSorts());
        request.put(JSON, toJson(role));
        return JSON;
    }

    /**
     * 获取渠道快递规则对应仓库列表
     * 
     * @return
     */
    public String findTransRoleWH() {
        setTableConfig();
        Pagination<OperationUnitCommand> wh = transRoleManager.findTransRoleWH(tableConfig.getStart(), tableConfig.getPageSize(), chanId, roleDtalId, tableConfig.getSorts());
        request.put(JSON, toJson(wh));
        return JSON;
    }

    /**
     * 保存、修改 物流规则
     * 
     * @return
     */
    public String saveTransRole() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        if (roleDetail != null) {
            if ("0".equals(isCod)) {
                roleDetail.setIsCod(false);
            } else if ("1".equals(isCod)) {
                roleDetail.setIsCod(true);
            } else {
                roleDetail.setIsCod(null);
            }
        }


        try {
            transRoleManager.saveTransRole(transRole, roleDetail, skuList, skuCateList, sktTagList, whList, isAdd, roleId, roleDtalId);
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 保存默认物流规则
     * 
     * @return
     */
    public String saveDefaultTransRole() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            transRoleManager.saveDefaultTransRole(transRole, isAdd);
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 保存EMS关键字
     * 
     * @return
     * @throws JSONException
     */
    public String saveKeyWord() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            transRoleManager.saveKeyWord(includeKey, channelId);
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询EMS关键字
     * 
     * @return
     * @throws JSONException
     */
    public String queryKeyWord() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            String emskey = transRoleManager.queryKeyWord();
            result.put("msg", SUCCESS);
            result.put("key", emskey);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    public String queryKeyWordForChannlId() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        String keyWord = transRoleManager.queryKeyWordForChannlId(channelId);
        try {
            result.put("msg", SUCCESS);
            result.put("key", keyWord);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    public TransRoleCommand getTransRole() {
        return transRole;
    }

    public void setTransRole(TransRoleCommand transRole) {
        this.transRole = transRole;
    }

    public TransRoleDetial getRoleDetail() {
        return roleDetail;
    }

    public void setRoleDetail(TransRoleDetial roleDetail) {
        this.roleDetail = roleDetail;
    }

    public String getSkuList() {
        return skuList;
    }

    public void setSkuList(String skuList) {
        this.skuList = skuList;
    }

    public String getSkuCateList() {
        return skuCateList;
    }

    public void setSkuCateList(String skuCateList) {
        this.skuCateList = skuCateList;
    }

    public String getSktTagList() {
        return sktTagList;
    }

    public void setSktTagList(String sktTagList) {
        this.sktTagList = sktTagList;
    }

    public Long getChanId() {
        return chanId;
    }

    public void setChanId(Long chanId) {
        this.chanId = chanId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRoleDtalId() {
        return roleDtalId;
    }

    public void setRoleDtalId(Long roleDtalId) {
        this.roleDtalId = roleDtalId;
    }

    public Long getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Long isAdd) {
        this.isAdd = isAdd;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getIncludeKey() {
        return includeKey;
    }

    public void setIncludeKey(String includeKey) {
        this.includeKey = includeKey;
    }

    public String getExIncludeKey() {
        return exIncludeKey;
    }

    public void setExIncludeKey(String exIncludeKey) {
        this.exIncludeKey = exIncludeKey;
    }

    public String getIsCod() {
        return isCod;
    }

    public void setIsCod(String isCod) {
        this.isCod = isCod;
    }

    public String getWhList() {
        return whList;
    }

    public void setWhList(String whList) {
        this.whList = whList;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }


}
