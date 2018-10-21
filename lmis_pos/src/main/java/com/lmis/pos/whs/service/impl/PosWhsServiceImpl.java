package com.lmis.pos.whs.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.util.StringUtil;
import com.lmis.pos.whs.dao.PosWhsMapper;
import com.lmis.pos.whs.model.PosWhs;
import com.lmis.pos.whs.service.PosWhsServiceInterface;

/** 
 * @ClassName: PosWhsServiceImpl
 * @Description:(仓库主表业务层实现类)
 * @author codeGenerator
 * @date 2018-05-29 10:13:18
 * 
 * @param <T>
 */
@SuppressWarnings("unchecked")
@Transactional(rollbackFor=Exception.class)
@Service
public class PosWhsServiceImpl<T extends PosWhs> implements PosWhsServiceInterface<T> {
	
	@Autowired
	private PosWhsMapper<T> posWhsMapper;
	@Autowired
    private HttpSession session;

	@Override
	public LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception {
	    Page<T> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
	    posWhsMapper.retrieve(t);
        LmisResult<T> lmisResult = new LmisResult<T>();
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        lmisResult.setMetaAndData(page.toPageInfo());
        return lmisResult;
	}

	@Override
	public LmisResult<?> executeSelect(T t) throws Exception {
	    List<T> result = posWhsMapper.retrieve(t);
        if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
        if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}

	@Override
	public LmisResult<?> executeInsert(T t) throws Exception {
	    //类型校验
	    if(!StringUtil.isNotEmpty(t.getWhsType())){
            throw new Exception("类型不为空");
        }
	    if(!StringUtil.isNotEmpty(t.getContacts())){
            throw new Exception("联系人不为空");
        }
        if(!StringUtil.isNotEmpty(t.getPhone())){
            throw new Exception("联系电话不为空");
        }
	    if(!StringUtil.isNotEmpty(t.getIsPoAllocate())){
	        throw new Exception("是否PO分仓不为空");
	    }
	    if(!StringUtil.isNotEmpty(t.getPoAllocatePriority())){
	        throw new Exception("PO分仓优先级不为空");
	    }
	    if(!StringUtil.isNotEmpty(t.getScMaxShoe())){
	        throw new Exception("最大库容鞋不为空");
	    }
	    if(!StringUtil.isNotEmpty(t.getScMaxDress())){
	        throw new Exception("最大库容服不为空");
	    }
	    if(!StringUtil.isNotEmpty(t.getScMaxAccessory())){
	        throw new Exception("最大库容配不为空");
	    }
	    if(!StringUtil.isNotEmpty(t.getArrivalLeadDays())){
	        throw new Exception("到货提前天数不为空");
	    }
	    t.setCreateBy(session.getAttribute("lmisUserId").toString());
	    t.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
	    LmisResult<T> result =  new LmisResult<>();
	    if(posWhsMapper.create(t)>0){
	        result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
	    }else{
	        result.setCode(LmisConstant.RESULT_CODE_ERROR);
	    }
		return result;
	}

    @Override
	public LmisResult<?> executeUpdate(T t) throws Exception {
        LmisResult<T> result =  new LmisResult<>();
        //类型校验
        if(!StringUtil.isNotEmpty(t.getWhsType())){
            throw new Exception("类型不为空");
        }
        if(!StringUtil.isNotEmpty(t.getContacts())){
            throw new Exception("联系人不为空");
        }
        if(!StringUtil.isNotEmpty(t.getPhone())){
            throw new Exception("联系电话不为空");
        }
        if(!StringUtil.isNotEmpty(t.getIsPoAllocate())){
            throw new Exception("是否PO分仓不为空");
        }
        if(!StringUtil.isNotEmpty(t.getPoAllocatePriority())){
            throw new Exception("PO分仓优先级不为空");
        }
        if(!StringUtil.isNotEmpty(t.getScMaxShoe())){
            throw new Exception("最大库容鞋不为空");
        }
        if(!StringUtil.isNotEmpty(t.getScMaxDress())){
            throw new Exception("最大库容服不为空");
        }
        if(!StringUtil.isNotEmpty(t.getScMaxAccessory())){
            throw new Exception("最大库容配不为空");
        }
        if(!StringUtil.isNotEmpty(t.getArrivalLeadDays())){
            throw new Exception("到货提前天数不为空");
        }
        
        
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());
        if(posWhsMapper.update(t)>0){
            result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        }else{
            result.setCode(LmisConstant.RESULT_CODE_ERROR);
        }
        return result;
	}

	@Override
	public LmisResult<?> deletePosWhs(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 删除操作
		return new LmisResult<T> (LmisConstant.RESULT_CODE_SUCCESS, posWhsMapper.logicalDelete(t));
	}
	
	@Override
    public LmisResult<T> switchWhs(T t) throws Exception{
        //更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, posWhsMapper.shiftValidity(t));
    }

    @Override
    public LmisResult<T> checkCodeName(T t) throws Exception{
        if(StringUtil.isNotEmpty(t.getId())){
            if(!StringUtil.isNotEmpty(t.getWhsName())){
                throw new Exception("请输入名称");
            }
            String whsName = t.getWhsName().trim();
            if(whsName.length()>30){
                throw new Exception("名称长度不能超过30个字符");
            }
            if(StringUtil.isSpecialChar(whsName)){
                throw new Exception("名称不能包含特殊符号");
            }
            PosWhs pw2 = new PosWhs();
            pw2.setId(t.getId());
            pw2.setWhsName(whsName);
            if(posWhsMapper.countCodeNameUpd((T) pw2)>0){
                return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, "已存在名称，继续修改?");
            }
            if(!StringUtil.isNotEmpty(t.getIsTailGoods())){
                throw new Exception("是否尾货仓不为空");
            }
            if(t.getIsTailGoods()){
                PosWhs pw3 = new PosWhs();
                pw3.setId(t.getId());
                pw3.setIsTailGoods(t.getIsTailGoods());
                if(posWhsMapper.countCodeNameUpd((T) pw3)>0){
                    throw new Exception("尾货仓只能有一个");
                }
            }
        }else{
            //code校验
            if(!StringUtil.isNotEmpty(t.getWhsCode())){
                throw new Exception("请输入编码");
            }
            String whsCode = t.getWhsCode().trim();
            if(whsCode.length()>30){
                throw new Exception("编码长度不能超过30个字符");
            }
            if(StringUtil.isSpecialChar(whsCode)){
                throw new Exception("编码不能包含特殊符号");
            }
            PosWhs pw = new PosWhs();
            pw.setWhsCode(whsCode);
            if(posWhsMapper.countCodeNameUpd((T) pw)>0){
                throw new Exception("编码已被占用，请输入新的编码");
            }
            //name校验
            if(!StringUtil.isNotEmpty(t.getWhsName())){
                throw new Exception("请输入名称");
            }
            String whsName = t.getWhsName().trim();
            if(whsName.length()>30){
                throw new Exception("名称长度不能超过30个字符");
            }
            if(StringUtil.isSpecialChar(whsName)){
                throw new Exception("名称不能包含特殊符号");
            }
            PosWhs pw2 = new PosWhs();
            pw2.setWhsName(whsName);
            if(posWhsMapper.countCodeNameUpd((T) pw2)>0){
                return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, "已存在名称，继续修改?");
            }
            if(!StringUtil.isNotEmpty(t.getIsTailGoods())){
                throw new Exception("是否尾货仓不为空");
            }
            if(t.getIsTailGoods()){
                PosWhs pw3 = new PosWhs();
                pw3.setIsTailGoods(t.getIsTailGoods());
                if(posWhsMapper.countCodeNameUpd((T) pw3)>0){
                    throw new Exception("尾货仓只能有一个");
                }
            }
        }
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, "");
    }

    @Override
    public LmisResult<T> queryWhsToSetSaleRate() throws Exception{
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, posWhsMapper.queryWhsToSetSaleRate());
    }

    @Override
    public LmisResult<T> updateWhsSaleRate(List<T> list) throws Exception{
        int count = posWhsMapper.queryCountWhsToSetSaleRate();
        if(count!=list.size()){
            throw new Exception("区域仓库销售占比可设置数目对应不上");
        }
        T updateModel = null;                        
        for (T t : list){
            BigDecimal saleRate =  t.getSaleRate();//这行不要删 
            updateModel = t;
            updateModel.setSaleRate(null);
            List<T>ts = posWhsMapper.retrieve(updateModel);
            
            if(ts==null||ts.isEmpty()||ts.size()!=1) throw new Exception("区域仓库数据异常");
            
            updateModel.setSaleRate(saleRate);
            updateModel.setVersion(ts.get(0).getVersion());
            updateModel.setUpdateBy(session.getAttribute("lmisUserId").toString());
            
            if(posWhsMapper.update(updateModel)<1) throw new Exception("区域仓库销售数据异常");
        }
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, "");
    }

}
