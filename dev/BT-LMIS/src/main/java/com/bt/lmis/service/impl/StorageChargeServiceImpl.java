package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AllAreaMapper;
import com.bt.lmis.dao.AllSingltTrayMapper;
import com.bt.lmis.dao.AllTrayMapper;
import com.bt.lmis.dao.AllVolumeCalculationMapper;
import com.bt.lmis.dao.AllVolumeMapper;
import com.bt.lmis.dao.StorageChargeMapper;
import com.bt.lmis.dao.TotalAreaMapper;
import com.bt.lmis.dao.TotalSingltTrayMapper;
import com.bt.lmis.dao.TotalTrayMapper;
import com.bt.lmis.dao.TotalVolumeCalculationMapper;
import com.bt.lmis.dao.TotalVolumeMapper;
import com.bt.lmis.model.AllArea;
import com.bt.lmis.model.AllSingltTray;
import com.bt.lmis.model.AllTray;
import com.bt.lmis.model.AllVolume;
import com.bt.lmis.model.AllVolumeCalculation;
import com.bt.lmis.model.StorageCharge;
import com.bt.lmis.model.TotalArea;
import com.bt.lmis.model.TotalSingltTray;
import com.bt.lmis.model.TotalTray;
import com.bt.lmis.model.TotalVolume;
import com.bt.lmis.model.TotalVolumeCalculation;
import com.bt.lmis.service.StorageChargeService;
@Service
public class StorageChargeServiceImpl<T> extends ServiceSupport<T> implements StorageChargeService<T> {

	@Autowired
    private StorageChargeMapper<T> scmapper;

	@Autowired
    private TotalAreaMapper<TotalArea> tamapper;

	@Autowired
    private AllAreaMapper<AllArea> aamapper;

	@Autowired
    private TotalVolumeMapper<TotalVolume> tvmapper;
	
	@Autowired
    private AllVolumeMapper<AllVolume> avmapper;
	
	@Autowired
	private TotalVolumeCalculationMapper<TotalVolumeCalculation> tvcmapper;

	@Autowired
	private AllVolumeCalculationMapper<AllVolumeCalculation> avcmapper;

	@Autowired
	private TotalTrayMapper<TotalTray> ttmapper;

	@Autowired
	private AllTrayMapper<AllTray> atmapper;
	
	@Autowired
	private TotalSingltTrayMapper<TotalSingltTray> tstmapper;
	
	@Autowired
	private AllSingltTrayMapper<AllSingltTray> astmapper;
	
	public StorageChargeMapper<T> getMapper() {
		return scmapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return scmapper.selectCount(param);
	}

	@Override
	public List<StorageCharge> findByCBID(String cbid) {
		return scmapper.findByCBID(cbid);
	}

	@Override
	public void saveAreaCGBF(TotalArea ta, T sc) throws Exception{
		try {
			scmapper.insert(sc);
			tamapper.insert(ta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void updateAreaCGBF(TotalArea ta, T sc) throws Exception{
		try {
			scmapper.update(sc);
			tamapper.insert(ta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveAreaALL(AllArea aa, T sc) throws Exception {
		try {
			scmapper.insert(sc);
			aamapper.insert(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateAreaALL(AllArea aa, T sc) throws Exception {
		try {
			scmapper.update(sc);
			aamapper.insert(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveVolumeCGBF(TotalVolume tv, T sc) throws Exception {
		try {
			scmapper.insert(sc);
			tvmapper.insert(tv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateVolumeCGBF(TotalVolume tv, T sc) throws Exception {
		try {
			scmapper.update(sc);
			tvmapper.insert(tv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveVolumeALL(AllVolume av, T sc) throws Exception {
		try {
			scmapper.insert(sc);
			avmapper.insert(av);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateVolumeALL(AllVolume av, T sc) throws Exception {
		try {
			scmapper.update(sc);
			avmapper.insert(av);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveTVC(TotalVolumeCalculation tvc, T sc) throws Exception {
		try {
			scmapper.insert(sc);
			tvcmapper.insert(tvc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTVC(TotalVolumeCalculation tvc, T sc) throws Exception {
		try {
			scmapper.update(sc);
			tvcmapper.insert(tvc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveAVC(AllVolumeCalculation avc, T sc) throws Exception {
		try {
			scmapper.insert(sc);
			avcmapper.insert(avc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAVC(AllVolumeCalculation avc, T sc) throws Exception {
		try {
			scmapper.update(sc);
			avcmapper.insert(avc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveTT(TotalTray tt, T sc) throws Exception {
		try {
			scmapper.insert(sc);
			ttmapper.insert(tt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTT(TotalTray tt, T sc) throws Exception {
		try {
			scmapper.update(sc);
			ttmapper.insert(tt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveAT(AllTray at, T sc) throws Exception {
		try {
			scmapper.insert(sc);
			atmapper.insert(at);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAT(AllTray at, T sc) throws Exception {
		try {
			scmapper.update(sc);
			atmapper.insert(at);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveTST(TotalSingltTray tst, T sc) throws Exception {
		try {
			scmapper.insert(sc);
			tstmapper.insert(tst);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTST(TotalSingltTray tst, T sc) throws Exception {
		try {
			scmapper.update(sc);
			tstmapper.insert(tst);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveAST(AllSingltTray ast, T sc) throws Exception {
		try {
			scmapper.insert(sc);
			astmapper.insert(ast);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAST(AllSingltTray ast, T sc) throws Exception {
		try {
			scmapper.update(sc);
			astmapper.insert(ast);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteByCBID(String id) {
		//根据合同ID查询仓储费集合
		List<Map<String, Object>> list = scmapper.selectSSCById(Integer.valueOf(id));
		StorageCharge scMap=(StorageCharge) list.get(0);
		if(null!=scMap){
			/*
			 * 仓储费计费公式：
			 * 0固定费用 
			 * 1按面积结算 
			 * 2按体积结算 
			 * 3按商品的体积推算 
			 * 4按件数结算 
			 * 5按件数反推所占面积 
			 * 6按托盘结算 
			 * 7按单个托盘的存放数量推算
			 */
			String ssc_sc_type = String.valueOf(scMap.getSsc_sc_type());
			String ssc_cb_id = String.valueOf(scMap.getSsc_cb_id());
			if(ssc_sc_type.equals("0")){
				//固定费用 
			}else if(ssc_sc_type.equals("1")){
				/**
				 * 占用面积
				 *	1无阶梯
				 *	2超过部分
				 *	3总占用
				 **/
				String ssc_occupied_area_type = null!=scMap.getSsc_occupied_area_type() ? scMap.getSsc_occupied_area_type().toString():"";
				if(ssc_occupied_area_type.equals("2")){
					scmapper.deleteSTAByCBID(ssc_cb_id);
				}else if(ssc_occupied_area_type.equals("3")){
					scmapper.deleteSAAByCBID(ssc_cb_id);
				}
			}else if(ssc_sc_type.equals("2")){
				/**
				 * 占用体积
				 *	1无阶梯
				 *	2超过部分
				 *	3总占用
				 **/
				String ssc_volume_type = null!=scMap.getSsc_volume_type() ? scMap.getSsc_volume_type().toString():"";
				if(ssc_volume_type.equals("2")){
					scmapper.deleteSTVByCBID(ssc_cb_id);
				}else if(ssc_volume_type.equals("3")){
					scmapper.deleteSAVByCBID(ssc_cb_id);
				}
			}else if(ssc_sc_type.equals("3")){
				/**
				 * 体积推算
				 *	1无阶梯
				 *	2超过部分
				 *	3总占用
				 **/
				String ssc_volume_calculation_type = null!=scMap.getSsc_volume_calculation_type() ? scMap.getSsc_volume_calculation_type().toString() :"";
				if(ssc_volume_calculation_type.equals("2")){
					scmapper.deleteSTVCByCBID(ssc_cb_id);
				}else if(ssc_volume_calculation_type.equals("3")){
					scmapper.deleteSAVCByCBID(ssc_cb_id);
				}
			}else if(ssc_sc_type.equals("4")){
				
			}else if(ssc_sc_type.equals("5")){
				
			}else if(ssc_sc_type.equals("6")){
				/**
				 * 按托盘结算类型
				 *	1无阶梯
				 *	2超过部分
				 *	3总占用
				 **/
				String ssc_tray_balance_type = null!=scMap.getSsc_tray_balance_type() ? scMap.getSsc_tray_balance_type().toString():"";
				if(ssc_tray_balance_type.equals("2")){
					scmapper.deleteSTTByCBID(ssc_cb_id);
				}else if(ssc_tray_balance_type.equals("3")){
					scmapper.deleteSATByCBID(ssc_cb_id);
				}
			}else if(ssc_sc_type.equals("7")){
				/**
				 * 按单个托盘结算类型
				 *	1无阶梯
				 *	2超过部分
				 *	3总占用
				 **/
				String ssc_single_tray_balance_type = null!=scMap.getSsc_single_tray_balance_type() ? scMap.getSsc_single_tray_balance_type().toString():"";
				if(ssc_single_tray_balance_type.equals("2")){
					scmapper.deleteSTSTByCBID(ssc_cb_id);
				}else if(ssc_single_tray_balance_type.equals("3")){
					scmapper.deleteSASTByCBID(ssc_cb_id);
				}
			}
			//删除仓储费
			scmapper.deleteByCBID(id);
		}
	}

	@Override
	public void deleteSAAByCBID(String cbid) {
		scmapper.deleteSAAByCBID(cbid);
	}

	@Override
	public void deleteSASTByCBID(String cbid) {
		scmapper.deleteSASTByCBID(cbid);
	}

	@Override
	public void deleteSATByCBID(String cbid) {
		scmapper.deleteSATByCBID(cbid);
	}

	@Override
	public void deleteSAVByCBID(String cbid) {
		scmapper.deleteSAVByCBID(cbid);
	}

	@Override
	public void deleteSAVCByCBID(String cbid) {
		scmapper.deleteSAVCByCBID(cbid);
	}

	@Override
	public void deleteSTAByCBID(String cbid) {
		scmapper.deleteSTAByCBID(cbid);
	}

	@Override
	public void deleteSTSTByCBID(String cbid) {
		scmapper.deleteSTSTByCBID(cbid);
	}

	@Override
	public void deleteSTTByCBID(String cbid) {
		scmapper.deleteSTTByCBID(cbid);
	}

	@Override
	public void deleteSTVByCBID(String cbid) {
		scmapper.deleteSTVByCBID(cbid);
	}

	@Override
	public void deleteSTVCByCBID(String cbid) {
		scmapper.deleteSTVCByCBID(cbid);
	}

	@Override
	public void deleteSAAByID(String id) {
		scmapper.deleteSAAByID(id);
	}

	@Override
	public void deleteSASTByID(String id) {
		scmapper.deleteSASTByID(id);
	}

	@Override
	public void deleteSATByID(String id) {
		scmapper.deleteSATByID(id);
	}

	@Override
	public void deleteSAVByID(String id) {
		scmapper.deleteSAVByID(id);
	}

	@Override
	public void deleteSAVyID(String id) {
		scmapper.deleteSAVyID(id);
	}

	@Override
	public void deleteSTAByID(String id) {
		scmapper.deleteSTAByID(id);
	}

	@Override
	public void deleteSTSTByID(String id) {
		scmapper.deleteSTSTByID(id);
	}

	@Override
	public void deleteSTTByID(String id) {
		scmapper.deleteSTTByID(id);
	}

	@Override
	public void deleteSTVByID(String id) {
		scmapper.deleteSTVByID(id);
	}

	@Override
	public void deleteSTVyID(String id) {
		scmapper.deleteSTVyID(id);
	}

	@Override
	public List<StorageCharge> queryWAS(StorageCharge sc) {
		return scmapper.queryWAS(sc);
	}

	@Override
	public List<StorageCharge> findByID(String cbid) {
		return scmapper.findByID(cbid);
	}

	@Override
	public List<StorageCharge> findBySSCID(String cbid) {
		return scmapper.findBySSCID(cbid);
	}
	

}
