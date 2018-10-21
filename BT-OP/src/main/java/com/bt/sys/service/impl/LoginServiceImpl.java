package com.bt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bt.base.BaseConstant;
import com.bt.base.session.SessionUtil;
import com.bt.common.util.CommonUtil;
import com.bt.common.util.MD5Util;
import com.bt.sys.dao.AccountMapper;
import com.bt.sys.model.Account;
import com.bt.sys.model.BusinessPower;
import com.bt.sys.service.LoginService;
import com.bt.sys.util.SysUtil;
import com.bt.sys.web.LoginController;

/** 
 * @ClassName: LoginServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年4月27日 下午5:04:15 
 * 
 * @param <T>
 */
@Transactional
@Service
public class LoginServiceImpl<T> implements LoginService<T> {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
    private AccountMapper<T> mapper;

	@Override
	public String login(HttpServletRequest request, Account account, RedirectAttributes attributes) {
		// 获取缓存在session中的账户信息
		Account temp = SysUtil.getAccountInSession(request);
		// 缓存存在则清空
		if(!CommonUtil.isNull(temp)) {
			SysUtil.removeAccountInSession(request);
			
		}
		// 非空校验
		if(CommonUtil.isNull(account.getCode()) || CommonUtil.isNull(account.getLoginPassword())){
			attributes.addFlashAttribute("message", "用户名或密码不能为空");
			return "redirect:/loginController/rollback.do";
			
		}
		// 密钥加密
		account.setLoginPassword(MD5Util.md5(account.getLoginPassword()));
		// 查询数据库记录
		account = mapper.getAccount(account);
		// 用户不存在或停用
		if (CommonUtil.isNull(account) || !account.getStatus()) {
			attributes.addFlashAttribute("message", "用户名或密码错误！"); 
			return "redirect:/loginController/rollback.do";
			
		}
		try {
			// 缓存用户信息
			SysUtil.setAccountInSession(request, account);
			//
			attributes.addFlashAttribute("account", account);
			BusinessPower bp  = new BusinessPower();
			List<Map<String, Object>> carrierList = mapper.getCarrier(account.getId());
			List<Map<String, Object>> org_codeList = mapper.getOrg(account.getId());
			List<Map<String, Object>> carrier_typeList = mapper.getCarrierType(account.getId());
			List<Map<String, Object>> org_List = getOrg_List(account.getId());
			//List<Map<String, Object>> userid_List = mapper.getUser_id(getPower(org_List));
			bp.setCarrier(getPower(carrierList));
			bp.setCarrier_type(getPower(carrier_typeList));
			bp.setOrg_code(getPower(org_codeList));
			bp.setOrg_code_list(getPower(org_List));
			SessionUtil.setAttr(request, BaseConstant.LOGIN_POWER, bp, Integer.parseInt(CommonUtil.getConfig(BaseConstant.SESSION_INTERVAL)));
			return "redirect:/loginController/main.do";
		} catch (Exception e) {
			logger.error(e);
			attributes.addFlashAttribute("message", "登录异常!"); 
			return "redirect:/loginController/rollback.do";
			
		}
		
	}
	
	
	
	private List<Map<String, Object>> getOrg_List(String id) {
		List<Map<String, Object>> org_codeList = mapper.getOrg(id);
		String power = getPower(org_codeList);
		String code = mapper.selectByCode(power);
		List<String> list2 = new ArrayList<String>();
		List<String> list = get(code,list2);
		list.add(code);
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		
		for (String string : list) {
			Map<String, Object>  map = new HashMap<String, Object>();
			map.put("code", string);
			list1.add(map);
		}
		return list1;
	}



	private List<String> get(String code,List<String> list1) {
		// TODO Auto-generated method stub
		List<Map<String, Object>>  list = mapper.selectBysuperior_id(code);
		if(list.size()!=0){  
            for(int i =0;i<list.size();i++){  
            	list1.add(list.get(i).get("code").toString());  
            	get(list.get(i).get("code").toString(),list1);
            }  
       }  
		return list1;
	}



	public String getPower(List<Map<String, Object>> list){
		String carrier = null;
		if(list.size()!=0){
			carrier="'";
			for (int i = 0; i < list.size(); i++) {
				if(list!=null){
					if(i+1==list.size()){
						carrier=carrier+list.get(i).get("code");
					}else{
						carrier=carrier+list.get(i).get("code")+"','";
					}
				}
			}
			carrier=carrier+"'";
		}
		return carrier;
	}
	
	public static void main(String[] args) {
		String t =MD5Util.md5("vfadmin");
		System.out.println(t);
	}
}