package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.BingdingDetaiDao;
import com.jumbo.dao.warehouse.DistriButionAreaDao;
import com.jumbo.dao.warehouse.DistriButionAreaLocDao;
import com.jumbo.dao.warehouse.DistriButionAreaTypeDao;
import com.jumbo.dao.warehouse.DwhDistriButionAreaLocDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.TwhdistriButionAreaTypeDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.AreaType;
import com.jumbo.wms.model.warehouse.BingdingDetai;
import com.jumbo.wms.model.warehouse.DistriButionArea;
import com.jumbo.wms.model.warehouse.DistriButionAreaLoc;
import com.jumbo.wms.model.warehouse.DwhDistriButionAreaLoc;
import com.jumbo.wms.model.warehouse.DwhDistriButionAreaLocCommand;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.TwhdistriButionAreaType;

import cn.baozun.bh.connector.gymboree.barcoderule.model.Row;
import loxia.annotation.QueryParam;
import loxia.dao.Page;
import loxia.dao.Pagination;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;

/**
 * 
 * @author lijinggong+2018年8月3日
 *
 *
 */
@Service("distriButionAreaMagger")
public class DistriButionAreaManagerImpl  extends BaseManagerImpl implements DistriButionAreaManager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1306005031064563096L;
	
	@Autowired
	private DistriButionAreaDao  distriButionAreaDao;
    
	@Autowired
	private DistriButionAreaLocDao  distriButionAreaLocDao;
	
	@Autowired
	private DistriButionAreaTypeDao  distriButionAreaTypeDao;
	
	@Autowired
	private TransactionTypeDao transactionTypeDao;
	
	@Autowired
	private TwhdistriButionAreaTypeDao twhdistriButionAreaTypeDao;
	
	@Autowired
	private BingdingDetaiDao bingdingDetaiDao;
	
	@Autowired
	private DwhDistriButionAreaLocDao dwhDistriButionAreaLocDao;
	@Resource(name="distriButionReader")
    private ExcelReader distriButionReader;
    
	
	
    @Override
	public Pagination<DistriButionArea> getDistriButionArea(Long mainWhid,Page page) {
		return distriButionAreaDao.getDistriButionArea(mainWhid,page);
	}
	
	/**
	 * 根据分配区域编码和分配区域名称进行模糊查询
	 * 
	 */
	@Override
	public Pagination<DistriButionArea> findDistriButionArea(Long mainWhid,Page page,String distriButionAreaCode,String distriButionAreaName) {
		if("".equals(distriButionAreaCode)) {
			distriButionAreaCode = null;
		}else {
		    distriButionAreaCode = "%" + distriButionAreaCode + "%";
		}
		if("".equals(distriButionAreaName)) {
			distriButionAreaName = null;
		}else {
		    distriButionAreaName = "%" + distriButionAreaName + "%";
		}
		RowMapper<DistriButionArea>	rowMapper = new BeanPropertyRowMapper<DistriButionArea>(DistriButionArea.class);
		Pagination<DistriButionArea>  ds = distriButionAreaDao.findDistriButionArea(mainWhid,page,distriButionAreaCode,distriButionAreaName,rowMapper);
		return ds;
	}
	
	 /**
     * msg:0代表失败 1:代表成功 2:代表分配区域编码存在 3:代表分配区域名称存在
     */
	@Override
	public Integer updateDistriButionArea(Long mainWhid,Long id, String distriButionAreaCode, String distriButionAreaName) {
		int msg = 0;
        if(repeatCheckDistriButionAreaCode(mainWhid, distriButionAreaCode, distriButionAreaName) > 0) {
            int msg2 = 2;
            return msg2; 
        }
        if(repeatCheckDistriButionAreaName(mainWhid, distriButionAreaCode, distriButionAreaName) > 0) {
            int msg3 = 3;
            return msg3;
        }
		if(repeatCheckDistriButionAreaCode(mainWhid, distriButionAreaCode, distriButionAreaName) == 0 && repeatCheckDistriButionAreaName(mainWhid, distriButionAreaCode, distriButionAreaName) == 0 ) {
		    distriButionAreaDao.updateDistriButionArea(id, distriButionAreaCode, distriButionAreaName);
            msg = 1; 
		}
		return msg;
	}


	@Override
	public Integer deleteDistriButionArea(Long id) {
	    int success = 0;
	    distriButionAreaDao.deleteLocByDistriButionAreaId(id);
	    distriButionAreaDao.deleteTypeByDistriButionAreaId(id);
	    int s3 = distriButionAreaDao.deleteDistriButionArea(id);
	    if(1 == s3 ) {
	        success = 1;
	    }
		return success;
	}

    /**
     * msg:0代表失败 1:代表成功 2:代表分配区域编码存在 3:代表分配区域名称存在
     */
	@Override
	public Integer insertDistriButionArea(String distriButionAreaCode, String distriButionAreaName,Long mainWhid, Long createId, String createUser) {
		int msg = 0;
		 if(repeatCheckDistriButionAreaCode(mainWhid, distriButionAreaCode, distriButionAreaName) > 0) {
            int msg2 = 2;
            return msg2; 
         }
         if(repeatCheckDistriButionAreaName(mainWhid, distriButionAreaCode, distriButionAreaName) > 0) {
            int msg3 = 3;
            return msg3;
         }
		if(repeatCheckDistriButionAreaCode(mainWhid, distriButionAreaCode, distriButionAreaName) == 0 && repeatCheckDistriButionAreaName(mainWhid, distriButionAreaCode, distriButionAreaName) == 0 ) {
			Date createTime = new Date(); 
			DistriButionArea distriButionArea = new DistriButionArea();
			distriButionArea.setDistriButionAreaCode(distriButionAreaCode);
			distriButionArea.setDistriButionAreaName(distriButionAreaName);
			distriButionArea.setMainWhid(mainWhid);
			distriButionArea.setCreateId(createId);
			distriButionArea.setCreateId(createId);
			distriButionArea.setCreateUser(createUser);
			distriButionArea.setCreatETime(createTime);
	        distriButionArea.setVersion(0);
			DistriButionArea s = distriButionAreaDao.save(distriButionArea);
			if(null != s) {
				msg = 1;
			}
		}
		return msg;
	}
	
   /*  //进行重复性校验
	private Integer repeatCheck(Long mainWhid,String distriButionAreaCode,String distriButionAreaName) {
		RowMapper<Integer> rowMapper = new SingleColumnRowMapper<Integer>(Integer.class);
		int num = distriButionAreaDao.repeatCheck(mainWhid,distriButionAreaCode, distriButionAreaName, rowMapper);
		return num;		
	}*/
	
	//进行区域编码重复性校验
	private Integer repeatCheckDistriButionAreaCode(Long mainWhid,String distriButionAreaCode,String distriButionAreaName) {
	    RowMapper<Integer> rowMapper = new SingleColumnRowMapper<Integer>(Integer.class);
        Integer num = distriButionAreaDao.repeatCheckDistriButionAreaCode(mainWhid, distriButionAreaCode,rowMapper);
        return num;	    
	}
	
	//进行区域名称重复性校验
    private Integer repeatCheckDistriButionAreaName(Long mainWhid,String distriButionAreaCode,String distriButionAreaName) {
        RowMapper<Integer> rowMapper = new SingleColumnRowMapper<Integer>(Integer.class);
        Integer num = distriButionAreaDao.repeatCheckDistriButionAreaName(mainWhid, distriButionAreaName,rowMapper);
        return num;    
    }
	
	
	@Override
	public String judgeDistriButionArea(String distriButionAreaCode) {
		return distriButionAreaDao.judgeDistriButionArea(distriButionAreaCode);
	}
   
	
	 /* 区域绑定库位
     */
    //分页查询区域绑定库位
	@Override
	public Pagination<DistriButionAreaLoc> getDistriButionAreaLoc(Long mainWhid,Page page) {
		RowMapper<DistriButionAreaLoc>	rowMapper = new BeanPropertyRowMapper<DistriButionAreaLoc>(DistriButionAreaLoc.class);
		return distriButionAreaLocDao.getDistriButionAreaLoc(mainWhid,page, rowMapper);
	}
	
	@Override
	public Pagination<DistriButionAreaLoc> findDistriButionAreaLoc(Long mainWhid,Page page,String locCodeName, String locCode,
			String locDistriButionAreaCode, String locDistriButionAreaName) {
	    if("".equals(locCodeName) ) {
	        locCodeName = null;
	    }
	    if("".equals(locCode)) {
	        locCode = null;
	    }else {
	        locCode = "%" + locCode + "%";
	    }
	    if("".equals(locDistriButionAreaName)) {
	        locDistriButionAreaName = null;
	    }
	    if("".equals(locDistriButionAreaCode)) {
	        locDistriButionAreaCode = null;
	    }
	    RowMapper<DistriButionAreaLoc> rowMapper = new BeanPropertyRowMapper<DistriButionAreaLoc>(DistriButionAreaLoc.class);
		return distriButionAreaLocDao.findDistriButionAreaLoc(mainWhid, page, locCodeName, locCode, locDistriButionAreaCode, locDistriButionAreaName, rowMapper);
	}
	//删除
	@Override
	public Integer deleteDistriButionAreaLoc(Long id) {
		return distriButionAreaLocDao.deleteDistriButionAreaLoc(id);
	}
	//查询库区
	@Override
	public List<DistriButionAreaLoc> getName() {
	    RowMapper<DistriButionAreaLoc> rowMapper = new BeanPropertyRowMapper<DistriButionAreaLoc>(DistriButionAreaLoc.class);
		return distriButionAreaLocDao.getName(rowMapper);
	}
	//查询分配区域名称
	@Override
	public List<DistriButionAreaLoc> getDistriButionNameList(Long mainWhid) {
	    RowMapper<DistriButionAreaLoc> rowMapper = new BeanPropertyRowMapper<DistriButionAreaLoc>(DistriButionAreaLoc.class);
		return distriButionAreaLocDao.getDistriButionNameList(mainWhid, rowMapper);
	}
	
	//查询分配区域编码
	@Override
    public List<DistriButionAreaLoc> getDistriButionCodeList(Long mainWhid) {
        RowMapper<DistriButionAreaLoc> rowMapper = new BeanPropertyRowMapper<DistriButionAreaLoc>(DistriButionAreaLoc.class);
        return distriButionAreaLocDao.getDistriButionCodeList(mainWhid, rowMapper);
    }
	
	/* 区域绑定作业类型
	*/
	@Override
	public Pagination<AreaType> getDistriButionAreaType(Long mainWhid,Page page) {
		Pagination<DistriButionArea> PageDisIN = distriButionAreaDao.getDistriButionArea(mainWhid,page);
		return getAreaType(PageDisIN);
	}
	/**
	 * 根据typeDistriButionAreaCode和typeDistriButionAreaName进行模糊查询
	 */
	@Override
	public Pagination<AreaType> findDistriButionAreaType(Long mainWhid,Page page, String typeDistriButionAreaCode,
		 String typeDistriButionAreaName) {
		 Pagination<DistriButionArea> Pagination = findDistriButionArea(mainWhid,page, typeDistriButionAreaCode, typeDistriButionAreaName);
		 return getAreaType(Pagination);
	}
    /**
         *  统计已绑定的作业类型
     * @param PageDisIN
     * @return
     */
	private Pagination<AreaType> getAreaType(Pagination<DistriButionArea> PageDisIN){
		Pagination<AreaType> PageDisOUT = new Pagination<AreaType>();
		List<AreaType> listOUT = new ArrayList<AreaType>();
		List<DistriButionArea> listIN = PageDisIN.getItems();
		for(DistriButionArea distriButionAreaIN : listIN) {
			AreaType distriButionAreaOUT = new AreaType();
			Long  id = distriButionAreaIN.getId();
			String distriButionAreaCode = distriButionAreaIN.getDistriButionAreaCode();
			String distriButionAreaName = distriButionAreaIN.getDistriButionAreaName();
			RowMapper<Integer> rowMapper = new SingleColumnRowMapper<Integer>(Integer.class);
			int num = distriButionAreaTypeDao.getDistriButionAreaType( distriButionAreaCode, distriButionAreaName,rowMapper);
			distriButionAreaOUT.setId(id);
			distriButionAreaOUT.setDistriButionAreaCode(distriButionAreaCode);
			distriButionAreaOUT.setDistriButionAreaName(distriButionAreaName);
		    distriButionAreaOUT.setNum(num);
			listOUT.add(distriButionAreaOUT);
		}
		PageDisOUT.setItems(listOUT);
		return  PageDisOUT;
		
	}
	@Override
	public Pagination<TransactionType> getTransActionType(Long mainWhid,Page page) {
		
		return transactionTypeDao.getTransActionType(page);
	}
    //区域绑定作业类型
	/**
	 * 0:代表绑定失败 1:代表绑定成功 2:代表绑定已经存在
	 */
	@Override
	public Integer bindingTransAction(String distriButionAreaIds, String transActionTypeIds) {
		String[] distriButionArea = distriButionAreaIds.split(",");
		String[] transActionType = transActionTypeIds.split(",");
		for(int i=0;i<distriButionArea.length;i++) {
			long distriButionAreaId = Long.parseLong(distriButionArea[i]);
			for(int j=0;j<distriButionArea.length;j++) {
				TwhdistriButionAreaType twhdistriButionAreaType = new TwhdistriButionAreaType();
				long transActionTypeId = Long.parseLong(transActionType[j]);
				twhdistriButionAreaType.setDistriButionAreaId(distriButionAreaId);
				twhdistriButionAreaType.setTransActionTypeId(transActionTypeId);
				if(judgeBinding(distriButionAreaId, transActionTypeId) == 0) {
					 twhdistriButionAreaTypeDao.save(twhdistriButionAreaType);
				}else {
	                return 2;
				}
			}
		}
        return 1;
	}
	//判断是否存在
	public Integer judgeBinding (Long distriButionAreaId,Long transActionTypeId ) {
		RowMapper<Integer> rowMapper = new SingleColumnRowMapper<Integer>(Integer.class);
		Integer num = twhdistriButionAreaTypeDao.judgeBinding(distriButionAreaId, transActionTypeId, rowMapper);
		return num;
	}
    //取消绑定
	@Override
	public Integer cancelBinding(String distriButionAreaIds, String transActionTypeIds) {
		String[] distriButionArea = distriButionAreaIds.split(",");
		String[] transActionType = transActionTypeIds.split(",");
		for(int i=0;i<distriButionArea.length;i++) {
			long distriButionAreaId = Long.parseLong(distriButionArea[i]);
			for(int j=0;j<distriButionArea.length;j++) {
				long transActionTypeId = Long.parseLong(transActionType[j]);
				twhdistriButionAreaTypeDao.cancelBinding(distriButionAreaId, transActionTypeId);
			}
		}
		return 1; 
	}

	
	//查看已绑定的明细列表
	@Override
	public Pagination<BingdingDetai> findBingdingDetai(Long mainWhid,Page page, Long bingdingDetaiId) {
	    RowMapper<BingdingDetai> rowMapper = new BeanPropertyRowMapper<BingdingDetai>(BingdingDetai.class);
		return bingdingDetaiDao.findBingdingDetai(mainWhid,page, bingdingDetaiId,rowMapper);
	}

	/**
       * 导入区域类型绑定库位
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus saveDwhDistriButionAreaLocImport(Long mainWhid,File file, OperationUnit op) throws Exception {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> beans = new HashMap<String, Object>();
        List<DwhDistriButionAreaLocCommand> typeCommandList = null;
        ReadStatus rs = null;
        rs = distriButionReader.readSheet(new FileInputStream(file), 0, beans);
        typeCommandList = (List<DwhDistriButionAreaLocCommand>) beans.get("dwhDistriButionAreaLocReadList");
        int i = 4;
        for(DwhDistriButionAreaLocCommand dwhDistriButionAreaLocCommand : typeCommandList) {
           String msg = verifyDwhDistriButionAreaLoc(mainWhid,dwhDistriButionAreaLocCommand);
           if (!"".equals(msg)) {
               rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
               sb.append("{第" + i+"的" + msg + "};\r\n");
           }
           i++;
        }
        return rs;
    }


    /**进行文件的校验
     * @param dwhDistriButionAreaLocCommand 
     * @return  
     */
    private String verifyDwhDistriButionAreaLoc(Long mainWhid,DwhDistriButionAreaLocCommand dwhDistriButionAreaLocCommand) {
        String msg = "";
        if(!StringUtils.hasText(dwhDistriButionAreaLocCommand.getCode())) {
            msg = "库位不能为空!";  
            return msg;
        }
        if(!StringUtils.hasText(dwhDistriButionAreaLocCommand.getCodeName())) {
            msg = "库区不能为空！！";  
            return msg;
        }
        if(!StringUtils.hasText(dwhDistriButionAreaLocCommand.getDistriButionCode())) {
            msg = "分配区域编码不能为空！";  
            return msg;
        }
        if(!StringUtils.hasText(dwhDistriButionAreaLocCommand.getDistriButionName())) {
            msg = "分配区域名称不能为空！";  
            return msg;
        }
        RowMapper<Integer> rowMapper = new SingleColumnRowMapper<Integer>(Integer.class);
        //根据分配区域编码和分配区域编码查询相关的DISTRIBUTION_ID;
        Integer distriButionId = dwhDistriButionAreaLocDao.getDistriButionId(mainWhid,dwhDistriButionAreaLocCommand.getDistriButionCode(),dwhDistriButionAreaLocCommand.getDistriButionName(), rowMapper);
        if(distriButionId == null ) {
            msg = "【" + dwhDistriButionAreaLocCommand.getDistriButionCode() + dwhDistriButionAreaLocCommand.getDistriButionName()+"】分配区域编码或者分配区域名称不正确！";
            return msg;
        }
        //根据库位和库区查询LOCATION_ID
        Integer locationId = dwhDistriButionAreaLocDao.getLocationId(dwhDistriButionAreaLocCommand.getCode(), dwhDistriButionAreaLocCommand.getCodeName(),rowMapper);
        if(locationId == null ) {
            msg = "【" + dwhDistriButionAreaLocCommand.getCode() +"," + dwhDistriButionAreaLocCommand.getCodeName() + "】库区或者库位不存在！";
        }
        //根据distriButionId和locationId判断数据是否已经存在
        int num = dwhDistriButionAreaLocDao.getNum(distriButionId, locationId, rowMapper);
        if(0 != num ) {
            msg = "【" + dwhDistriButionAreaLocCommand.getCode() +"," 
                     + dwhDistriButionAreaLocCommand.getCodeName() +","
                     + dwhDistriButionAreaLocCommand.getDistriButionCode() +","
                     +dwhDistriButionAreaLocCommand.getDistriButionName()+"】已存在";
            return msg;
        }
        DwhDistriButionAreaLoc dwhDistriButionAreaLoc = new DwhDistriButionAreaLoc();
        dwhDistriButionAreaLoc.setDistriButionAreaId(distriButionId);
        dwhDistriButionAreaLoc.setLocationId(locationId);
        dwhDistriButionAreaLocDao.save(dwhDistriButionAreaLoc);
        return msg;
    }

    /**
     * 导出库位
     */
    @Override
    public List<DwhDistriButionAreaLocCommand> exportDistriButionArea(Long mainWhid,String locCodeName,String locCode,String locDistriButionAreaCode,String locDistriButionAreaName) {
        RowMapper<DwhDistriButionAreaLocCommand> rowMapper = new BeanPropertyRowMapper<DwhDistriButionAreaLocCommand>(DwhDistriButionAreaLocCommand.class);
        
        return dwhDistriButionAreaLocDao.exportDistriButionArea(mainWhid,locCodeName,locCode,locDistriButionAreaCode,locDistriButionAreaName,rowMapper);
    }
   
    public static void main(String[] args) {
        String s = null;
        String s1 = "";
        if(!s1.equals(s)) {
            System.out.println("不存在");
        }
    }

  
}
