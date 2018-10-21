package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.util.StringUtil;
import com.jumbo.wms.model.jasperReport.OutBoundPackageInfoLinesObj;
import com.jumbo.wms.model.jasperReport.OutBoundPackageInfoObj;

public class OutBoundPackageInfoObjRowMapper extends BaseRowMapper<Map<Long, OutBoundPackageInfoObj>> {

    private Map<Long, OutBoundPackageInfoObj> map = new LinkedHashMap<Long, OutBoundPackageInfoObj>();

    @Override
    public Map<Long, OutBoundPackageInfoObj> mapRow(ResultSet rs, int rowNum) throws SQLException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        Long cId = getResultFromRs(rs, "ID", Long.class);
        OutBoundPackageInfoObj opo = map.get(cId);
        if (opo == null) {
            opo = new OutBoundPackageInfoObj();
            String owner = getResultFromRs(rs, "owner", String.class);
            opo.setOwner(owner);
            String staCode = getResultFromRs(rs, "staCode", String.class);
            opo.setStaCode(staCode);
            String slipCode = getResultFromRs(rs, "slipCode", String.class);
            opo.setSlipCode(slipCode);
            String printTime = df.format(new Date()).toString();// 系统当前时间
            opo.setPrintTime(printTime);
            String seQno = getResultFromRs(rs, "seqNo", String.class);// 箱号
            opo.setSeQno(seQno);
            String code = getResultFromRs(rs, "code", String.class);// 箱号编码
            opo.setCode(code);
            double completeQty = Double.parseDouble(getResultFromRs(rs, "completeQty", String.class));// 总执行量
            opo.setCompleteQty(completeQty);
            String weight = getResultFromRs(rs, "weight", String.class);// 装箱重量
            opo.setWeight(StringUtil.isEmpty(weight) ? "" : weight);
            List<OutBoundPackageInfoLinesObj> opoList = new ArrayList<OutBoundPackageInfoLinesObj>();
            opoList.add(getPlObj(rs, opoList.size()));
            opo.setLines(opoList);
            opo.setDetailSize(opoList.size());
            opo.setDetailSize(opo.getLines().size());
            map.put(cId, opo);
        } else {
            opo.getLines().add(getPlObj(rs, opo.getLines().size()));
            opo.setDetailSize(opo.getLines().size());
        }
        return map;
    }

    private OutBoundPackageInfoLinesObj getPlObj(ResultSet rs, int size) throws SQLException {
        OutBoundPackageInfoLinesObj opoList = new OutBoundPackageInfoLinesObj();
        String seQno = getResultFromRs(rs, "seqNo", String.class);// 箱号
        opoList.setSeQno(seQno);
        String code = getResultFromRs(rs, "code", String.class);// 箱号编码
        opoList.setCode(code);
        String barCode = getResultFromRs(rs, "barCode", String.class);// 商品条形码
        opoList.setBarCode(barCode);
        String supplierCode = getResultFromRs(rs, "supplierCode", String.class);// 货号属性
        opoList.setSupplierCode(supplierCode);
        double qty = Double.parseDouble(getResultFromRs(rs, "qty", String.class));// 单个执行量
        opoList.setQty(qty);
        String skuSize=getResultFromRs(rs, "skuSize", String.class);// 尺码
        opoList.setSkuSize(skuSize);
        opoList.setOrdinal(size + 1);
        return opoList;
    }
}
