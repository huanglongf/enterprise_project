package com.jumbo.wms.manager.task.vmi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoDao;
import com.jumbo.dao.vmi.gucciData.GucciSalesInventoryDao;
import com.jumbo.dao.vmi.gucciData.GucciVMIInFeedbackDao;
import com.jumbo.dao.vmi.gucciData.GucciVMIInFeedbackLineDao;
import com.jumbo.dao.vmi.gucciData.GucciVMIRtnFBDao;
import com.jumbo.dao.vmi.gucciData.GucciVMIRtnFBLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.GucciTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.gucciData.GucciManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.vmi.GucciData.DataResponse;
import com.jumbo.wms.model.vmi.GucciData.GucciRespose;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIInBound;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIInFB;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIInFBLine;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIInFeedback;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIInFeedbackLine;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIRtnFB;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIRtnFBLine;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIRtnFeedback;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIRtnFeedbackLine;

import loxia.dao.Pagination;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
@Service("gucciTask")
public class GucciTaskImpl extends BaseManagerImpl implements GucciTask {

    private static final long serialVersionUID = -1499344068941620033L;
    protected static final Logger log = LoggerFactory.getLogger(GucciTaskImpl.class);

    @Autowired
    private VmiFactory f;
    // @Autowired
    // private GucciVMIInstructionDao gucciVMIInstructionDao;
    // @Autowired
    // private GucciVMIInstructionLineDao gucciVMIInstructionLineDao;
    @Autowired
    private GucciVMIInFeedbackDao gucciVMIInFeedbackDao;
    @Autowired
    private GucciVMIInFeedbackLineDao gucciVMIInFeedbackLineDao;
    @Autowired
    private VmiRtoDao vmiRtoDao;
    @Autowired
    private GucciManager gucciManager;
    @Autowired
    private GucciVMIRtnFBDao gucciVMIRtnFBDao;
    @Autowired
    private GucciVMIRtnFBLineDao gucciVMIRtnFBLineDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private GucciSalesInventoryDao gucciSalesInventoryDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    /**
     * 根据入库指令JDADocumentNo生成入库单
     */
    @Override
    public GucciRespose saveGucciVMIInstructions(String systemKey, GucciVMIInBound gucciVMIInBound) {
        // GucciRespose rep = new GucciRespose();
        // rep.setErrorCode(ErrorCode.SYSTEM_ERROR + "");
        // rep.setSuccess(false);
        // if (gucciVMIInBound == null) {
        // rep.setErrorMsg("请求参数为空！");
        // return rep;
        // }
        // if (!StringUtils.equals(Constants.GUCCI_SYSTEM_KEY, systemKey)) {
        // rep.setErrorMsg("systemKey不为gucci，请检查！");
        // return rep;
        // }
        // List<GucciVMIInBoundLine> lines = gucciVMIInBound.getGucciVMIInBoundLines();
        // if (lines == null || lines.isEmpty()) {
        // rep.setErrorMsg("入库指令明细行为空！");
        // return rep;
        // }
        // // 明细重复校验
        // if (lines != null && !lines.isEmpty()) {
        // HashSet<String> set = new HashSet<String>();
        // for (GucciVMIInBoundLine line : lines) {
        // set.add(line.getRowNumberDetail());
        // }
        // if (set.size() != lines.size()) {
        // rep.setErrorMsg("入库指令明细行重复性校验未通过！");
        // return rep;
        // }
        // }
        // GucciVMIInstruction vmiInstruction =
        // gucciVMIInstructionDao.findByJDADocumentNo(gucciVMIInBound.getJDADocumentNumber());
        // if (vmiInstruction != null) {
        // rep.setErrorMsg("该入库指令已存在，不允许重复发送！");
        // return rep;
        // }
        // try {
        // vmiInstruction = new GucciVMIInstruction();
        // BeanUtils.copyProperties(gucciVMIInBound, vmiInstruction, new String[]
        // {"gucciVMIInBoundLines"});
        // vmiInstruction.setStatus(0);
        // gucciVMIInstructionDao.save(vmiInstruction);
        // if (lines != null && !lines.isEmpty()) {
        // for (GucciVMIInBoundLine line : lines) {
        // GucciVMIInstructionLine l = new GucciVMIInstructionLine();
        // BeanUtils.copyProperties(line, l);
        // l.setGucciVMIInstruction(vmiInstruction);
        // gucciVMIInstructionLineDao.save(l);
        // }
        // }
        // rep.setSuccess(true);
        // } catch (Exception e) {
        // log.error("gucci入库指令保存异常！JDADocumentNumber:" + gucciVMIInBound.getJDADocumentNumber());
        // }
        // return rep;
        return null;
    }

    /**
     * 生成VMI入库作业单
     */
    @Override
    // @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateStaByInstruction() {
        // vmigucci = f.getBrandVmi(Constants.GUCCI_BRAND_VMI_CODE);
        // vmigucci.generateInboundSta();
    }

    /**
     * 提供给hub的gucci入库反馈信息/退货入库反馈信息
     */
    public DataResponse<GucciVMIInFB> feedbackInboundInfo(String systemKey, String request) {
        DataResponse<GucciVMIInFB> rep = new DataResponse<GucciVMIInFB>();
        rep.setStatus("0");
        if (!StringUtils.equals(systemKey, Constants.GUCCI_SYSTEM_KEY)) {
            rep.setMsg("systemKey不为gucci，请检查！");
            return rep;
        }
        if (request == null) {
            rep.setMsg("请求参数不能为空！");
            return rep;
        }
        try {
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(request);
            String startTimes = obj.get("startTime").toString(); // 开始时间
            String endTimes = obj.get("endTime").toString(); // 结束时间
            int page = Integer.parseInt(obj.get("page").toString()); // 页号
            int pageSize = Integer.parseInt(obj.get("pageSize").toString()); // 每页大小
            int type = Integer.parseInt(obj.get("type").toString()); // 类型：0普通入库 1退货入库
            // 转换时间
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = df.parse(startTimes);
            Date endTime = df.parse(endTimes);
            int startNum = page * pageSize - pageSize;
            // 时间段查询入库反馈数据
            Pagination<GucciVMIInFeedback> items = gucciVMIInFeedbackDao.findVMIInFBListByJDADocumentNo(startNum, pageSize, startTime, endTime, type, new BeanPropertyRowMapperExt<GucciVMIInFeedback>(GucciVMIInFeedback.class), null);
            List<GucciVMIInFeedback> feedbacks = items.getItems();
            if (feedbacks == null || feedbacks.isEmpty()) {
                rep.setMsg("当前请求参数无匹配结果！");
                rep.setStatus("1");
                return rep;
            }
            // 封装数据
            List<GucciVMIInFB> fbList = new ArrayList<GucciVMIInFB>();
            for (GucciVMIInFeedback feedback : feedbacks) {
                GucciVMIInFB fb = new GucciVMIInFB();
                BeanUtils.copyProperties(feedback, fb, new String[] {"id", "staCode", "status", "errCount", "errMsg", "type"});
                fbList.add(fb);
                // 明细数据
                List<GucciVMIInFBLine> fblList = fb.getLines();
                List<GucciVMIInFeedbackLine> feedbackls = gucciVMIInFeedbackLineDao.findLinesByInFeedbackId(feedback.getId());
                if (feedbackls != null && !feedbackls.isEmpty()) {
                    for (GucciVMIInFeedbackLine feedbackl : feedbackls) {
                        GucciVMIInFBLine fbl = new GucciVMIInFBLine();
                        BeanUtils.copyProperties(feedbackl, fbl, new String[] {"id", "gucciVMIInFeedback"});
                        fblList.add(fbl);
                    }
                }
                // 将头信息设置为已发送状态
                feedback.setStatus(1);
                gucciVMIInFeedbackDao.save(feedback);
            }
            rep.setData(fbList);
            rep.setStatus("1");
            rep.setTotal(items.getCount());
            return rep;
        } catch (Exception e) {
            rep.setMsg("WMS系统异常！");
            return rep;
        }
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateVmiRtn() {
        List<VmiRtoCommand> list = vmiRtoDao.findVmiRtoByCreGucciRtn(VmiGeneralStatus.NEW.getValue(), new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
        if (list == null || list.isEmpty()) return;
        for (VmiRtoCommand vmiRtoCommand : list) {
            gucciManager.generateVmiRtn(vmiRtoCommand);
        }
    }

    /**
     * 提供gucci退大仓反馈信息
     */
    @Override
    public DataResponse<GucciVMIRtnFeedback> feedbackRtnInfo(String systemKey, String request) {
        DataResponse<GucciVMIRtnFeedback> rep = new DataResponse<GucciVMIRtnFeedback>();
        rep.setStatus("0");
        if (!StringUtils.equals(systemKey, Constants.GUCCI_SYSTEM_KEY)) {
            rep.setMsg("systemKey不为gucci，请检查！");
            return rep;
        }
        if (request == null) {
            rep.setMsg("请求参数不能为空！");
            return rep;
        }
        try {
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(request);
            String startTimes = obj.get("startTime").toString(); // 开始时间
            String endTimes = obj.get("endTime").toString(); // 结束时间
            int page = Integer.parseInt(obj.get("page").toString()); // 页号
            int pageSize = Integer.parseInt(obj.get("pageSize").toString()); // 每页大小
            // 转换时间
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = df.parse(startTimes);
            Date endTime = df.parse(endTimes);
            int startNum = page * pageSize - pageSize;
            // 时间段查询退大仓反馈数据
            Pagination<GucciVMIRtnFB> items = gucciVMIRtnFBDao.findVMIRtnFBListByTime(startNum, pageSize, startTime, endTime, new BeanPropertyRowMapperExt<GucciVMIRtnFB>(GucciVMIRtnFB.class), null);
            List<GucciVMIRtnFB> feedbacks = items.getItems();
            if (feedbacks == null || feedbacks.isEmpty()) {
                rep.setMsg("当前请求参数无匹配结果！");
                rep.setStatus("1");
                return rep;
            }
            // 封装数据
            List<GucciVMIRtnFeedback> fbList = new ArrayList<GucciVMIRtnFeedback>();
            for (GucciVMIRtnFB feedback : feedbacks) {
                GucciVMIRtnFeedback fb = new GucciVMIRtnFeedback();
                BeanUtils.copyProperties(feedback, fb, new String[] {"id", "staCode", "status"});
                fbList.add(fb);
                // 明细数据
                List<GucciVMIRtnFeedbackLine> fblList = fb.getGucciVMIRtnFeedbackLines();
                List<GucciVMIRtnFBLine> feedbackls = gucciVMIRtnFBLineDao.findLinesByOutFeedbackId(feedback.getId());
                if (feedbackls != null && !feedbackls.isEmpty()) {
                    for (GucciVMIRtnFBLine feedbackl : feedbackls) {
                        GucciVMIRtnFeedbackLine fbl = new GucciVMIRtnFeedbackLine();
                        BeanUtils.copyProperties(feedbackl, fbl, new String[] {"id", "gucciVMIRtnFBId"});
                        fblList.add(fbl);
                    }
                }
                // 将头信息设置为已发送状态
                feedback.setStatus(1);
                gucciVMIRtnFBDao.save(feedback);
            }
            rep.setData(fbList);
            rep.setStatus("1");
            rep.setTotal(items.getCount());
            return rep;
        } catch (Exception e) {
            rep.setMsg("WMS系统异常！");
            return rep;
        }
    }

    /**
     * 每日库存数据生成（含占用量和各种库存状态）
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void salesInventoryGucci() {
        log.debug("==============================GucciTask salesInventoryGucci start==============================");
        ChooseOption location = chooseOptionDao.findByCategoryCodeAndKey(Constants.GUCCI_SYSTEM_KEY, Constants.GUCCI_LOCATION);
        String locationStr = location == null ? "baozun" : (location.getOptionValue() == null ? "baozun" : location.getOptionValue());
        ChooseOption jdaWarehouseCode = chooseOptionDao.findByCategoryCodeAndKey(Constants.GUCCI_SYSTEM_KEY, Constants.JDA_WAREHOURSE_CODE);
        String jdaWarehouseCodeStr = jdaWarehouseCode == null ? "22400" : (jdaWarehouseCode.getOptionValue() == null ? "22400" : jdaWarehouseCode.getOptionValue());
        ChooseOption brandCode = chooseOptionDao.findByCategoryCodeAndKey(Constants.GUCCI_SYSTEM_KEY, Constants.GUCCI_BRAND_CODE);
        String brandCodeStr = brandCode == null ? "GG" : (brandCode.getOptionValue() == null ? "GG" : brandCode.getOptionValue());
        BiChannel shop = companyShopDao.getByVmiCode(Constants.GUCCI_BRAND_VMI_CODE);
        if (shop != null) {
            OperationUnit ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
            if (ou != null) {
                gucciSalesInventoryDao.insertTotalSalesInv(ou.getId(), shop.getCode(), locationStr, jdaWarehouseCodeStr, brandCodeStr);
            }
        }
        log.debug("==============================GucciTask salesInventoryGucci end================================");
    }

    /**
     * 超过6小时未收到商品主档信息发送预警邮件
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendEmailWhenInstructionHasNoSku() {
        log.info("==============================GucciTask sendEmailWhenInstructionHasNoSku start==============================");
        try {
            // 发送邮件预警
            gucciManager.sendEmailWhenInstructionHasNoSku();
        } catch (Exception e) {
            log.error("Gucci入库单发送未收到商品主档信息预警邮件异常！", e);
        }
        log.info("==============================GucciTask sendEmailWhenInstructionHasNoSku end==============================");
    }
}
