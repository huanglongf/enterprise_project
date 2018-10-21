package com.lmis.pos.whsOutPlan.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.common.util.POIUtil;
import com.lmis.pos.common.util.ZipUtil;
import com.lmis.pos.skuMaster.controller.PosSkuMasterController;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlan;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanAreaScale;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanLog;
import com.lmis.pos.whsOutPlan.service.PosWhsOutAreaScaleServiceInterface;
import com.lmis.pos.whsOutPlan.service.PosWhsOutPlanServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 *@author jinggong.li
 *@date 2018年6月22日
 */
@Api(value = "")
@RestController
@RequestMapping(value="/pos")
public class PosWhsOutAreaScaleController {
	private static Log logger = LogFactory.getLog(PosWhsOutAreaScaleController.class);
	
	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Value("${lmis_pos.podata.excel_import_file_upload_path}")
	String upZipFilePath;
	
	@Resource(name="posWhsOutAreaScaleServiceImpl")
	PosWhsOutAreaScaleServiceInterface<PosWhsOutPlan> posWhsOutAreaScaleService;
	
	
	@ApiOperation(value="查询区域尺码比例")
	@RequestMapping(value = "/selectPosWhsOutAreaScale", method = RequestMethod.POST)
    public String selectPosWhsOutAreaScale(
    		@RequestParam(value="size",required=false) String size,
    		@RequestParam(value="skuGgg",required=false) String skuGgg,
    		@ModelAttribute DynamicSqlParam<PosWhsOutPlanAreaScale> dynamicSqlParam,
    		@ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsOutAreaScaleService.selectPosWhsOutAreaScale(dynamicSqlParam, pageObject,size,skuGgg));
    }
	
	
	@ApiOperation(value = "导入")
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String importExcel(@RequestParam @ApiParam(value = "上传文件", required = false) MultipartFile file)
			throws Exception {
		unZip(file.getInputStream(), upZipFilePath + file.getName() + "zip");
		logger.info("解压文件后存放路径：" + upZipFilePath);
		String fileName = ZipUtil.unzipStr(upZipFilePath + file.getName() + "zip", upZipFilePath);
		logger.info("解压后文件名称：" + fileName);
		List<Map<String, Object>> excelList = readExcel(upZipFilePath + fileName);
		return JSON.toJSONString(posWhsOutAreaScaleService.savePosWhsOutAreaScaleInfo(excelList, upZipFilePath, upZipFilePath + fileName));
	}
	
	private void unZip(InputStream is, String destFilePath) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		File file = new File(destFilePath);
		File parent = file.getParentFile();
		if (parent != null && (!parent.exists())) {
			parent.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = bis.read(buf, 0, 1024)) != -1) {
			fos.write(buf, 0, len);
		}
		bos.flush();
		bos.close();
		bis.close();
	}
	
	private List<Map<String, Object>> readExcel(String filePath) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		File file = new File(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		if (workbook != null) {
			// 获得当前sheet工作表
			int sheetNum = 0;
			Sheet sheet = workbook.getSheetAt(sheetNum);
			if (sheet == null) {
				return Collections.EMPTY_LIST;
			}
			// 获得当前sheet的开始行
			int firstRowNum = sheet.getFirstRowNum();
			// 获得当前sheet的结束行
			int lastRowNum = sheet.getLastRowNum();
			// 获得当前行的开始列
			int firstCellNum = 0;
			// 获得当前行的列数
			int lastCellNum = 0;
			// 循环除了第一行的所有行
			for (int rowNum = firstRowNum; rowNum < lastRowNum; rowNum++) {

				// 获得当前行
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					break;
				}

				if (rowNum == firstRowNum) {
					// 获得当前行的开始列
					firstCellNum = row.getFirstCellNum();
					// 获得当前行的列数
					lastCellNum = row.getPhysicalNumberOfCells();
				} else {
					// 循环当前行
					Map<String, Object> map = new HashMap<String, Object>();
					StringBuffer sb = new StringBuffer("");
					for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
						Cell cell = row.getCell(cellNum);
						String cellValue = POIUtil.getCellValue(cell) == null ? "" : POIUtil.getCellValue(cell);
						switch (cellNum) {
						case 0:
							map.put("skuGgg", cellValue);
							sb.append(cellValue);
							break;
						case 1:
							map.put("skuCategory", cellValue);
							sb.append(cellValue);
							break;
						case 2:
							map.put("gndrAgeDesc", cellValue);
							sb.append(cellValue);
							break;
						case 3:
							map.put("gblCatSumDesc", cellValue);
							sb.append(cellValue);
							break;							
						case 4:
							map.put("gblCatCoreFocsDesc", cellValue);
							sb.append(cellValue);
							break;
						case 5:
							map.put("size", cellValue);
							sb.append(cellValue);
							break;
						case 6:
							map.put("whs_code", cellValue);
							sb.append(cellValue);
							break;
						case 7:
							map.put("rate", cellValue);
							sb.append(cellValue);
							break;
						default:
							break;
						}
					}
					if(sb.length() > 0){
						map.put("vNumber", sb.toString());
						list.add(map);
					}
				}
			}
			workbook.close();
		}
		return list;
	}
}
