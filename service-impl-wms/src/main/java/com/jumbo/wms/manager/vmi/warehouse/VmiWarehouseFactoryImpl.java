package com.jumbo.wms.manager.vmi.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Warehouse;

@Transactional
@Component
public class VmiWarehouseFactoryImpl extends BaseManagerImpl implements VmiWarehouseFactory {

    private static final long serialVersionUID = -3134142368375105315L;

    private VmiWarehouseInterface idsWh;
    private VmiWarehouseInterface biaoganWh;
    private VmiWarehouseInterface itochuWh;
    private VmiWarehouseInterface ckWh;
    private VmiWarehouseInterface wlbWh;
    private VmiWarehouseInterface itochuUAWh;
    private VmiWarehouseInterface idsUAWh;
    private VmiWarehouseInterface gqsscmWh;
    private VmiWarehouseInterface follieWh;
    private VmiWarehouseInterface wmfWh;
    private VmiWarehouseInterface sfWh;
    private VmiWarehouseInterface sfTwWh;
    private VmiWarehouseInterface idsNikeWh;
    private VmiWarehouseInterface idsaeoWh;
    private VmiWarehouseInterface godivaWh;
    private VmiWarehouseInterface gymboreeWh;
    private VmiWarehouseInterface idsafWh;
    private VmiWarehouseInterface yhWh;
    private VmiWarehouseInterface guessWh;
    private VmiWarehouseInterface idsNBAUA;
    private VmiWarehouseInterface idsNBJ01;
    private VmiWarehouseInterface idsHavi;
    private VmiWarehouseInterface smWh; // 微软神码
    private VmiWarehouseInterface newLookJDWh;
    private VmiWarehouseInterface idsNikeTmWh;// nike001
    private VmiWarehouseInterface hdWh;
    private VmiWarehouseInterface cjWh;
    private VmiWarehouseInterface sqWh;
    private VmiWarehouseInterface guessRetailWh;
    private VmiWarehouseInterface spdzNBAWh;
    private VmiWarehouseInterface IdsVSWh;
    private VmiWarehouseInterface idsNikeTmWh2;// nike002
    private VmiWarehouseInterface idsNikeTmWh3;// nike003
    private VmiWarehouseInterface ztWh;// ZT
    private VmiWarehouseInterface isCancelTohub;
    private VmiWarehouseInterface idsConverse;

    @Autowired
    private WarehouseDao warehouseDao;


    public VmiWarehouseInterface getZtWh() {
        return ztWh;
    }

    public void setZtWh(VmiWarehouseInterface ztWh) {
        this.ztWh = ztWh;
    }

    public VmiWarehouseInterface getIdsNikeTmWh2() {
        return idsNikeTmWh2;
    }

    public void setIdsNikeTmWh2(VmiWarehouseInterface idsNikeTmWh2) {
        this.idsNikeTmWh2 = idsNikeTmWh2;
    }

    public VmiWarehouseInterface getIdsNikeTmWh3() {
        return idsNikeTmWh3;
    }

    public void setIdsNikeTmWh3(VmiWarehouseInterface idsNikeTmWh3) {
        this.idsNikeTmWh3 = idsNikeTmWh3;
    }

    public VmiWarehouseInterface getIdsVSWh() {
        return IdsVSWh;
    }

    public void setIdsVSWh(VmiWarehouseInterface idsVSWh) {
        IdsVSWh = idsVSWh;
    }

    public VmiWarehouseInterface getGuessRetailWh() {
        return guessRetailWh;
    }

    public void setGuessRetailWh(VmiWarehouseInterface guessRetailWh) {
        this.guessRetailWh = guessRetailWh;
    }

    public VmiWarehouseInterface getSqWh() {
        return sqWh;
    }

    public void setSqWh(VmiWarehouseInterface sqWh) {
        this.sqWh = sqWh;
    }

    public VmiWarehouseInterface getHdWh() {
        return hdWh;
    }

    public void setHdWh(VmiWarehouseInterface hdWh) {
        this.hdWh = hdWh;
    }

    public VmiWarehouseInterface getIdsNikeTmWh() {
        return idsNikeTmWh;
    }

    public void setIdsNikeTmWh(VmiWarehouseInterface idsNikeTmWh) {
        this.idsNikeTmWh = idsNikeTmWh;
    }

    public VmiWarehouseInterface getNewLookJDWh() {
        return newLookJDWh;
    }

    public void setNewLookJDWh(VmiWarehouseInterface newLookJDWh) {
        this.newLookJDWh = newLookJDWh;
    }

    public VmiWarehouseInterface getIdsNBJ01() {
        return idsNBJ01;
    }

    public void setIdsNBJ01(VmiWarehouseInterface idsNBJ01) {
        this.idsNBJ01 = idsNBJ01;
    }

    public VmiWarehouseInterface getNewLook() {
        return newLook;
    }

    public void setNewLook(VmiWarehouseInterface newLook) {
        this.newLook = newLook;
    }

    private VmiWarehouseInterface newLook;

    public VmiWarehouseInterface getItochuUAWh() {
        return itochuUAWh;
    }

    public void setItochuUAWh(VmiWarehouseInterface itochuUAWh) {
        this.itochuUAWh = itochuUAWh;
    }

    public VmiWarehouseInterface getItochuWh() {
        return itochuWh;
    }

    public void setItochuWh(VmiWarehouseInterface itochuWh) {
        this.itochuWh = itochuWh;
    }

    public VmiWarehouseInterface getSfTwWh() {
        return sfTwWh;
    }

    public void setSfTwWh(VmiWarehouseInterface sfTwWh) {
        this.sfTwWh = sfTwWh;
    }

    public VmiWarehouseInterface getCjWh() {
        return cjWh;
    }

    public void setCjWh(VmiWarehouseInterface cjWh) {
        this.cjWh = cjWh;
    }

    public VmiWarehouseInterface getSpdzNBAWh() {
        return spdzNBAWh;
    }

    public void setSpdzNBAWh(VmiWarehouseInterface spdzNBAWh) {
        this.spdzNBAWh = spdzNBAWh;
    }

    public VmiWarehouseInterface getVmiWarehouse(String sourceCode) {
        if (StringUtils.hasText(sourceCode)) {
            Warehouse wh = warehouseDao.findWarehouseByVmiSource(sourceCode);
            if (sourceCode.equals(idsWh.getSourceCode())) {
                return idsWh;
            } else if (sourceCode.equals(IdsVSWh.getSourceCode())) {
                return IdsVSWh;
            } else if (sourceCode.equals(idsNikeTmWh2.getSourceCode())) {
                return idsNikeTmWh2;
            } else if (sourceCode.equals(idsNikeTmWh3.getSourceCode())) {
                return idsNikeTmWh3;
            } else if (sourceCode.equals(biaoganWh.getSourceCode())) {
                return biaoganWh;
            } else if (sourceCode.equals(itochuWh.getSourceCode())) {
                return itochuWh;
            } else if (sourceCode.equals(ckWh.getSourceCode())) {
                return ckWh;
            } else if (sourceCode.equals(wlbWh.getSourceCode())) {
                return wlbWh;
            } else if (sourceCode.equals(itochuUAWh.getSourceCode())) {
                return itochuUAWh;
            } else if (sourceCode.equals(idsUAWh.getSourceCode())) {
                return idsUAWh;
            } else if (sourceCode.equals(gqsscmWh.getSourceCode())) {
                return gqsscmWh;
            } else if (sourceCode.equals(follieWh.getSourceCode())) {
                return follieWh;
            } else if (sourceCode.equals(wmfWh.getSourceCode())) {
                return wmfWh;
            } else if (sourceCode.equals(sfWh.getSourceCode())) {
                return sfWh;
            } else if (sourceCode.equals(sfTwWh.getSourceCode())) {
                return sfTwWh;
            } else if (sourceCode.equals(idsNikeWh.getSourceCode())) {
                return idsNikeWh;
            } else if (sourceCode.equals(idsaeoWh.getSourceCode())) {
                return idsaeoWh;
            } else if (sourceCode.equals(godivaWh.getSourceCode())) {
                return godivaWh;
            } else if (sourceCode.equals(gymboreeWh.getSourceCode())) {
                return gymboreeWh;
            } else if (sourceCode.equals(idsafWh.getSourceCode())) {
                return idsafWh;
            } else if (sourceCode.equals(yhWh.getSourceCode())) {
                return yhWh;
            } else if (sourceCode.equals(newLook.getSourceCode())) {
                return newLook;
            } else if (sourceCode.equals(guessWh.getSourceCode())) {
                return guessWh;
            } else if (sourceCode.equals(idsNBAUA.getSourceCode())) {
                return idsNBAUA;
            } else if (sourceCode.equals(idsNBJ01.getSourceCode())) {
                return idsNBJ01;
            } else if (sourceCode.equals(idsHavi.getSourceCode())) {
                return idsHavi;
            } else if (sourceCode.equals(smWh.getSourceCode())) {
                return smWh;
            } else if (sourceCode.equals(newLookJDWh.getSourceCode())) {
                return newLookJDWh;
            } else if (sourceCode.equals(idsNikeTmWh.getSourceCode())) {
                return idsNikeTmWh;
            } else if (sourceCode.equals(hdWh.getSourceCode())) {
                return hdWh;
            } else if (sourceCode.equals(cjWh.getSourceCode())) {
                return cjWh;
            } else if (sourceCode.equals(sqWh.getSourceCode())) {
                return sqWh;
            } else if (sourceCode.equals(guessRetailWh.getSourceCode())) {
                return guessRetailWh;
            } else if (sourceCode.equals(spdzNBAWh.getSourceCode())) {
                return spdzNBAWh;
            } else if (sourceCode.equals(ztWh.getSourceCode())) {
                return ztWh;
            }else if(null!=wh&&null!=wh.getIsCancelTohub()&&wh.getIsCancelTohub()){
                return isCancelTohub;
            } else if (sourceCode.equals(idsConverse.getSourceCode())) {
                return idsConverse;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public VmiWarehouseInterface getIdsNBAUA() {
        return idsNBAUA;
    }

    public void setIdsNBAUA(VmiWarehouseInterface idsNBAUA) {
        this.idsNBAUA = idsNBAUA;
    }

    public VmiWarehouseInterface getGuessWh() {
        return guessWh;
    }

    public void setGuessWh(VmiWarehouseInterface guessWh) {
        this.guessWh = guessWh;
    }

    public VmiWarehouseInterface getIdsWh() {
        return idsWh;
    }

    public void setIdsWh(VmiWarehouseInterface idsWh) {
        this.idsWh = idsWh;
    }

    public VmiWarehouseInterface getBiaoganWh() {
        return biaoganWh;
    }

    public void setBiaoganWh(VmiWarehouseInterface biaoganWh) {
        this.biaoganWh = biaoganWh;
    }

    public VmiWarehouseInterface getCkWh() {
        return ckWh;
    }

    public void setCkWh(VmiWarehouseInterface ckWh) {
        this.ckWh = ckWh;
    }

    public VmiWarehouseInterface getWlbWh() {
        return wlbWh;
    }

    public void setWlbWh(VmiWarehouseInterface wlbWh) {
        this.wlbWh = wlbWh;
    }

    public VmiWarehouseInterface getGqsscmWh() {
        return gqsscmWh;
    }

    public void setGqsscmWh(VmiWarehouseInterface gqsscmWh) {
        this.gqsscmWh = gqsscmWh;
    }

    public VmiWarehouseInterface getFollieWh() {
        return follieWh;
    }

    public void setFollieWh(VmiWarehouseInterface follieWh) {
        this.follieWh = follieWh;
    }

    public VmiWarehouseInterface getWmfWh() {
        return wmfWh;
    }

    public VmiWarehouseInterface getIdsNikeWh() {
        return idsNikeWh;
    }

    public void setIdsNikeWh(VmiWarehouseInterface idsNikeWh) {
        this.idsNikeWh = idsNikeWh;
    }

    public void setWmfWh(VmiWarehouseInterface wmfWh) {
        this.wmfWh = wmfWh;
    }

    public VmiWarehouseInterface getIdsaeoWh() {
        return idsaeoWh;
    }

    public void setIdsaeoWh(VmiWarehouseInterface idsaeoWh) {
        this.idsaeoWh = idsaeoWh;
    }

    public VmiWarehouseInterface getSfWh() {
        return sfWh;
    }

    public void setSfWh(VmiWarehouseInterface sfWh) {
        this.sfWh = sfWh;
    }

    public VmiWarehouseInterface getGodivaWh() {
        return godivaWh;
    }

    public void setGodivaWh(VmiWarehouseInterface godivaWh) {
        this.godivaWh = godivaWh;
    }

    public VmiWarehouseInterface getGymboreeWh() {
        return gymboreeWh;
    }

    public void setGymboreeWh(VmiWarehouseInterface gymboreeWh) {
        this.gymboreeWh = gymboreeWh;
    }

    public VmiWarehouseInterface getIdsafWh() {
        return idsafWh;
    }

    public void setIdsafWh(VmiWarehouseInterface idsafWh) {
        this.idsafWh = idsafWh;
    }

    public VmiWarehouseInterface getYhWh() {
        return yhWh;
    }

    public void setYhWh(VmiWarehouseInterface yhWh) {
        this.yhWh = yhWh;
    }

    public VmiWarehouseInterface getIdsUAWh() {
        return idsUAWh;
    }

    public void setIdsUAWh(VmiWarehouseInterface idsUAWh) {
        this.idsUAWh = idsUAWh;
    }

    public VmiWarehouseInterface getIdsHavi() {
        return idsHavi;
    }

    public void setIdsHavi(VmiWarehouseInterface idsHavi) {
        this.idsHavi = idsHavi;
    }

    public VmiWarehouseInterface getSmWh() {
        return smWh;
    }

    public void setSmWh(VmiWarehouseInterface smWh) {
        this.smWh = smWh;
    }

    public VmiWarehouseInterface getIsCancelTohub() {
        return isCancelTohub;
    }

    public void setIsCancelTohub(VmiWarehouseInterface isCancelTohub) {
        this.isCancelTohub = isCancelTohub;
    }

    public VmiWarehouseInterface getIdsConverse() {
        return idsConverse;
    }

    public void setIdsConverse(VmiWarehouseInterface idsConverse) {
        this.idsConverse = idsConverse;
    }

    
}
