package com.jumbo.wms.manager.task.autoPicking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.warehouse.AutoPickingListRoleDao;
import com.jumbo.dao.warehouse.AutoPickingListRoleDetailTransportatorDao;
import com.jumbo.dao.warehouse.AutoPlConfigDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.BiChannelGroupDao;
import com.jumbo.dao.warehouse.ImportFileLogDao;
import com.jumbo.dao.warehouse.SkuSizeConfigDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.mergeSta.MergeStaTaskImpl;
import com.jumbo.wms.manager.warehouse.AutoPickingListRoleManager;
import com.jumbo.wms.manager.warehouse.CreatePickingListManagerProxy;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.AutoPickingListRole;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleDetailCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleDetailTransportator;
import com.jumbo.wms.model.warehouse.AutoPlConfig;
import com.jumbo.wms.model.warehouse.AutoPlStatus;
import com.jumbo.wms.model.warehouse.BiChannelGroup;
import com.jumbo.wms.model.warehouse.BiChannelRefCommand;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationPickingType;


@Service("autoPickingManagerProxy")
public class AutoPickingManagerProxyImpl extends BaseManagerImpl implements AutoPickingManagerProxy {

    private static final long serialVersionUID = -8724749022722647813L;



    @Autowired
    private AutoPlConfigDao apcDao;
    @Autowired
    private BiChannelGroupDao bgDao;
    @Autowired
    private AutoPickingListRoleDao aplrDao;
    @Autowired
    private BiChannelDao bcDao;
    @Autowired
    private CreatePickingListManagerProxy createPickingListManagerProxy;
    @Autowired
    private AutoPickingManager autoPickingManager;
    @Autowired
    private SkuSizeConfigDao skuSizeConfigDao;
    @Autowired
    private AutoPickingListRoleDetailTransportatorDao aplrdtDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private AutoPickingListRoleManager autoPickingListRoleManager;
    @Autowired
    private MergeStaTaskImpl mergeStaTask;
    @Autowired
    private ImportFileLogDao importFileLogDao;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private ExcelWriterManager excelWriterManager;

    /**
     * 执行通用总任务创建配货清单
     */
    @Override
    public void createCreateByWh(Long whOuId, boolean isSingleTask) {
        log.debug("---" + whOuId + ":" + isSingleTask);
        AutoPlConfig apc = apcDao.getAotoPlConfig(whOuId);
        Date date = new Date();
        if (apc != null && apc.getStatus().equals(AutoPlStatus.NORMAL) && apc.getIsSingleTask() == isSingleTask) {
            mergeStaTask.mergeSta(whOuId);
            if (date.getTime() > apc.getNextExecuteTime().getTime()) {
                try {
                    autoPickingManager.updateAutoPickingNextExecuteTime(apc.getId());//
                    // 更新下次执行时间
                    autoGeneratePickingLilst(apc.getOuId().getId());
                } catch (Exception e) {
                    log.error("AutoPickingManagerProxyImpl.autoGeneratePickingLilst----error:ou_id:" + apc.getOuId().getId());
                    log.error("", e);
                }
            }
        }
    }

    // /**
    // * 单仓执行先处理合并订单，再创建配货清单(同物流)
    // */
    // public void autoGeneratePickingLilst(Long hwouid) {
    // // 通过仓库ID获取渠道组信息
    // List<BiChannelGroup> bcgList = bgDao.getBiChannelGroupByOuId(hwouid, new
    // BeanPropertyRowMapper<BiChannelGroup>(BiChannelGroup.class));
    // List<String> channelCode = new ArrayList<String>();// 封装渠道CODE
    // for (BiChannelGroup bcg : bcgList) {
    // // 通过渠道组ID获取绑定渠道信息
    // List<BiChannelRefCommand> bfrList = bgDao.getBiChannelRefByCgId(bcg.getId(), new
    // BeanPropertyRowMapper<BiChannelRefCommand>(BiChannelRefCommand.class));
    // for (BiChannelRefCommand brf : bfrList) {
    // BiChannel bc = bcDao.getByPrimaryKey(brf.getChannelId());
    // channelCode.add(bc.getCode());
    // }
    // }
    // AutoPlConfigCommand apc = apcDao.getAotoPlConfig(hwouid, new
    // BeanPropertyRowMapper<AutoPlConfigCommand>(AutoPlConfigCommand.class));
    // AutoPickingListRole aplr = aplrDao.getByPrimaryKey(apc.getAutoPlr());// 获取规则
    // // 获取规则明细
    // List<AutoPickingListRoleDetailCommand> aplrdList =
    // aplrDao.getAutoPickingListRoleDetial(aplr.getId(), new
    // BeanPropertyRowMapper<AutoPickingListRoleDetailCommand>(AutoPickingListRoleDetailCommand.class));
    // for (AutoPickingListRoleDetailCommand aplrList : aplrdList) {
    // PickingListCheckMode pcMode = null;
    // // 创建配货清单
    // // 判断多件单件
    // if (StockTransApplicationPickingType.valueOf(aplrList.getType()) ==
    // StockTransApplicationPickingType.SKU_SINGLE) {
    // // 单件
    // pcMode = PickingListCheckMode.PICKING_CHECK;
    // } else {
    // // 多件
    // pcMode = PickingListCheckMode.DEFAULE;
    // }
    // // 先找寻能配送一个批次的物流商
    // StockTransApplicationCommand staLpCode = apcDao.getAllStaAutoLpCode(channelCode, hwouid,
    // aplrList.getType(), aplrList.getSkuCategoryId(), new
    // BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    // if (staLpCode != null && staLpCode.getLpcode() != null) {
    // // 通过规则和物流商查出所有STA
    // String[] lpcodeList = staLpCode.getLpcode().split(",");
    // for (String lp : lpcodeList) {
    // List<StockTransApplicationCommand> staId =
    // apcDao.getAllAutoStaId(channelCode, hwouid, aplrList.getType(), aplrList.getSkuCategoryId(),
    // lp, aplrList.getSkuSizeId(), new
    // BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    // if (staId.size() >= aplrList.getMinOrder()) {
    // int count = staId.size();
    // // 判断作业单数量是否符合规则最小配货数量
    // List<Long> staLong = new ArrayList<Long>();
    // Log.error("最小:" + aplrList.getMinOrder() + " 最大:" + aplrList.getMaxOrder() + " SKU大小:" +
    // aplrList.getSkuSizeId() + " " + staId.size());
    // for (int i = 0; i < staId.size(); i++) {
    // staLong.add(staId.get(i).getId());
    // if (count >= aplrList.getMaxOrder()) {
    // // 总条目数>=最大配置数量
    // if (staLong.size() % aplrList.getMaxOrder() == 0) {
    // if (staLong.size() > 0) {
    // try {
    // createPickingListManagerProxy.createPickingListBySta(staLong, hwouid, null, pcMode, false);
    // } catch (Exception e) {
    // Log.error("", e);
    // }
    // count = count - staLong.size();// 总条目数递减
    // }
    // staLong = new ArrayList<Long>();
    // }
    // } else {
    // if (count < aplrList.getMaxOrder() && count > aplrList.getMinOrder()) {
    // // 总条目数在最大和最小配置数量区间内
    // if (staLong.size() % count == 0) {
    // if (staLong.size() > 0) {
    // try {
    // createPickingListManagerProxy.createPickingListBySta(staLong, hwouid, null, pcMode, false);
    // } catch (Exception e) {
    // Log.error("", e);
    // }
    // count = count - staLong.size();// 总条目数递减
    // }
    // staLong = new ArrayList<Long>();
    // }
    // } else {
    // if (count >= aplrList.getMinOrder()) {
    // // 总条目数>=最小配置数量
    // if (staLong.size() % aplrList.getMinOrder() == 0) {
    // if (staLong.size() > 0) {
    // try {
    // createPickingListManagerProxy.createPickingListBySta(staLong, hwouid, null, pcMode, false);
    // } catch (Exception e) {
    // Log.error("", e);
    // }
    // count = count - staLong.size();// 总条目数递减
    // }
    // staLong = new ArrayList<Long>();
    // }
    // }
    // }
    // }
    // }
    // }
    // }
    // }
    // }
    // }

    /**
     * 单仓执行先处理合并订单，再创建配货清单 (混物流)
     */
    public void autoGeneratePickingLilst(Long hwouid) {
        // 通过仓库ID获取渠道组信息
        List<BiChannelGroup> bcgList = bgDao.getBiChannelGroupByOuId(hwouid, new BeanPropertyRowMapper<BiChannelGroup>(BiChannelGroup.class));
        AutoPlConfig apc = apcDao.getAotoPlConfig(hwouid);
        AutoPickingListRole aplr = apc.getAutoPlr();// 获取规则
        // 获取规则明细
        List<AutoPickingListRoleDetailCommand> aplrdList = aplrDao.getAutoPickingListRoleDetial(aplr.getId(), new BeanPropertyRowMapper<AutoPickingListRoleDetailCommand>(AutoPickingListRoleDetailCommand.class));
        if (aplrdList == null || aplrdList.size() == 0) {
            return;// 仓库绑定的规则明细为空，该仓无法执行自动配货
        }
        for (BiChannelGroup bcg : bcgList) {
            List<String> channelCode = new ArrayList<String>();// 封装渠道CODE
            // 通过渠道组ID获取绑定渠道信息
            List<BiChannelRefCommand> bfrList = bgDao.getBiChannelRefByCgId(bcg.getId(), new BeanPropertyRowMapper<BiChannelRefCommand>(BiChannelRefCommand.class));
            if (bfrList == null || bfrList.size() == 0) {
                continue;// 当前渠道组明细为空，不执行当前渠道组
            }
            for (BiChannelRefCommand brf : bfrList) {
                BiChannel bc = bcDao.getByPrimaryKey(brf.getChannelId());
                channelCode.add(bc.getCode());
            }
            for (AutoPickingListRoleDetailCommand aplrList : aplrdList) {
                PickingListCheckMode pcMode = null;
                // 创建配货清单
                // 判断多件单件
                if (StockTransApplicationPickingType.valueOf(aplrList.getType()) == StockTransApplicationPickingType.SKU_SINGLE) {
                    // 单件
                    pcMode = PickingListCheckMode.PICKING_CHECK;
                } else {
                    // 多件
                    pcMode = PickingListCheckMode.DEFAULE;
                }

                List<SkuSizeConfig> sizeList = null;
                if (aplrList.getSkuSizeId() != null) {
                    sizeList = new ArrayList<SkuSizeConfig>();
                    sizeList.add(skuSizeConfigDao.getByPrimaryKey(aplrList.getSkuSizeId()));
                }
                if (aplrList.getIsTransAfter()) {// 混物流配货
                    List<String> lpListH = new ArrayList<String>();
                    // 查询可以混物流的物流商
                    List<Transportator> tList = autoPickingListRoleManager.findTransportatorList();
                    for (Transportator t : tList) {
                        lpListH.add(t.getExpCode());
                    }
                    autoCreatePickingList(channelCode, aplrList.getIsSn(), hwouid, aplrList.getType(), aplrList.getSkuCategoryId(), lpListH, sizeList, aplrList.getCity(), aplrList.getMaxOrder(), aplrList.getMinOrder(), pcMode, aplrList.getIsTransAfter());
                }
                List<String> lpList = new ArrayList<String>();
                // 获取规则对应物流商明细
                List<AutoPickingListRoleDetailTransportator> aprdtList =
                        aplrdtDao.getAutoPickingListRoleDetailTransportator(aplrList.getId(), new BeanPropertyRowMapper<AutoPickingListRoleDetailTransportator>(AutoPickingListRoleDetailTransportator.class));
                for (AutoPickingListRoleDetailTransportator aprd : aprdtList) {
                    Transportator t = transportatorDao.getByPrimaryKey(aprd.getTranId());
                    lpList.add(t.getExpCode());
                }
                for (String lpCode : lpList) {
                    List<String> newLpList = new ArrayList<String>();
                    newLpList.add(lpCode);
                    autoCreatePickingList(channelCode, aplrList.getIsSn(), hwouid, aplrList.getType(), aplrList.getSkuCategoryId(), newLpList, sizeList, aplrList.getCity(), aplrList.getMaxOrder(), aplrList.getMinOrder(), pcMode,
                            aplrList.getIsTransAfter());
                }
            }
        }
    }

    private void autoCreatePickingList(List<String> channelCode, Boolean isSn, Long ouId, Integer pickType, Long skuCategoryId, List<String> lpList, List<SkuSizeConfig> sizeList, String sendCity, Integer maxOrder, Integer minOrder,
            PickingListCheckMode pcMode, Boolean isTransAfter) {
        Integer hasSn = null;
        hasSn = isSn == null ? null : (isSn ? 1 : 0);
        List<String> cityList = getCityList(sendCity);
        if (!StringUtils.hasText(sendCity)) {
            sendCity = null;
        } else {
            String[] s = sendCity.split(",");
            if (s.length > 1) {
                sendCity = null;
            }
        }
        List<StockTransApplicationCommand> staId =
                apcDao.getAllAutoStaIdByCount(channelCode, hasSn, ouId, pickType, skuCategoryId, lpList, sizeList, sendCity, STA_COUNT, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        if (staId.size() >= minOrder) {
            int count = staId.size();
            // 判断作业单数量是否符合规则最小配货数量
            List<Long> staLong = new ArrayList<Long>();
            for (int i = 0; i < staId.size(); i++) {
                staLong.add(staId.get(i).getId());
                if (count >= maxOrder) {
                    // 总条目数>=最大配置数量
                    if (staLong.size() % maxOrder == 0) {
                        try {
                            createPickingListManagerProxy.createPickingListBySta(null, staLong, ouId, null, pcMode, false, hasSn, skuCategoryId, cityList, isTransAfter, sizeList, null, null, null, false, false, null, false);
                        } catch (Exception e) {
                            log.error("", e);
                        }
                        count = count - staLong.size();// 总条目数递减
                        staLong = new ArrayList<Long>();
                    }
                } else {
                    if (count >= minOrder) {
                        // 总条目数在最大和最小配置数量区间内
                        if (staLong.size() % count == 0) {
                            try {
                                createPickingListManagerProxy.createPickingListBySta(null, staLong, ouId, null, pcMode, false, hasSn, skuCategoryId, cityList, isTransAfter, sizeList, null, null, null, false, false, null, false);
                            } catch (Exception e) {
                                log.error("", e);
                            }
                            staLong = new ArrayList<Long>();
                        }
                    } else {
                        break;
                    }
                }
            }
        }

    }

    private List<String> getCityList(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (str != null && StringUtils.hasText(str)) {
            for (String s : str.split(",")) {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 基于渠道组创建配货清单,根据规则顺序查询单据依次创建配货清单
     */
    @Override
    public void autoGeneratePickingLilst(Long hwouid, Long channelGroupId) {

    }


}
