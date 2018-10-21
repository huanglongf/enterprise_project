package com.jumbo.web.action.warehouse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.StringUtil;
import com.jumbo.util.zip.ZipUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.manager.warehouse.PickingListManager;
import com.jumbo.wms.manager.warehouse.PickingListPrintManager;
import com.jumbo.wms.manager.warehouse.SkuSizeConfigManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListCommand;

import loxia.dao.Pagination;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public class PrintPickingListBulkAction extends BaseJQGridProfileAction {
    /**
     * 
     */
    private static final long serialVersionUID = 4501081672945249578L;
    @Autowired
    private ExcelWriterManager excelWriterManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private PickingListPrintManager pickingListPrintManager;
    @Autowired
    private PickingListManager pickingListManager;
    @Autowired
    private SkuSizeConfigManager skuSizeConfigManager;
    /**
     * 作为查询条件的pickingList
     */
    private PickingList pickingList;

    private String fileName;
    private String plList;
    private boolean isPostPrint = false;
    private Integer pickZoneId;
    private Long pickingListId;
    /**
     * 作业单ID
     */
    private String staid;

    /**
     * 区域
     */
    private String pickingZoneIds;

    private String priority;


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStaid() {
        return staid;
    }

    public void setStaid(String staid) {
        this.staid = staid;
    }

    public Long getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(Long pickingListId) {
        this.pickingListId = pickingListId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPlList() {
        return plList;
    }

    public void setPlList(String plList) {
        this.plList = plList;
    }


    public boolean isPostPrint() {
        return isPostPrint;
    }

    public void setPostPrint(boolean isPostPrint) {
        this.isPostPrint = isPostPrint;
    }

    public PickingList getPickingList() {
        return pickingList;
    }

    public void setPickingList(PickingList pickingList) {
        this.pickingList = pickingList;
    }

    /**
     * 页面跳转 跳转到配货单信息打印--秒杀页面
     * 
     * @return
     */
    public String printPickingListBulk() {
        return SUCCESS;
    }

    /**
     * 返回根据配货清单状态的数据
     * 
     * @return
     */
    public String getAllPickingListStatusBulk() {
        setTableConfig();
        Pagination<PickingList> pickinglist = pickingListPrintManager.getAllPickingListStatusBulk(tableConfig.getStart(), tableConfig.getPageSize(), pickingList, userDetails.getCurrentOu().getId(), tableConfig.getSorts(), getCityList(priority));
        request.put(JSON, toJson(pickinglist));
        return JSON;
    }

    /**
     * 导出税控发票
     * 
     * @throws IOException
     * 
     * @throws UnsupportedEncodingException
     * 
     * @throws Exception
     */
    public String exportSoInvoices() throws IOException {
        List<ByteArrayOutputStream> outs = new ArrayList<ByteArrayOutputStream>();
        String[] plId = plList.split(",");
        if (plId.length > 0) {
            Map<String, List<Long>> map = wareHouseManager.findInvoiceStaId(plId);
            String fileName = excelWriterManager.getExportFileName(Long.parseLong(plId[0]));
            setFileName(fileName + Constants.ZIP);
            for (String key : map.keySet()) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                excelExportManager.exportSoInvoiceByPickingLists(out, map.get(key), null);
                outs.add(out);
            }
            if (outs.size() > 0) {
                setInputStream(ZipUtil.zipFileDownloadForExcel1(fileName, outs));
            } else {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                // excelExportManager.exportSoInvoiceByPickingList(out, Long.parseLong(plId[0]),
                // null);
                setInputStream(ZipUtil.zipFileDownloadForExcel("NO_INVOICE", out));
            }
        }
        return SUCCESS;
    }

    /**
     * VMI退仓拣货单打印
     */
    public String vmiBackPrint() {
        List<JasperPrint> jpJasperPrint = new ArrayList<JasperPrint>();
        try {
            if (staid != null && !"".equals(staid)) {
                jpJasperPrint = printManager.vmiBackPrint(staid);
                return printObject(jpJasperPrint);
            } else {
                return null;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // log.error("", e);
            if (log.isErrorEnabled()) {
                log.error("vmiBackPrint error:" + staid, e);
            } ;
            return null;
        }
    }

    /**
     * 打印配货清单-old
     * 
     * @return
     * @throws Exception
     */
    public String printPickingListModeBulk1() {
        List<JasperPrint> jpJasperPrint = new ArrayList<JasperPrint>();
        try {
            List<PickingListCommand> commands = new ArrayList<PickingListCommand>();
            if (StringUtils.hasText(plList)) {
                String[] plLists = plList.split(",");
                List<PickingList> list = wareHouseManager.queryPickingLists(plLists);
                Warehouse w = printManager.queryTotalPickingList(userDetails.getCurrentOu().getId());
                if (null != w && null != w.getTotalPickinglistLimit() && 0 != w.getTotalPickinglistLimit()) {
                    Integer total = w.getTotalPickinglistLimit();
                    for (int i = 0; i < list.size(); i++) {
                        if (i < total) {
                            PickingListCommand pl = wareHouseManager.getPickingListById(list.get(i).getId());
                            commands.add(pl);
                        } else {
                            break;
                        }

                    }
                } else {
                    for (PickingList pickingList : list) {
                        PickingListCommand pl = wareHouseManager.getPickingListById(pickingList.getId());
                        commands.add(pl);
                    }
                }

                if (StringUtil.isEmpty(pickingZoneIds)) {
                    jpJasperPrint = printManager.printPickingBulkListMode1(commands, pickZoneId, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                } else {
                    List<JasperPrint> jpPrints = new ArrayList<JasperPrint>();
                    String[] pZoneIds = pickingZoneIds.split(",");
                    if (null != w && null != w.getTotalPickinglistLimit() && 0 != w.getTotalPickinglistLimit()) {
                        Integer total = w.getTotalPickinglistLimit();
                        for (int j = 0; j < list.size(); j++) {
                            if (j < total) {
                                for (int i = 0; i < pZoneIds.length; i++) {
                                    commands.clear();
                                    PickingListCommand pls = wareHouseManager.getPickingListById(list.get(j).getId());
                                    commands.add(pls);
                                    Integer pickZoneId = Integer.parseInt(pZoneIds[i]);
                                    jpPrints = printManager.printPickingBulkListMode1(commands, pickZoneId, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                                    jpJasperPrint.addAll(jpPrints);
                                }
                            } else {
                                break;
                            }

                        }
                    } else {
                        for (PickingList pl : list) {
                            for (int i = 0; i < pZoneIds.length; i++) {
                                commands.clear();
                                PickingListCommand pls = wareHouseManager.getPickingListById(pl.getId());
                                commands.add(pls);
                                Integer pickZoneId = Integer.parseInt(pZoneIds[i]);
                                jpPrints = printManager.printPickingBulkListMode1(commands, pickZoneId, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                                jpJasperPrint.addAll(jpPrints);
                            }
                        }
                    }

                }
                return printObject(jpJasperPrint);
            } else {
                return null;
            }

        } catch (Exception e) {
            // e.printStackTrace();
            // log.error("", e);
            if (log.isErrorEnabled()) {
                log.error("printPickingListModeBulk1 error:" + plList, e);
            } ;
            return null;
        }
    }

    /**
     * 打印配货清单-new
     * 
     * @return
     * @throws Exception
     * @author kai.zhu
     */
    @SuppressWarnings("null")
    public String printPickingListModeBulkNew2() {
        List<JasperPrint> jpJasperPrintList = new ArrayList<JasperPrint>();
        try {
            if (StringUtils.hasText(plList)) {
                String[] plLists = plList.split(",");
                List<PickingList> list = wareHouseManager.queryPickingLists(plLists);
                Warehouse w = printManager.queryTotalPickingList(userDetails.getCurrentOu().getId());
                if (null != w && null != w.getTotalPickinglistLimit() && 0 != w.getTotalPickinglistLimit()) {
                    Integer total = w.getTotalPickinglistLimit();
                    for (int j = 0; j < list.size(); j++) {
                        if (j < total) {
                            List<Long> zoonIds = pickingListManager.findZoonIds(list.get(j).getId(), userDetails.getCurrentOu().getId());
                            if (null == zoonIds && zoonIds.size() == 0) {
                                continue;
                            }
                            if (!StringUtil.isEmpty(pickingZoneIds)) {
                                String[] pZoneIds = pickingZoneIds.split(",");
                                for (int i = 0; i < pZoneIds.length; i++) {
                                    Long pickZoneId = Long.parseLong(pZoneIds[i]);
                                    if (zoonIds.contains(pickZoneId)) {
                                        Long plId = list.get(j).getId();
                                        JasperPrint jpJasperPrint = null;
                                        String psizes = "";
                                        Long pcheckmode = pickingListManager.findPickingListByPickId(plId);
                                        Long special = pickingListManager.findPickingListByPickIdS(plId);
                                        if (pcheckmode == PickingListCheckMode.PICKING_SECKILL.getValue()) {
                                            psizes = "秒";
                                        } else if (pcheckmode == PickingListCheckMode.PICKING_GROUP.getValue()) {
                                            psizes = "团";
                                        } else if (pcheckmode == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
                                            psizes = "套";
                                        } else {
                                            if (special == null) {
                                                psizes = skuSizeConfigManager.findBypicklistId(plId);
                                            } else {
                                                if (special == 1) {
                                                    psizes = skuSizeConfigManager.findBypicklistId(plId);
                                                    psizes += "/特";
                                                } else {
                                                    psizes = skuSizeConfigManager.findBypicklistId(plId);
                                                    psizes += "/包";
                                                }
                                            }
                                        }
                                        jpJasperPrint = printManager.printPickingListNewMode1(plId, Integer.parseInt(pickZoneId.toString()), userDetails.getCurrentOu().getId(), psizes, userDetails.getUser().getId(), null);
                                        jpJasperPrintList.add(jpJasperPrint);
                                    }
                                }
                            } else {
                                if (zoonIds.contains(Long.parseLong(pickZoneId.toString()))) {
                                    Long plId = list.get(j).getId();
                                    JasperPrint jpJasperPrint = null;
                                    String psizes = "";
                                    Long pcheckmode = pickingListManager.findPickingListByPickId(plId);
                                    Long special = pickingListManager.findPickingListByPickIdS(plId);
                                    if (pcheckmode == PickingListCheckMode.PICKING_SECKILL.getValue()) {
                                        psizes = "秒";
                                    } else if (pcheckmode == PickingListCheckMode.PICKING_GROUP.getValue()) {
                                        psizes = "团";
                                    } else if (pcheckmode == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
                                        psizes = "套";
                                    } else {
                                        if (special == null) {
                                            psizes = skuSizeConfigManager.findBypicklistId(plId);
                                        } else {
                                            if (special == 1) {
                                                psizes = skuSizeConfigManager.findBypicklistId(plId);
                                                psizes += "/特";
                                            } else {
                                                psizes = skuSizeConfigManager.findBypicklistId(plId);
                                                psizes += "/包";
                                            }
                                        }
                                    }
                                    jpJasperPrint = printManager.printPickingListNewMode1(plId, Integer.parseInt(pickZoneId.toString()), userDetails.getCurrentOu().getId(), psizes, userDetails.getUser().getId(), null);
                                    jpJasperPrintList.add(jpJasperPrint);
                                }
                            }
                        } else {
                            break;
                        }
                    }
                } else {
                    for (PickingList pickingList : list) {
                        List<Long> zoonIds = pickingListManager.findZoonIds(pickingList.getId(), userDetails.getCurrentOu().getId());
                        if (null == zoonIds && zoonIds.size() == 0) {
                            continue;
                        }
                        if (!StringUtil.isEmpty(pickingZoneIds)) {
                            String[] pZoneIds = pickingZoneIds.split(",");
                            for (int i = 0; i < pZoneIds.length; i++) {
                                Long pickZoneId = Long.parseLong(pZoneIds[i]);
                                if (zoonIds.contains(pickZoneId)) {
                                    Long plId = pickingList.getId();
                                    JasperPrint jpJasperPrint = null;
                                    String psizes = "";
                                    Long pcheckmode = pickingListManager.findPickingListByPickId(plId);
                                    Long special = pickingListManager.findPickingListByPickIdS(plId);
                                    if (pcheckmode == PickingListCheckMode.PICKING_SECKILL.getValue()) {
                                        psizes = "秒";
                                    } else if (pcheckmode == PickingListCheckMode.PICKING_GROUP.getValue()) {
                                        psizes = "团";
                                    } else if (pcheckmode == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
                                        psizes = "套";
                                    } else {
                                        if (special == null) {
                                            psizes = skuSizeConfigManager.findBypicklistId(plId);
                                        } else {
                                            if (special == 1) {
                                                psizes = skuSizeConfigManager.findBypicklistId(plId);
                                                psizes += "/特";
                                            } else {
                                                psizes = skuSizeConfigManager.findBypicklistId(plId);
                                                psizes += "/包";
                                            }
                                        }
                                    }
                                    jpJasperPrint = printManager.printPickingListNewMode1(plId, Integer.parseInt(pickZoneId.toString()), userDetails.getCurrentOu().getId(), psizes, userDetails.getUser().getId(), null);
                                    jpJasperPrintList.add(jpJasperPrint);
                                }
                            }
                        } else {
                            if (zoonIds.contains(Long.parseLong(pickZoneId.toString()))) {
                                Long plId = pickingList.getId();
                                JasperPrint jpJasperPrint = null;
                                String psizes = "";
                                Long pcheckmode = pickingListManager.findPickingListByPickId(plId);
                                Long special = pickingListManager.findPickingListByPickIdS(plId);
                                if (pcheckmode == PickingListCheckMode.PICKING_SECKILL.getValue()) {
                                    psizes = "秒";
                                } else if (pcheckmode == PickingListCheckMode.PICKING_GROUP.getValue()) {
                                    psizes = "团";
                                } else if (pcheckmode == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
                                    psizes = "套";
                                } else {
                                    if (special == null) {
                                        psizes = skuSizeConfigManager.findBypicklistId(plId);
                                    } else {
                                        if (special == 1) {
                                            psizes = skuSizeConfigManager.findBypicklistId(plId);
                                            psizes += "/特";
                                        } else {
                                            psizes = skuSizeConfigManager.findBypicklistId(plId);
                                            psizes += "/包";
                                        }
                                    }
                                }
                                jpJasperPrint = printManager.printPickingListNewMode1(plId, Integer.parseInt(pickZoneId.toString()), userDetails.getCurrentOu().getId(), psizes, userDetails.getUser().getId(), null);
                                jpJasperPrintList.add(jpJasperPrint);
                            }
                        }
                    }
                }

            }
            return printObject(jpJasperPrintList);
        } catch (Exception e) {
            // e.printStackTra
            if (log.isErrorEnabled()) {
                log.error("printPickingListModeBulkNew2 error:" + plList, e);
            } ;
            return null;
        }
    }

    /**
     * 打印装箱清单列表
     * 
     * @return
     * @throws Exception
     */
    public String printTrunkPackingListMode1OutBulk() {
        List<JasperPrint> jp;
        try {
            if (StringUtils.hasText(plList)) {
                String[] plLists = plList.split(",");
                List<Long> plIds = new ArrayList<Long>();
                List<PickingList> list = wareHouseManager.queryPickingLists(plLists);
                Warehouse w = printManager.queryTotalPickingList(userDetails.getCurrentOu().getId());
                if (null != w && null != w.getTotalPickinglistLimit() && 0 != w.getTotalPickinglistLimit()) {
                    Integer total = w.getTotalPickinglistLimit();
                    for (int i = 0; i < list.size(); i++) {
                        if (i < total) {
                            plIds.add(list.get(i).getId());
                        } else {
                            break;
                        }
                    }
                } else {
                    for (PickingList pickingList : list) {
                        plIds.add(pickingList.getId());
                    }
                }

                jp = printManager.printPackingPageBulk(plIds, null, userDetails.getUser().getId(), isPostPrint);
                return printObject(jp);
            } else {
                return null;
            }
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 物流快递面单套打
     * 
     * @return
     * @throws Exception
     */
    public String printDeliveryInfoMode1Bulk() {
        try {
            List<JasperPrint> jp = new ArrayList<JasperPrint>();
            if (StringUtils.hasText(plList)) {
                String[] plLists = plList.split(",");
                List<Long> plIds = new ArrayList<Long>();
                List<PickingList> list = wareHouseManager.queryPickingLists(plLists);
                for (PickingList pickingList : list) {
                    plIds.add(pickingList.getId());
                }

                jp = printManager.printExpressBillByPickingBulkList(plIds, null, userDetails.getUser().getId());
                return printObject(jp);
            } else {
                return null;
            }
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 更新配货单状态
     * 
     * @return
     */
    public String updatePickListWhStatus() {
        JSONObject result = new JSONObject();
        if (StringUtils.hasText(plList)) {
            String[] plLists = plList.split(",");
            List<Long> plIds = new ArrayList<Long>();
            for (int i = 0; i < plLists.length; i++) {
                plIds.add(Long.parseLong(plLists[i]));
            }
            try {
                pickingListPrintManager.updatePickListWhStatusBulk(plIds, this.userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                result.put("msg", "SUCCESS");

            } catch (Exception e) {
                log.error("", e);
                try {
                    result.put("msg", "ERROR");
                } catch (JSONException e1) {
                    log.error("", e1);
                }
            }
        } else {
            try {
                result.put("msg", "ERROR");
            } catch (JSONException e) {
                // e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("updatePickListWhStatus error:" + plList, e);
                } ;
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    // 拣货单打印
    public String diekingLinePrint() {
        List<JasperPrint> jpJasperPrint = new ArrayList<JasperPrint>();
        try {
            if (plList != null && !"".equals(plList)) {
                // String[] pls = plList.split(",");
                jpJasperPrint = printManager.diekingLinePrint(staid, plList);
                return printObject(jpJasperPrint);
            } else {
                return null;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // log.error("", e);
            if (log.isErrorEnabled()) {
                log.error("diekingLinePrint error:" + staid, e);
            }
            return null;
        }

    }


    public Integer getPickZoneId() {
        return pickZoneId;
    }

    public void setPickZoneId(Integer pickZoneId) {
        this.pickZoneId = pickZoneId;
    }

    public String getPickingZoneIds() {
        return pickingZoneIds;
    }

    public void setPickingZoneIds(String pickingZoneIds) {
        this.pickingZoneIds = pickingZoneIds;
    }


    private List<String> getCityList(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (str != null && StringUtils.hasText(str)) {
            for (String s : str.split(",")) {
                list.add(s);
            }
        }
        return list;
    }
}
