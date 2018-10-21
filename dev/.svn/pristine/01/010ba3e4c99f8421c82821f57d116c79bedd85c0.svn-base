package com.bt.lmis.tools.whsTempData.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.common.CommonUtils;
import com.bt.common.ExcelExportUtil;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.service.TempletService;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.tools.whsTempData.service.WhsTempDataService;
import com.bt.utils.BaseConst;
import com.bt.utils.SessionUtils;
import com.monitorjbl.xlsx.StreamingReader;

@Controller
@RequestMapping("/control/WhsTempDataController")
public class WhsTempDataController extends BaseController {
	@Resource(name = "templetServiceImpl")
	private TempletService<T> templetService;
	@Resource(name = "whsTempDataServiceImpl")
	private WhsTempDataService<T> whsTempDataService;

	/**
	 * 
	 * 
	 * 
	 * 
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping("/downloadTemplete")
	public String downloadTemplete(HttpServletRequest request, HttpServletResponse resp) {
		try {

			String[] headNames = new String[] { "箱号", "库位", "SKU", "数量", "状态" };
			String fileName = "仓库暂存数据导入模板.xlsx";
			ExcelExportUtil.exportExcelData(new ArrayList<Map<String, Object>>(), headNames, "sheet1",
					ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX, fileName, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/importWhsTempData", produces = "application/json;charset=UTF-8")
	public String importWhsTempData(@RequestParam("file") MultipartFile file, HttpServletRequest req,
			HttpServletResponse res) {
		Employee user = SessionUtils.getEMP(req);
		JSONObject obj = new JSONObject();
		SimpleDateFormat SDF=new SimpleDateFormat("yyyyMMdd");
		String date = SDF.format(new Date());
		whsTempDataService.addSequence(date);
		
		String batchId = whsTempDataService.getSequence(date);
		
		if (null != user) {
			try {
				String fileName = file.getOriginalFilename();
				String prefix = fileName.split("[.]")[fileName.split("[.]").length - 1];
				obj.put("code", "400");
				obj.put("msg", "系统错误!");
				// 判断文件是否为空
				if (!file.isEmpty()) {
					if (prefix.equals("xlsx")) {
						
						//创建目录
						File dir = new File(CommonUtils.getAllMessage("config", "TLS_UPLOAD_"+ OSinfo.getOSname()));  
				        if (!dir.exists()) {  
				        	dir.mkdirs();
				        }
						
						// 文件保存路径
						String filePath = CommonUtils.getAllMessage("config", "TLS_UPLOAD_"+ OSinfo.getOSname())
								+ ("仓库暂存数据导入_"+batchId + "." + prefix);
						// 转存文件
						file.transferTo(new File(filePath));
						FileInputStream in = new FileInputStream(filePath);
						Workbook wk = StreamingReader.builder().rowCacheSize(100) // 缓存到内存中的行数，默认是10
								.bufferSize(4096) // 读取资源时，缓存到内存的字节大小，默认是1024
								.open(in); // 打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
						for (Sheet sheet : wk) {
							for (Row r : sheet) {
								if(r==null) {
									obj.put("code", "400");
									obj.put("msg", "模板有误");
									return obj.toString();
								}else {
									if(r.getCell(0)==null||!r.getCell(0).getStringCellValue().equals("箱号")
											||r.getCell(1)==null||!r.getCell(1).getStringCellValue().equals("库位")
											||r.getCell(2)==null||!r.getCell(2).getStringCellValue().equals("SKU")
											||r.getCell(3)==null||!r.getCell(3).getStringCellValue().equals("数量")
											||r.getCell(4)==null||!r.getCell(4).getStringCellValue().equals("状态")
											||r.getCell(5)!=null) {
										obj.put("code", "400");
										obj.put("msg", "模板有误");
										return obj.toString();
									}
								}
								break;
							}
						}
						List<Map<String, Object>> errorMapList = whsTempDataService.importWhsTempData(wk,user,batchId);
						//导出错误信息
						if(errorMapList.size()>0) {
							try {
								
								// 错误文件保存路径
								String errorFilePath = CommonUtils.getAllMessage("config", "TLS_UPLOAD_"+ OSinfo.getOSname())
										+ ("仓库暂存数据导入_"+batchId +"_error"+ "." + prefix);
								
								String[] headNames = new String[] { "箱号", "库位", "SKU", "数量", "状态","错误原因" };
								
								writeExcelFile(errorMapList, headNames, null, "sheet1", errorFilePath);
								
								obj.put("code", "502");
								obj.put("msg", "导入失败!");
								obj.put("path", "仓库暂存数据导入_"+batchId +"_error"+ "." + prefix);
								return obj.toString();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						obj.put("code", "200");
						obj.put("msg", "导入成功!");
					} else {
						obj.put("code", "400");
						obj.put("msg", "文件类型必须为[.xlsx],您上传的文件类型为[." + prefix + "]!");
						return obj.toString();
					}
				} else {
					obj.put("code", "500");
					obj.put("msg", "必填参数为空!");
					return obj.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				obj.put("code", "400");
				obj.put("msg", "系统错误!");
			}
		} else {
			obj.put("code", "400");
			obj.put("msg", "Session过期,请重新登陆后在操作!");
		}
		return obj.toString();
	}

	/**
	 *
	 * @param map
	 * @param request
	 *            跳转和分页查询接口 根据传参来判定
	 * @return
	 */
	@RequestMapping("/toWarehouseSearch")
	public String toWarehouseSearch(Parameter parameter, HttpServletRequest req) {
		// 参数构造
		parameter = generateParameter(parameter, req);

		String tableName = CommonUtils.checkExistOrNot(parameter.getParam().get("tableName"))
				? parameter.getParam().get("tableName").toString()
				: "";
		// 配置表头 参数 tableName columnName
		req.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
		// 表格功能配置
		req.setAttribute("tableFunctionConfig",
				JSONObject.toJSONString(new TableFunctionConfig(tableName, true, null)));
		String pageName = CommonUtils.checkExistOrNot(parameter.getParam().get("pageName"))
				? parameter.getParam().get("pageName").toString()
				: "";

		// 分页控件
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
		parameter.setFirstResult(pageView.getFirstResult());
		parameter.setMaxResult(pageView.getMaxresult());
		String url = "";
	
		if (pageName.equals("firstPage")) {
			QueryResult<Map<String, Object>> qr = new QueryResult<>();
            pageView.setQueryResult(qr, parameter.getPage()); 
			url = "/lmis/tools/warehouseSearch";
			
		}else{
			pageView.setQueryResult(whsTempDataService.queryWhsTempData(parameter), parameter.getPage());
			url = "/templet/table";
		}

		req.setAttribute("pageView", pageView);

		// 返回页面
		return url;
	}

	@RequestMapping("/searchWarehouse")
	public String searchWarehouse(ModelMap map, HttpServletRequest request) {
		return null;
	}

	/**
	 * 根据批次号删除数据
	 * 
	 * @param batchId
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteByBatchId")
	public String deleteByBatchId(@RequestParam String batchId){
		Map<String,Object> result = new HashMap<>();
		int count = whsTempDataService.selectBybatchId(batchId.trim());
		if(count==0) {
			result.put("msg", "当前批次号不存在");
			result.put("code", "0");
		}else {
			int i = whsTempDataService.deleteBybatchId(batchId.trim());
			if(i>0) {
				result.put("msg", "删除成功");
				result.put("code", "1");
			}else {
				result.put("msg", "删除失败");
				result.put("code", "0");
			}
		}
		
		return JSONObject.toJSONString(result).toString();
	
	}
	
	
	/**
	 *
	 *
	 *
	 * 暂存数据导出
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping("/exportWhsTempData")
	public String exportWhsTempData(Parameter parameter,HttpServletRequest request, HttpServletResponse resp) {
		parameter = generateParameter(parameter, request);
		try {
			String[] headNames = new String[] { "导入批次号", "箱号", "库位","SKU","数量","状态"};
			String fileName ="仓库暂存数据.xlsx";
			ArrayList<Map<String,Object>> data= whsTempDataService.exportWhsTempData(parameter);
			ExcelExportUtil.exportExcelData(data, headNames, "sheet1",
					ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX, fileName, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	 /**
     * 写excel文件-支持 2003 2007 2010  xls,xlsx扩展名
     * @param list 需要写的数据 （可以为空）
     * @param headColums 数据的列名（字段名，数据里的key名 不能为null）
     * @param headNames 列名说明-一般是字段说明，（不需要时，可以传 null）
     * @param sheetName excel的第一个sheet的名称
     * @param path 写的文件路径（带文件名）
     * @throws Exception
     */
    public static void writeExcelFile(List<Map<String,Object>> list,String[] headColums,String[] headNames, String sheetName,String path)  throws Exception {
        if (!CommonUtils.checkExistOrNot(path)) {
             return;
        }else if(list == null || headColums == null){
             return;
        }else{
               writeXlsxHandle(list, headColums,headNames, sheetName, path);
        }
    }
	
	
	/**
     * 写excel文件-支持2007 2010 xlsx扩展名
     * @param list
     * @param headColums
     * @param sheetName
     * @param path
     * @throws Exception
     */
    private static void writeXlsxHandle(List<Map<String,Object>> list,String[] headColums,String[] headNames, String sheetName,String path) throws Exception {
        File file = new File(path);

        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet(sheetName);
        //当前行
        int curRow = 0;

        //写列的列说明
        if(headNames != null && headNames.length > 0){
            XSSFRow firstRow = sheet.createRow(curRow);
            XSSFCell[] firstCells = new XSSFCell[headNames.length];
            for (int j = 0; j < headNames.length; j++) {
                firstCells[j] = firstRow.createCell(j);
                firstCells[j].setCellValue(new XSSFRichTextString(headNames[j]));
            }

            curRow++;
        }


        //写表头列数据标识.
        if(headColums != null && headColums.length > 0){
            XSSFRow firstRow = sheet.createRow(curRow);
            XSSFCell[] firstCells = new XSSFCell[headColums.length];
            for (int j = 0; j < headColums.length; j++) {
                firstCells[j] = firstRow.createCell(j);
                firstCells[j].setCellValue(new XSSFRichTextString(headColums[j]));
            }

            curRow++;
        }

        if(list != null){
            int countColumnNum = list.size();
            //写数据
            for (int i = 0; i < countColumnNum; i++) {
                XSSFRow row = sheet.createRow(i + curRow);
                Map<String,Object> rowData = list.get(i);

                for (int column = 0; column < headColums.length; column++) {
                    Object cellData = rowData.get(headColums[column]);
                    XSSFCell cell = row.createCell(column);
                    if(CommonUtils.checkExistOrNot(cellData)) {
                    	cell.setCellValue(cellData.toString());
                    }else {
                    	cell.setCellValue("");
                    }
                }
            }
        }

        OutputStream os = new FileOutputStream(file);
        book.write(os);
        book.close();
        os.close();
    }

    
    /**
	 * 
	 * 
	 * 
	 * 
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping("/exportErrorExcel")
	public String exportErrorExcel(HttpServletRequest request, HttpServletResponse resp,String path) {
		
		// 文件保存路径
		String filePath = CommonUtils.getAllMessage("config", "TLS_UPLOAD_"+ OSinfo.getOSname())
				+ path;
		
		try {
			List<Map<String, Object>> mapList = readExcelFile(new File(filePath),2);
			String[] headNames = new String[] { "箱号", "库位", "SKU", "数量", "状态","错误原因" };
			ExcelExportUtil.exportExcelData(mapList, headNames, "sheet1",
					ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX, path, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
     * 读excel文件-支持 2003 2007 2010  xls,xlsx扩展名
     * @param excelFile
     * @param startReadRow 从第几行开始是数据就填几，例如第3行是数据那就填3  （行索引从1开始 ）
     * @return
     * @throws Exception
     */
    public static List<Map<String,Object>> readExcelFile(File excelFile,int startReadRow) throws Exception{
    	
    	
    	
        if (excelFile == null || !excelFile.exists()) {
             return null ;
        }else{
                return readXlsxExcelsHandle(excelFile,startReadRow);
        }

    }

    
    /**
     * 读取xlsx文件（2007 2010格式的excel文件）-默认只读取第一个sheet里的数据
     * @param xlsxFile
     * @param startReadRow 从第几行开始读取
     * @return
     * @throws Exception
     */
     @SuppressWarnings("resource")
	private static List<Map<String,Object>> readXlsxExcelsHandle(File xlsxFile,int startReadRow) throws Exception{
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(xlsxFile);
            XSSFSheet sheet=workbook.getSheetAt(0);//第一个工作表
            if(sheet == null)return null;
            Map<Integer , String> kind = getXlsxKind(sheet,startReadRow);
            if(kind == null)return null;
            int rows=sheet.getLastRowNum();
            List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
            for (int j = startReadRow-1; j <= rows; j++) {//循环工作表中的行，从第三行开始统计数据，不统计列头和字段名
                XSSFRow row=sheet.getRow(j);
                if(row != null){
                    int cells=row.getLastCellNum();
                    Map<String, Object> rowList = getXlsxRowRecords(kind, row, cells);
                    if(rowList != null && !rowList.isEmpty()){
                        listMap.add(rowList);
                    }
                }
            }
            return listMap;
        }catch(Exception e){
            return null;
        }
    }

     /**
      * 得到有字段名的 类型Map（固定第二行为字段类型）
      * @param sheet
      * @param kind
      */
     @SuppressWarnings("deprecation")
	private static Map<Integer, String> getXlsxKind(XSSFSheet sheet,int startReadRow) {
         Map<Integer, String> kind = new HashMap<Integer, String>();
         XSSFRow row=sheet.getRow(startReadRow - 2);//获取字段行
         if(row == null)return null;
         int cells = row.getLastCellNum();//表头格数
         for(int i = 0; i <= cells ; i++){
             XSSFCell cell=row.getCell(i);
             if(cell != null){
                 if(!kind.containsKey(i) && (cell.getCellType() != Cell.CELL_TYPE_BLANK)){
                     kind.put(i, cell.getStringCellValue());
                     continue;
                 }
             }
         }
         return kind;
     }
	
     
     /**
      * 记录一行记录id列为空不统计
      * @param kind
      * @param row
      * @param cells格数
      * @return
      */
     @SuppressWarnings("deprecation")
	private static Map<String , Object> getXlsxRowRecords(Map<Integer, String> kind, XSSFRow row,int cells) {
         Map<String , Object> record = new HashMap<String, Object>();
         for (int k = 0; k <= cells; k++) {
             XSSFCell cell=row.getCell(k);

             if(cell != null){
                 if(kind.containsKey(k)){
                     switch (cell.getCellType()) {
                         case Cell.CELL_TYPE_STRING:
                             record.put(kind.get(k), cell.getStringCellValue());
                             break;
                         case Cell.CELL_TYPE_BOOLEAN:
                             record.put(kind.get(k), cell.getBooleanCellValue());
                             break;
                         case Cell.CELL_TYPE_NUMERIC:
                             record.put(kind.get(k), cell.getNumericCellValue());
                             break;
                         case Cell.CELL_TYPE_FORMULA:
                             try {
                                 record.put(kind.get(k),cell.getRichStringCellValue());
                             } catch (Exception e) {
                                 record.put(kind.get(k),cell.getCellFormula());
                             }
                             break;
                         default:
                             if(k ==0 )return null;//判断id列，为空不统计第一列
                             break;
                     }
                 }
             }
         }
         return record;
     }
     
}
