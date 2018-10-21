package com.bt.orderPlatform.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bt.common.util.CommonUtil;
import com.bt.common.util.DateUtil;
import com.bt.common.util.FileUtil;
import com.bt.common.util.OSinfo;
import com.bt.common.util.XLSXCovertCSVReader;
import com.bt.orderPlatform.controller.form.WaybillMasterQueryParam;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.WaybillMasterService;
import com.bt.sys.model.Account;
import com.bt.sys.util.SysUtil;

import net.sf.json.JSONObject;

/**
 * 运单信息主表控制器
 */
@Controller
@RequestMapping("/control/orderPlatform/updExpectedFromDate")
public class UpdateExpTimeController {

	private static final Logger logger = Logger.getLogger(UpdateExpTimeController.class);

	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterServiceImpl;

	@RequestMapping("/upd_exptime")
	public String upd_exptime(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		return "op/upd_exptime";
	}

	@ResponseBody
	@RequestMapping(value = "/updExptimeUpload")
	public synchronized JSONObject baozunupload(@RequestParam("uploadFile") MultipartFile file, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// 获取当前操作用户
		Account user = SysUtil.getAccountInSession(req);
		// 返回值
		JSONObject result = new JSONObject();
		// 将上传文件写入本地
		// 文件不为空
		if (!file.isEmpty()) {
			try {
				String filePath = CommonUtil.getAllMessage("config",
						"BALANCE_UPLOAD_WAYBILLMASTER_" + OSinfo.getOSname())
						+ new StringBuffer(file.getOriginalFilename()).insert(file.getOriginalFilename().indexOf("."),
								"_" + user.getCreateUser() + "_VAS_"
										+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				FileUtil.isExistFile(filePath);
				file.transferTo(new File(filePath));
				List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, null, 27);
				list.remove(list.get(0));
				int num = 0;
				StringBuffer sb = new StringBuffer();
				Map<Integer,String> poNumberMap = new HashMap<>(); 
				for (int i = 0; i < list.size(); i++) {
					String waybill = list.get(i)[0];
					String expTime = list.get(i)[1];
					int excelH = i+2;
					if(poNumberMap.containsValue(waybill)){
						sb.append("第" + excelH + "行PoNumber重复！\n");
						continue;
					}else{
						poNumberMap.put(excelH, waybill);
					}
					if(!DateUtil.isValidDate(expTime)){
						sb.append("第" + excelH + "行日期格式不正确！\n");
					}
					Map<String, Object> map = new HashMap<>();
					map.put("waybill", waybill);
					map.put("expTime", expTime);
					int j = waybillMasterServiceImpl.updExpTime(map);
					if (j == 0) {
						sb.append("第" + excelH + "行单号不存在\n");
					}
					if (j == 1) {
						num++;
					}
				}
				result.put("success_records", num);
				result.put("failed_content", sb.toString());
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				// upload file failure-文件上传失败
				result.put("result_code", "ULF");
				result.put("result_content", "上传文件与所选类型不匹配或上传文件中标题字段有误！");
			}
		} else {
			// file is empty-文件为空
			result.put("result_code", "FIE");
		}
		return result;
	}
}
