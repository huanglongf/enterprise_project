package com.lmis.pos.whsCover.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.util.StringUtil;
import com.lmis.pos.whsCover.dao.PosWhsCoverMapper;
import com.lmis.pos.whsCover.model.PosWhsCover;
import com.lmis.pos.whsCover.service.PosWhsCoverServiceInterface;

/** 
 * @ClassName: PosWhsCoverServiceImpl
 * @Description: TODO(仓库覆盖区域设置业务层实现类)
 * @author codeGenerator
 * @date 2018-05-29 16:30:53
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosWhsCoverServiceImpl<T extends PosWhsCover> implements PosWhsCoverServiceInterface<T> {
	
	@Autowired
	private PosWhsCoverMapper<T> posWhsCoverMapper;
	@Autowired
    private HttpSession session;
	
    @Override
    public LmisResult<?> whsAreaSearch(T t) throws Exception{
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,posWhsCoverMapper.retrieve(t));
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> whsBindingArea(String whsCode,String province,String citys) throws Exception{
        if(!StringUtil.isNotEmpty(province)){
            throw new Exception("请输入省");
        }
        if(!StringUtil.isNotEmpty(whsCode)){
            throw new Exception("whsCode不为空");
        }
        PosWhsCover pwc = new PosWhsCover();
        pwc.setWhsCode(whsCode);
        pwc.setProvince(province);
        pwc.setCreateBy(session.getAttribute("lmisUserId").toString());
        pwc.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
        pwc.setProvinceName(posWhsCoverMapper.getaddrNameByCode(province));
        if(!StringUtil.isNotEmpty(citys)){
            pwc.setCity("");
            pwc.setCityName("");
            if(posWhsCoverMapper.checkProvince(province)==0){
                posWhsCoverMapper.create((T)pwc);
            }else{
                throw new Exception("该省已有区域被仓库覆盖");
            }
        }else{
            String [] cityss = citys.split(",");
            for(String cityCode:cityss){
                pwc.setCity(cityCode);
                pwc.setCityName(posWhsCoverMapper.getaddrNameByCode(cityCode));
                Map<String,Object> map = new HashMap<>();
                map.put("province", province);
                map.put("city", cityCode);
                Map<String,Object> map2 = new HashMap<>();
                map2.put("province", province);
                map2.put("city", "");
                if(posWhsCoverMapper.checkProvinceCity(map)>0||posWhsCoverMapper.checkProvinceCity(map2)>0){
                    throw new Exception(pwc.getCityName()+"已有仓库覆盖");
                }
                posWhsCoverMapper.create((T)pwc);
            }
        }
        return new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS,"");
    }

    @Override
    public LmisResult<?> deleteBindingArea(T t) throws Exception{
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,posWhsCoverMapper.delete(t));
    }

    

}
