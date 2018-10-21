package com.lmis.pos.common.job;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmis.common.dataFormat.GetUuidForJava;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.pos.common.dao.BaseExcelMapper;
import com.lmis.pos.common.dao.PosDataFileTempleteMapper;
import com.lmis.pos.common.dao.ServiceDataFileLogMapper;
import com.lmis.pos.common.model.PosDataFileTemplete;
import com.lmis.pos.common.model.ServiceDataFileLog;
import com.lmis.pos.common.service.TableCommandService;
import com.lmis.pos.common.util.ConstantCommon;
import com.lmis.pos.common.util.POIUtil;
import com.lmis.pos.common.util.SpringUtil;
import com.lmis.pos.common.util.SqlFilterUtil;
import com.lmis.pos.common.util.ZipUtil;
import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.impl.StreamingCell;

public class DataSourceAsyncJob {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAsyncJob.class);  

	private static ServiceDataFileLogMapper<ServiceDataFileLog> serviceDataFileLogMapper;
	
	private static PosDataFileTempleteMapper<PosDataFileTemplete> posDataFileTempleteMapper;
	
	private static TableCommandService tableCommandService;
	
	private static RedisTemplate<String, Long> redisTemplate;
	
	private static String excelImportFileUploadPath;
	
	private static String excelImportFileDownloadPath;
	
	/**
	 * 更新文件状态
	 * Title: updateFileLogStatus
	 * Description: 
	 * @param fileId
	 * @param status
	 * @param opsCount
	 * @param updateBy
	 * @return
	 * @author lsh8044
	 * @date 2018年4月20日
	 */
	public static int updateFileLogStatus(String fileId, int status, 
			Long dataCount, Long opsCount, String updateBy,
			String opsErrorInfo) {
        ServiceDataFileLog serviceDataFileLog = new ServiceDataFileLog();
        serviceDataFileLog.setId(fileId);
    	serviceDataFileLog.setDataCount(dataCount);
    	serviceDataFileLog.setOpsCount(opsCount);
        serviceDataFileLog.setOpsStatus(status);
        serviceDataFileLog.setOpsErrorInfo(opsErrorInfo);
        serviceDataFileLog.setUpdateBy(updateBy);
        return serviceDataFileLogMapper.update(serviceDataFileLog);
	}
	
	/**
	 * Title: updateTemplete
	 * Description: 修改模板状态
	 * @param posDataFileTemplete
	 * @return
	 * @author lsh8044
	 * @date 2018年5月31日
	 */
	public static int updateTemplete(PosDataFileTemplete posDataFileTemplete) {
		return posDataFileTempleteMapper.update(posDataFileTemplete);
	}

	/**
	 * Title: parseExcelFile
	 * Description: xlsExcel解析(读取、插入)
	 * @param directoryId
	 * @param baseFile
	 * @param titleMap {column(列名):title(Excel标题名)}
	 * @param tableName
	 * @param updateBy
	 * @param isAll
	 * @param... isDefault是否使用默认配置不传为true，true时则插入表中必须存在
	 						id
			                bat_id
			                row_number
			                create_by
			                update_by
			                create_time
			                update_time
	 * @author lsh8044
	 * @throws Exception 
	 * @date 2018年4月12日
	 */
	public static LmisResult<String> parseExcelFile(
			String directoryId,File baseFile,Map<String,String> titleMap,String tableName,
			String updateBy,Boolean isAll,Boolean... isDefault) throws Exception {
		LmisResult<String> result = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
		
		//获取文件总记录
		//获得Workbook工作薄对象  
        Workbook workbook = null;
        Sheet sheet = null;
        Cell cell = null;
        FileInputStream ins = null;
	    int firstRowNum = 0;
	    int lastRowNum = 0;
	    int firstCellNum = 0;
	    int lastCellNum = 0;
        String cellValue = null;
		List<String> columns = null;//字段列对象
        List<Integer> columnsIndex = null;//字段列索引
        List<String> cells = null;//数据列对象
        int cellNumber = 0;
        int cellOffset = 0;
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回  
        List<List<String>> list = null;
		LmisResult<String> validateResult = null;
        SimpleDateFormat sdf = new SimpleDateFormat(SqlFilterUtil.JAVA_POI_DATE_FORMAT);
        
        int batchSize = 1000;//批次处理统一量
		try {
			ins = new FileInputStream(baseFile);
			if(baseFile.getName().endsWith(".xls")) {
				workbook = new HSSFWorkbook(ins);
			} else {
				workbook = StreamingReader.builder()
		                .rowCacheSize(batchSize)  //缓存到内存中的行数，默认是10
		                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
		                .open(ins);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
			}
			
            long opsCount = 0;
			ValueOperations<String, Long> opsForValue = redisTemplate.opsForValue();
	        for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  

	        	columns = new ArrayList<>();//字段列对象
	        	cellOffset = 0;
	        	columnsIndex = new ArrayList<>();//列索引
	        	list = new ArrayList<>(batchSize);
	        	
	            //获得当前sheet工作表  
	            sheet = workbook.getSheetAt(sheetNum);  
	            if(sheet == null){  
	                continue;
	            }
			    if(!isAll && sheetNum>0) {
			    	//单sheet遍历模式
			    	break;
			    }
			    if(sheet.getLastRowNum()<=0) {
			    	result.setCode(LmisConstant.RESULT_CODE_ERROR);
					result.setMessage("第" + (sheetNum+1) + "个sheet内容不合法！");
					break;
			    }
			    
			    int rowNum = firstRowNum;
			    for (Row row : sheet) {
			    	//标题行
		        	if(rowNum == firstRowNum) {
		                lastCellNum = row.getLastCellNum();
		                //循环当前行
		                //基本字段
		                if(!(isDefault != null && isDefault.length>1 && isDefault[0])) {
			                columns.add("id");
			                columns.add("bat_id");
			                columns.add("row_number");
			                columns.add("create_by");
			                columns.add("update_by");
			                columns.add("create_time");
			                columns.add("update_time");
			                columns.add("file_name");
		                	cellOffset = columns.size();//基本阶段
		                }
		                for (Entry<String,String> entry : titleMap.entrySet()) {
			                for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
			                    cell = row.getCell(cellNum);  
			                    cellValue = POIUtil.getCellValue(cell);
			                    if(StringUtils.isNotBlank(cellValue)) {
			                    	if(entry.getValue().toUpperCase().replaceAll(" ", "")
			                    			.equals(cellValue.toUpperCase().replaceAll(" ", ""))) {
//			                    	if(!SqlFilterUtil.sqlFilterValid(cellValue)) {
						        		columns.add(entry.getKey());
						        		columnsIndex.add(cellNum);
						        		break;
			                    	}
			                    } else {
			            			result = flushWorkBooke(workbook);
			            			result.setCode(LmisConstant.RESULT_CODE_ERROR);
			            			result.setData(directoryId);
			            			result.setMessage("标题列存在空值");
			            			return result;
			                    }
			                } 
						}
		                if(titleMap.size() > columns.size()-cellOffset) {
	            			result = flushWorkBooke(workbook);
	            			result.setCode(LmisConstant.RESULT_CODE_ERROR);
	            			result.setData(directoryId);
	            			result.setMessage("文件上传模板格式不正确，请检查标题是否正确");
	            			return result;
		                }
		                if(CollectionUtils.isEmpty(columns)) {
	            			result = flushWorkBooke(workbook);
	            			result.setCode(LmisConstant.RESULT_CODE_ERROR);
	            			result.setData(directoryId);
	            			result.setMessage("未找到匹配的表头，请确认模板格式是否正确");
	            			return result;
		                }
		        	} else {
		                //循环当前行  
		                cells = new ArrayList<>();
		                //固定列
		                //基本字段
		                if(cellOffset>0) {
			                cells.add(GetUuidForJava.getUuidForJava());
			                cells.add(directoryId);
			                cells.add(String.valueOf(rowNum));
			                cells.add(updateBy);
			                cells.add(updateBy);
			                cells.add(sdf.format(new Date()));
			                cells.add(sdf.format(new Date()));
			                cells.add(baseFile.getName());
		                }
		                cellNumber = 0;
		                for (Integer index : columnsIndex) {
		                	cell = row.getCell(index);  
		                    cellValue = poTitleParseCell(cell,columns.get(cellNumber+cellOffset));
		                	validateResult = valditePoData(cellValue,columns.get(cellNumber+cellOffset));
	                    	if(LmisConstant.RESULT_CODE_SUCCESS.equals(validateResult.getCode())) {
			                    cells.add(cellValue);
	                    	} else {
		            			result = flushWorkBooke(workbook);
		            			result.setCode(LmisConstant.RESULT_CODE_ERROR);
		            			result.setData(directoryId);
		            			result.setMessage("内容列存在异常格式的数据，在第"+(rowNum+1)+"行,第"+(index+1)+"列，值:" 
		            			+ cellValue + "要求：" + validateResult.getMessage());
		            			logger.info(result.getMessage());
		            			return result;
	                    	}
	                    	cellNumber++;
						}
		                list.add(cells);
	        			//更新进度，写入redis
		                opsCount++;
	        	    	opsForValue.set(directoryId,opsCount);
	        	    	if(list.size() >= batchSize ){
		        		    //插入表
		        	    	tableCommandService.insertBatchData(tableName,columns,list);
		        	    	list.clear();
		        		}
		        	}
	                rowNum++;
				}
			    if(CollectionUtils.isNotEmpty(list)) {
        		    //插入表
        	    	tableCommandService.insertBatchData(tableName,columns,list);
        	    	list.clear();
			    }
	        }
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.setCode(LmisConstant.RESULT_CODE_ERROR);
			result.setData(directoryId);
			result.setMessage("未找到对应文件！message:" + e1.getMessage());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.setCode(LmisConstant.RESULT_CODE_ERROR);
			result.setData(directoryId);
			result.setMessage("文件解析读取异常！message:" + e1.getMessage());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.setCode(LmisConstant.RESULT_CODE_ERROR);
			result.setData(directoryId);
			result.setMessage("数据插入异常，请检查数据是否完整！message:" + e1.getMessage());
		} finally {
			result = flushWorkBooke(workbook);
			result.setData(directoryId);
		}
		
		return result;
	}
	
	private static String poTitleParseCell(Cell cell, String columnName) {
		if(cell == null) {
			return null;
		}
		
		StreamingCell sc = null;
		Object scCellRawContent = null;
		if(cell instanceof StreamingCell) {
			sc = (StreamingCell) cell;
			scCellRawContent = sc == null ? cell.getStringCellValue() : sc.getRawContents();
		}
		
		if("sold_to".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		if("ship_to".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		if("order_type".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		if("door".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		if("crd_time".equals(columnName)) {
			return POIUtil.getCellValue(cell);
		}
		if("cancel_time".equals(columnName)) {
			return POIUtil.getCellValue(cell);
		}
		if("sales_org".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		if("plant".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		if("po_number".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		if("contract".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		if("prod_code".equals(columnName)) {
			return POIUtil.getCellValue(cell);
		}
		if("launch_date".equals(columnName)) {
			return POIUtil.getCellValue(cell);
		}
		if("prod_size".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		if("prod_qty".equals(columnName)) {
			return scCellRawContent == null ? null : scCellRawContent.toString();
		}
		return cell.getStringCellValue();
	}

	private static LmisResult<String> valditePoData(String cellValue, String columnName) {
		LmisResult<String> result = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
		StringBuffer message = new StringBuffer();

		if("sold_to".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			}
		}
		if("ship_to".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			}
		}
		if("order_type".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			}
		}
		if("crd_time".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			} else {
				try {
					POIUtil.format.get().parse(cellValue);
				} catch (Exception e) {
					message.append("日期列不符合格式，格式为:yyyy-MM-dd|");
				}
			}
		}
		if("cancel_time".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			} else {
				try {
					POIUtil.format.get().parse(cellValue);
				} catch (Exception e) {
					message.append("日期列不符合格式，格式为:yyyy-MM-dd|");
				}
			}
		}
		if("sales_org".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			}
		}
		if("plant".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			}
		}
		if("po_number".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			}
		}
		if("prod_code".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			}
		}
		if("launch_date".equals(columnName)) {
			if(StringUtils.isNotBlank(cellValue)) {
				try {
					POIUtil.format.get().parse(cellValue);
				} catch (Exception e) {
					message.append("日期列不符合格式，格式为:yyyy-MM-dd|");
				}
			}
		}
		if("prod_size".equals(columnName)) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数据列不能为空|");
			}
			if(StringUtils.isNotBlank(cellValue) && 
					cellValue.length()>11) {
				message.append("数字列长度不能超过50|");
			}
		}
		if(columnName.equals("prod_qty")) {
			if(StringUtils.isBlank(cellValue)) {
				message.append("数字列不能为空|");
			}
			if(StringUtils.isNotBlank(cellValue) && 
					cellValue.length()>11) {
				message.append("数字列长度不能超过11|");
			} 
			if(StringUtils.isNotBlank(cellValue) && 
					!isNumericZidai(cellValue)) {
				message.append("数字列不能存在其他字符|");
			}
		}
		if(message.length()>0) {
			result.setMessage(message.toString());
			return result;
		}
		result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return result;
	}
	
	public static boolean isNumericZidai(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

	private static LmisResult<String> flushWorkBooke(Workbook workbook){
		LmisResult<String> result = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
		try {
			if(workbook != null)
				workbook.close();
			result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setCode(LmisConstant.RESULT_CODE_ERROR);
			result.setMessage("文件日志记录创建失败！message:" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * Title: moveFile
	 * Description: 转存文件并创建任务记录
	 * @param file
	 * @return result{code:LmisConstant.RESULT_CODE_SUCCESS,data:fileId} 
	 * 										| result{code:LmisConstant.RESULT_CODE_ERROR,message:""}
	 * @author lsh8044
	 * @date 2018年5月28日
	 */
	public static LmisResult<String> moveFile(MultipartFile file,String updateBy) {
		LmisResult<String> result = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");

		String fileId = GetUuidForJava.getUuidForJava();
		String fileName = file.getOriginalFilename();
		
		File baseFile = null;
		if(fileName.endsWith(".zip")) {
			//zip转存逻辑
			//转存
			baseFile = new File(buildDirectoryUploadPath(true,fileId),fileId+".zip");
			if(!baseFile.getParentFile().exists()) {
				baseFile.getParentFile().mkdirs();
			}
	        InputStream in = null;
	        ZipArchiveInputStream zin = null;
			try {
				//移动文件
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(baseFile));
				result.setData(fileId);
				//探查压缩文件内容
				//zip内容仅限.xls,.xlsx判断
		        //ZipEntry 类用于表示 ZIP 文件条目。
				ZipArchiveEntry ze;
		        int fileCount = 0;
				in = new BufferedInputStream(new FileInputStream(baseFile));
				zin = new ZipArchiveInputStream(in);
				while((ze=zin.getNextZipEntry())!=null){
				    if(!ze.isDirectory()){
				        //为空的文件夹什么都不做
				    	logger.error("file:"+ze.getName()+"\nsize:"+ze.getSize()+"bytes");
				    	if(!ze.getName().endsWith(".xls") && !ze.getName().endsWith(".xlsx")) {
							result.setCode(LmisConstant.RESULT_CODE_ERROR);
							result.setMessage("不支持的文件类型,.zip文件内容仅支持.xls,.xlsx文件！");
							break;
				    	}
						result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
				    	fileCount ++;
				    }
				    if(fileCount > 1) {
						result.setCode(LmisConstant.RESULT_CODE_ERROR);
						result.setMessage("仅支持.zip存在单个文件，请重新上传！");
						break;
				    }
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setMessage("文件解压错误,message:"+e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setMessage("文件读写错误,message:"+e.getMessage());
			} finally {
				try {
					if(zin != null)
						zin.close();
					if(in != null)
						in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setMessage("文件读写错误,message:"+e.getMessage());
				}
			}
		} else if(fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
			//转存
			if(fileName.endsWith(".xlsx")) {
				baseFile = new File(buildDirectoryUploadPath(true,fileId),fileId+".xlsx");
			} else {
				baseFile = new File(buildDirectoryUploadPath(true,fileId),fileId+".xls");
			}
			if(!baseFile.getParentFile().exists()) {
				baseFile.getParentFile().mkdirs();
			}
			try {
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(baseFile));
				result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
				result.setData(fileId);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setMessage("未找到文件,message:"+e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setMessage("文件读写错误,message:"+e.getMessage());
			}
		} else {
			result.setMessage("仅支持上传.xlsx、.xls、.zip文件");
		}
		
		if(LmisConstant.RESULT_CODE_SUCCESS.equals(result.getCode())){
			//初步解析文件、创建任务
				//解析总记录数
			return createFileLogJob(fileId, baseFile,updateBy,false);
		} else {
			if(baseFile != null) {
				baseFile.delete();
				baseFile.getParentFile().delete();
			}
		}
		
		return result;
	}
	
	/**
	 * Title: exprotFile
	 * Description: 文件导出任务
	 * @param dataSourceId
	 * @param fileId
	 * @param execSql
	 * @param updateBy
	 * @param isTemplete 默认为false
	 * @return
	 * @throws Exception
	 * @author lsh8044
	 * @date 2018年4月23日
	 */
	@SuppressWarnings("unchecked")
	public static <T extends PersistentObject> LmisResult<?> exprotFile(String fileId, String fileName,
			Map<String,Object> titleMap,BaseExcelMapper<T> mapper,T queryObject,
			String updateBy,Boolean isTemplete) throws Exception{
		LmisResult<String> result = new LmisResult<String>(LmisConstant.RESULT_CODE_SUCCESS,"");
		result.setData(fileId);
		
		if(isTemplete == null) {
			result.setMessage("isTemplete参数缺失");
			return result;
		}
		
		ValueOperations<String, Long> opsForValue = redisTemplate.opsForValue();
		
		//预导出数据
		Page<Map<String, Object>> page = null;
		PageInfo<Map<String, Object>> pageInfo = null;
    	if(!isTemplete) {
    		page = PageHelper.startPage(1, ConstantCommon.excelExprotPageSize);
    		mapper.retrieveAsMap(queryObject);
    		pageInfo = page.toPageInfo();
    		//更新任务进度
    		DataSourceAsyncJob.updateFileLogStatus(fileId, 1, pageInfo.getTotal(),0l, updateBy,null);
    	} else {
    		//更新任务进度
    		DataSourceAsyncJob.updateFileLogStatus(fileId, 1, 0l,0l, updateBy,null);
    	}

		//导出Excel
    	Object title = null;
    	Map<String,Boolean> titleObject = null;
	    SXSSFWorkbook wb = null;
	    Sheet sh = null;
	    Row row = null;
	    Cell cell = null;
	    Object cellValue = null;
	    CellStyle cellStyle = null;
	    DataFormat df = null;

	    List<Map<String,Boolean>> resolveList = new ArrayList<>();
    	Boolean isDecimal = false;//data是否为数值型
        Boolean isInteger=false;//data是否为整数
        Boolean isPercent=false;//data是否为百分数
	    
	    int rowNum = 1;
	    int columnNum = 0;
	    Boolean titleIsFont = false;
	    
	    if(CollectionUtils.isNotEmpty(page) || isTemplete
	    		) {

			try {
				
			    while(CollectionUtils.isNotEmpty(page) || isTemplete) {
				    
			    	if(!titleIsFont) {
			    		wb = new SXSSFWorkbook(1000);  //内存保存1000行数据
			    		sh = wb.createSheet(); // 建立新的sheet对象  
			    		row = sh.createRow(0);   // 创建第一行对象  (表头)
					    for (Entry<String,Object> entry : titleMap.entrySet()) {
					    	cell = row.createCell(columnNum);
					    	title = entry.getValue();
					    	titleObject = null;
					    	if(title != null && title instanceof Map) {
					    		titleObject = new LinkedHashMap<>();
						    	isDecimal = (Boolean) ((Map<String,Object>) title).get("isDecimal");//data是否为数值型
				                isInteger= (Boolean) ((Map<String,Object>) title).get("isInteger");//data是否为整数
				                isPercent= (Boolean) ((Map<String,Object>) title).get("isPercent");//data是否为百分数
				                if(isDecimal == null) {
				                	titleObject.put("isDecimal", false);
				                } else {
				                	titleObject.put("isDecimal", isDecimal);
				                }
				                if(isInteger == null) {
				                	titleObject.put("isInteger", false);
				                } else {
				                	titleObject.put("isInteger", isDecimal);
				                }
				                if(isPercent == null) {
				                	titleObject.put("isPercent", false);
				                } else {
				                	titleObject.put("isPercent", isDecimal);
				                }
				                if(((Map<String,Object>) title).get("title") == null) {
					                cell.setCellValue("");
				                } else {
				                	cell.setCellValue(((Map<String,Object>) title).get("title").toString());
				                }
					    	} else {
						    	isDecimal = false;//data是否为数值型
				                isInteger= false;//data是否为整数
				                isPercent= false;//data是否为百分数
				                if(title == null) {
					                cell.setCellValue("");
				                } else {
					                cell.setCellValue(title.toString());
				                }
					    	}
			                resolveList.add(titleObject);
					    	columnNum++;
						}
					    titleIsFont = true;
			    	}
			    	if(!isTemplete) {
					    for (Map<String,Object> rowData : page) {
					    	row = sh.createRow(rowNum);
					    	columnNum=0;
						    for (Entry<String,Object> entry : titleMap.entrySet()) {
						    	cell = row.createCell(columnNum);
						    	cellValue = rowData.get(entry.getKey());
						    	cellStyle = wb.createCellStyle();
						    	df = null;

						    	if(resolveList.get(columnNum) != null) {
				                    df = wb.createDataFormat(); // 此处设置数据格式
						    		if (resolveList.get(columnNum).get("isInteger")) {
				                    	cellStyle.setDataFormat(df.getFormat("#,#0"));//数据格式只显示整数
				                    } else {
				                    	//如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
						                if (resolveList.get(columnNum).get("isDecimal")) {
					                    	cellStyle.setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点
						                } 
//						                else if(resolveList.get(columnNum).get("isPercent")) {
//					                    	cellStyle.setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点
//						                }
				                    }
				                    // 设置单元格格式
				                    cell.setCellStyle(cellStyle);
				                    // 设置单元格内容为double类型
				                    cell.setCellValue(Double.parseDouble(cellValue.toString()));
						    	} else {
				                	cell.setCellStyle(cellStyle);
				                    // 设置单元格内容为字符型
				                	cell.setCellValue(String.valueOf(cellValue));
						    	}
						    	
						    	columnNum++;
							}
					    	opsForValue.set(fileId, Long.valueOf(rowNum));
					    	rowNum++;
						}
					    //尝试继续取数据(不进行count)
						page = PageHelper.startPage(pageInfo.getPageNum()+1, pageInfo.getPageSize(),false);
						mapper.retrieveAsMap(queryObject);
			    	} else {
			    		break;
			    	}
				}
			} catch (Exception e) {
				result.setCode(LmisConstant.RESULT_CODE_ERROR);
				result.setData(fileId);
				result.setMessage(e.getMessage());
				e.printStackTrace();
			} finally {
				if(wb != null && 
						LmisConstant.RESULT_CODE_SUCCESS.equals(result.getCode())) {
					File file = new File(buildDirectoryDownloadPath(true,fileId));
					if(!file.exists()) {
						file.mkdirs();
					}
					FileOutputStream fileOut = null;
					try {
						fileOut = new FileOutputStream(
								buildDirectoryDownloadPath(
										true,fileId,fileName
								));  
						wb.write(fileOut);
					} catch (Exception e) {
						result.setCode(LmisConstant.RESULT_CODE_ERROR);
						result.setData(fileId);
						result.setMessage(e.getMessage());
						e.printStackTrace();
					}  finally {
						if(fileOut != null)
							fileOut.close(); 
						wb.dispose();
					}
				}
			}
			return result;
	    } else {
			result.setCode(LmisConstant.RESULT_CODE_ERROR);
			result.setData(fileId);
			result.setMessage("未查找到结果集，请确认导出结果不为空");
	    }
		return result;
		
	}
	
	/**
	 * Title: createFileLogJob
	 * Description: 创建导入文件任务记录
	 * @param directoryId
	 * @param file
	 * @param isAll 遍历模式默认为false单sheet，true全部sheet
	 * @return
	 * @author lsh8044
	 * @date 2018年4月24日
	 */
	public static LmisResult<String> createFileLogJob(String directoryId, File file,String updateBy,Boolean isAll) {
		LmisResult<String> result = new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS,"");
		
		if(isAll == null) {
			isAll = false;
		}
		
		long dataCount = 0l;
		//获取文件总记录
		//获得Workbook工作薄对象  
        Workbook workbook = null;
        Sheet sheet = null;
        
		try {
			//zip文件预先解压
			if(file.getName().endsWith(".zip")) {
				//解压
				ZipUtil.unzip(buildDirectoryUploadPath(true,directoryId,directoryId+".zip"), 
						buildDirectoryUploadPath(true,directoryId));
				//读取
				//获取解压目录对象
				File baseFile = new File(buildDirectoryUploadPath(true,directoryId));
				if(baseFile.isDirectory()) {
					File[] fileList = new File[0];
					//获取所有.xlsx,.xls文件集合
					fileList = baseFile.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return name.endsWith(".xls") || name.endsWith(".xlsx");
						}
					});
					//转变baseFile为待处理文件对象(固定一个zip只处理一个excel文件)
					if(fileList.length > 0) {
						file = fileList[0];
					}
				}
			}
			
			//分析文件获取文件总记录数
			if(file.getName().endsWith(".xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(file));
			} else if(file.getName().endsWith(".xlsx")){
				workbook = StreamingReader.builder()
		                .rowCacheSize(1000)  //缓存到内存中的行数，默认是10
		                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
		                .open(new FileInputStream(file));  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
			} else {
				result.setMessage("创建任务异常，不识别的文件类型，必须未.xls、.xlsx、.zip文件类型");
				return result;
			}
	        for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
	            //获得当前sheet工作表  
	            sheet = workbook.getSheetAt(sheetNum);  
	            if(sheet == null){  
	                continue;
	            }
			    if(!isAll && sheetNum>0) {
			    	//单sheet遍历模式
			    	break;
			    }
			    if(sheet.getLastRowNum()>0) {
			    	dataCount+=sheet.getLastRowNum();
			    } else {
					result.setCode(LmisConstant.RESULT_CODE_ERROR);
					result.setMessage("第" + (sheetNum+1) + "个sheet内容不合法！");
					break;
			    }
	        }
	        
	        if(LmisConstant.RESULT_CODE_SUCCESS.equals(result.getCode())) {
		        //插入文件上传日志记录
		        ServiceDataFileLog serviceDataFileLog = new ServiceDataFileLog();
		        serviceDataFileLog.setId(directoryId);
		        serviceDataFileLog.setFileName(file.getName());
		        serviceDataFileLog.setFilePath(buildDirectoryUploadPath(false,directoryId,file.getName()));
		        serviceDataFileLog.setDataCount(dataCount);
		        serviceDataFileLog.setOpsCount(0l);
		        serviceDataFileLog.setOpsStatus(0);
		        serviceDataFileLog.setCreateBy(updateBy);
		        serviceDataFileLog.setUpdateBy(updateBy);
		        int r = serviceDataFileLogMapper.create(serviceDataFileLog);
		        if(r>0) {
		        	result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		        	result.setData(directoryId);
		        } else {
					result.setCode(LmisConstant.RESULT_CODE_ERROR);
					result.setMessage("文件日志记录创建失败！");
		        }
	        }
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.setCode(LmisConstant.RESULT_CODE_ERROR);
			result.setMessage("未找到对应文件！");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.setCode(LmisConstant.RESULT_CODE_ERROR);
			result.setMessage("文件解析读取异常！");
		} finally {
			try {
				if(workbook != null)
					workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setCode(LmisConstant.RESULT_CODE_ERROR);
				result.setMessage("文件日志记录创建失败！");
			}
		}
		return result;
	}
	
	/**
	 * Title: deleteServiceDataSourceUploadFile
	 * Description: 删除文件
	 * @param fileId
	 * @return
	 * @throws Exception
	 * @author lsh8044
	 * @date 2018年5月29日
	 */
	public static LmisResult<?> deleteServiceDataSourceUploadFile(String fileId) throws Exception {
		LmisResult<String> lmisResult = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
		
		if(StringUtils.isBlank(fileId)) {
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage("fileId不能为空");
			return lmisResult;
		}
		
        ServiceDataFileLog serviceDataFileLog = new ServiceDataFileLog();
        serviceDataFileLog.setId(fileId);
        serviceDataFileLog.setIsDeleted(false);
        serviceDataFileLog = serviceDataFileLogMapper.retrieve(serviceDataFileLog).get(0);
        if(serviceDataFileLog.getOpsStatus()>1) {
    		File baseFile = new File(DataSourceAsyncJob.buildDirectoryUploadPath(true,fileId));
    		if(baseFile.exists() && baseFile.isDirectory()) {
    			File[] fileList = new File[0];
    			//获取所有.xlsx,.xls文件集合
    			fileList = baseFile.listFiles();
    			//转变baseFile为待处理文件对象(固定一个zip只处理一个excel文件)
    			for (File file : fileList) {
    				file.delete();
    			}
    			baseFile.delete();
    		}
			lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
			lmisResult.setMessage("文件已成功删除");
        }
		return lmisResult;
	}

	/**
	 * Title: buildDirectoryUploadPath
	 * Description: 目录构建，默认前缀excelImportFileUploadPath值
	 * @param isDefault 是否开启默认模式,true:开启,false:关闭
	 * @param directory
	 * @return
	 * @author lsh8044
	 * @date 2018年4月12日
	 */
	public static String buildDirectoryUploadPath(Boolean isDefault,String... directory) {
		StringBuffer directoryPath = null;
		
		if(isDefault==null || isDefault) 
			directoryPath = new StringBuffer(excelImportFileUploadPath);
		else
			directoryPath = new StringBuffer();
		for (int i = 0; i < directory.length; i++) {
			if(!directoryPath.toString().endsWith(File.separator)) directoryPath.append(File.separator);
			directoryPath.append(directory[i]);
		}
		return directoryPath.toString();
	}

	/**
	 * Title: buildDirectoryDownloadPath
	 * Description: 目录构建，默认前缀excelImportFileDownloadPath值
	 * @param isDefault 是否开启默认模式,true:开启,false:关闭
	 * @param directory
	 * @return
	 * @author lsh8044
	 * @date 2018年4月12日
	 */
	public static String buildDirectoryDownloadPath(Boolean isDefault,String... directory) {
		StringBuffer directoryPath = null;
		
		if(isDefault==null || isDefault) 
			directoryPath = new StringBuffer(excelImportFileDownloadPath);
		else
			directoryPath = new StringBuffer();
		for (int i = 0; i < directory.length; i++) {
			if(!directoryPath.toString().endsWith(File.separator)) directoryPath.append(File.separator);
			directoryPath.append(directory[i]);
		}
		return directoryPath.toString();
	}
	
	/**
	 * Title: initJobContext
	 * Description: 上下文初始化
	 * @author lsh8044
	 * @date 2018年5月29日
	 */
	@SuppressWarnings("unchecked")
	public static void initJobContext() {
		serviceDataFileLogMapper = 
        		(ServiceDataFileLogMapper<ServiceDataFileLog>) SpringUtil.getBean("serviceDataFileLogMapper");
		posDataFileTempleteMapper = 
        		(PosDataFileTempleteMapper<PosDataFileTemplete>) SpringUtil.getBean("posDataFileTempleteMapper");
		tableCommandService =
				(TableCommandService) SpringUtil.getBean("tableCommandServiceImpl");
		redisTemplate = 
				(RedisTemplate<String, Long>) SpringUtil.getBean("redisTemplate");
		Environment env =  SpringUtil.getBean(Environment.class);
		excelImportFileUploadPath = env.getProperty("lmis_pos.podata.excel_import_file_upload_path");
		excelImportFileDownloadPath = env.getProperty("lmis_pos.podata.excel_import_file_download_path");
//		new String(env.getProperty("com.zyd.title2").getBytes("ISO-8859-1"), "UTF-8");
	}
	
}
