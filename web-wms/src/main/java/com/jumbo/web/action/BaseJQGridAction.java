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

package com.jumbo.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;
import loxia.support.table.BasicTableConfigSupport;
import loxia.support.table.TableConfig;
import loxia.support.table.TableConfigAware;
import loxia.support.table.TableModelJSGridImpl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.BeanUtilSupport;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.model.system.ChooseOption;

/**
 * 
 * @author wanghua
 */
public class BaseJQGridAction extends BaseAction implements TableConfigAware, ServletResponseAware {

    /**
     * 
     */
    private static final long serialVersionUID = -7883109957449131786L;
    /**
     * 第几页
     */
    private int page;
    /**
     * 每页条数
     */
    private int rows;
    /**
     * asc/desc
     */
    private String sord;
    /**
     * 排序字段
     */
    private String sidx;
    /**
     * 过滤字段
     */
    private String columns;
    private List<String> colNames;
    private List<String> colModel;
    private String caption;
    private Boolean isExcel;
    private Map<String, Object> columnOption;
    protected TableConfig tableConfig;
    protected HttpServletResponse response;
    @Autowired
    private ChooseOptionManager chooseOptionManager;

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected JSONObject toJson(List list) {
        log.debug("Json List with size: {}", list == null ? "null" : "" + list.size());
        if (tableConfig.getIsExcel() != null && tableConfig.getIsExcel()) {
            try {
                toExcel(list);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return new TableModelJSGridImpl(tableConfig, list).toJson();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected JSONObject toJson(Pagination list) {
        if (tableConfig.getIsExcel() != null && tableConfig.getIsExcel()) {
            try {
                toExcel(list == null ? null : list.getItems());
            } catch (Exception e) {
                log.error("Export data to excel exception:{}", e.getMessage());
            }
        }
        return new TableModelJSGridImpl(tableConfig, list).toJson();
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public void setTableConfig() {
        String sortString = sidx;
        sortString = sidx + " " + sord;
        tableConfig = new BasicTableConfigSupport(columns == null ? null : columns.split(","));
        tableConfig.setCurrentPage(page);
        tableConfig.setPageSize(rows);
        tableConfig.setSorts(sortString);
        if (colNames != null && colNames.size() > 0) {
            String[] cns = new String[colNames.size()];
            tableConfig.setColumnNames(colNames.toArray(cns));
        }
        if (colModel != null && colModel.size() > 0) {
            String[] cms = new String[colModel.size()];
            tableConfig.setColumns(colModel.toArray(cms));
        }
        tableConfig.setCaption(caption);
        tableConfig.setIsExcel(isExcel);
        tableConfig.setColumnOption(columnOption);
    }

    public void setColNames(List<String> colNames) {
        this.colNames = colNames;
    }

    public void setColModel(List<String> colModel) {
        this.colModel = colModel;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getColumns() {
        return columns;
    }

    public List<String> getColNames() {
        return colNames;
    }

    public List<String> getColModel() {
        return colModel;
    }

    public String getCaption() {
        return caption;
    }

    public void setIsExcel(Boolean isExcel) {
        this.isExcel = isExcel;
    }

    public Boolean getIsExcel() {
        return isExcel;
    }

    /**
     * 一次性导出非分页数据,要保证数据没有溢出
     * 
     * @param list
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    protected void toExcel(List list) throws Exception {
        // 限制导出EXCEL记录数，超过2W 不做处理
        if (list != null && list.size() > 20000000) {
            ServletOutputStream outs = null;
            try {
                response.setContentType("text/plain;charset=" + Constants.UTF_8);
                response.setHeader("Content-Disposition", "attachment;filename=msg.txt");
                outs = response.getOutputStream();
                outs.print("out of size limit!!!");
            } catch (Exception e) {
                log.error("writeExcelToResponse error");
            } finally {
                if (outs != null) {
                    outs.flush();
                    outs.close();
                }
            }
        } else {
            response.setContentType("application/vnd.ms-excel;charset=" + Constants.UTF_8);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(tableConfig.getCaption().getBytes(Constants.GBK), Constants.ISO_8859_1) + Constants.EXCEL_XLS);
            ServletOutputStream outs = null;
            // FileChannel outs = null;
            try {
                outs = response.getOutputStream();
                writeExcelToResponse(outs, list);
            } catch (Exception e) {
                log.error("writeExcelToResponse error");
            } finally {
                if (outs != null) {
                    outs.flush();
                    outs.close();
                }
            }
        }

    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @SuppressWarnings("rawtypes")
    protected void writeExcelToResponse(ServletOutputStream outs, List list) throws Exception {
        // writeExcelToResponse(ExcelKit.getInstance().createWorkbook(), outs, list);
        writeExcelToResponseSXSSF(outs, list);
    }

    @SuppressWarnings({"rawtypes", "resource"})
    protected void writeExcelToResponseSXSSF(ServletOutputStream outs, List list) throws Exception {
        if (list != null && !list.isEmpty()) {
            String url = Constants.EXCLE_IMPORT_TEMP;// object.toString();
            File f = new File(url);
            if (!f.exists()) {
                f.mkdirs();
            }
            String StringDate = Long.toString(new Date().getTime());
            String fileName = StringDate + new Random().nextInt(900) + ".xls"; // 临时文件名
            FileOutputStream output = new FileOutputStream(new File(url + fileName)); // 读取临时文件
            SXSSFWorkbook wb = new SXSSFWorkbook(1000);// 内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘
            String[] colNames = tableConfig.getColumnNames(), colModel = tableConfig.getColumns();
            Sheet sheet = wb.createSheet(tableConfig.getCaption());
            int rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            for (int cellNum = 0; cellNum < colNames.length; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(colNames[cellNum]);
            }
            boolean noOption = columnOption == null || columnOption.isEmpty();
            Map<String, Map<String, String>> map = null;
            if (!noOption) {
                map = parseColumnOption();
            }
            for (Object o : list) {
                Map<String, Object> obj = new HashMap<String, Object>();
                BeanUtilSupport.describe(obj, null, o, colModel);
                if (!noOption) {
                    describe(obj, map);
                }
                row = sheet.createRow(rowNum++);
                for (int cellNum = 0; cellNum < colModel.length; cellNum++) {
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(obj.get(colModel[cellNum]) == null ? "" : obj.get(colModel[cellNum]).toString());
                }
            }
            wb.write(output);
            // NIO读文件
            File file = new File(url + fileName);
            int bufferSize = 1;
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel fileChannel = fileInputStream.getChannel();
            ByteBuffer bb = ByteBuffer.allocateDirect(786432);
            byte[] barray = new byte[bufferSize];
            int nRead, nGet;
            try {
                while ((nRead = fileChannel.read(bb)) != -1) {
                    if (nRead == 0) continue;
                    bb.position(0);
                    bb.limit(nRead);
                    while (bb.hasRemaining()) {
                        nGet = Math.min(bb.remaining(), bufferSize);
                        bb.get(barray, 0, nGet);
                        outs.write(barray);
                    }
                    bb.clear();
                }
            } catch (IOException e) {
                // e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("writeExcelToResponseSXSSF Exception:", e);
                };
            } finally {
                bb.clear();
                fileChannel.close();
                fileInputStream.close();
            }
            try {
                File myDelFile = new File(url + fileName);
                myDelFile.delete();
            } catch (Exception e) {
                log.error("删除文件出错", e);
            }
            outs.close();
        }
    }

    /**
     * 在导出大批量数据时,分页写数据,防止溢出
     * 
     * @param wb
     * @param outs
     * @param list
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    protected void writeExcelToResponse(Workbook wb, ServletOutputStream outs, List list) throws Exception {
        if (list != null && !list.isEmpty()) {
            Sheet sheet = wb.createSheet(tableConfig.getCaption());
            int rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            String[] colNames = tableConfig.getColumnNames(), colModel = tableConfig.getColumns();
            for (int cellNum = 0; cellNum < colNames.length; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(colNames[cellNum]);
            }
            boolean noOption = columnOption == null || columnOption.isEmpty();
            Map<String, Map<String, String>> map = null;
            if (!noOption) {
                map = parseColumnOption();
            }
            for (Object o : list) {
                Map<String, Object> obj = new HashMap<String, Object>();
                BeanUtilSupport.describe(obj, null, o, colModel);
                if (!noOption) {
                    describe(obj, map);
                }
                row = sheet.createRow(rowNum++);
                for (int cellNum = 0; cellNum < colModel.length; cellNum++) {
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(obj.get(colModel[cellNum]) == null ? "" : obj.get(colModel[cellNum]).toString());
                }
            }
        }
        wb.write(outs);
    }

    private Map<String, Object> describe(Map<String, Object> obj, Map<String, Map<String, String>> option) {
        if (option == null || option.isEmpty()) return obj;
        for (Map.Entry<String, Map<String, String>> entry : option.entrySet()) {
            obj.put(entry.getKey(), entry.getValue().get(obj.get(entry.getKey())));
        }
        return obj;
    }

    private Map<String, Map<String, String>> parseColumnOption() {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        for (Map.Entry<String, Object> entry : columnOption.entrySet()) {
            log.debug(entry.getKey() + "=" + entry.getValue());
            String[] value = (String[]) entry.getValue();
            map.put(entry.getKey(), getOptionMap(value[0]));
        }
        return map;
    }

    /**
     * 根据categoryCode获取map
     */
    private Map<String, String> getOptionMap(String opcode) {
        Map<String, String> map = new HashMap<String, String>();
        List<ChooseOption> list = chooseOptionManager.findOptionListByCategoryCode(opcode);
        if (list == null || list.isEmpty()) return map;
        for (ChooseOption option : list) {
            map.put(option.getOptionKey(), option.getOptionValue());
        }
        return map;

    }

    public void setColumnOption(Map<String, Object> columnOption) {
        this.columnOption = columnOption;
    }

    public Map<String, Object> getColumnOption() {
        return columnOption;
    }

}
