package com.jumbo.web.action.baseinfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.model.baseinfo.LicensePlate;
import com.jumbo.wms.model.baseinfo.VehicleStandard;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

public class CarServiceAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -6653252596310084184L;
    @Autowired
    private BaseinfoManager baseinfoManager;

    private VehicleStandard vehicleStandard;

    private BigDecimal vehicleVolume1;

    private BigDecimal vehicleVolume2;

    private LicensePlate licensePlate;

    private String standardCode;

    private String vehicleCode;

    private String licensePlateNumber;

    private String vehicleStandard2;

    private BigDecimal vehicleVolume3;

    private BigDecimal vehicleVolume4;

    private Date useTime;

    private int status;

    private Long id;


    public String carService() {
        return SUCCESS;
    }

    public String findVehicleStandardList() {
        setTableConfig();
        Pagination<VehicleStandard> list = baseinfoManager.findVehicleStandardList(tableConfig.getStart(), tableConfig.getPageSize(), standardCode, vehicleVolume1, vehicleVolume2, tableConfig.getSorts());
        List<VehicleStandard> list1 = new ArrayList<VehicleStandard>();
        for (VehicleStandard vehicleStandard : list.getItems()) {
            list1.add(vehicleStandard);
        }
        list.setItems(list1);
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String findLicensePlateList() {
        setTableConfig();
        Pagination<LicensePlate> list = baseinfoManager.findLicensePlateList(tableConfig.getStart(), tableConfig.getPageSize(), vehicleCode, licensePlateNumber, vehicleStandard2, vehicleVolume3, vehicleVolume4, useTime, tableConfig.getSorts());
        List<LicensePlate> list1 = new ArrayList<LicensePlate>();
        for (LicensePlate licensePlate : list.getItems()) {
            list1.add(licensePlate);
        }
        list.setItems(list1);
        request.put(JSON, toJson(list));
        return JSON;
    }


    public String saveOrUpdateVehicleVolume() throws JSONException {
        JSONObject ob = new JSONObject();
        String result = null;
        try {
            result = baseinfoManager.updateVehicleStandard(vehicleStandard);
            if ("".equals(result)) {
                ob.put("msg", "success");
            } else {
                ob.put("msg", result);
            }
        } catch (Exception e) {
            ob.put("msg", "格式错误");
        }
        request.put(JSON, ob);
        return JSON;
    }

    public String saveOrUpdateLicensePlate() throws JSONException {
        JSONObject ob = new JSONObject();
        String result = null;
        try {
            result = baseinfoManager.updateLicensePlate(licensePlate, userDetails.getCurrentOu().getId());
            if ("".equals(result)) {
                ob.put("msg", "success");
            } else {
                ob.put("msg", result);
            }
        } catch (Exception e) {
            ob.put("msg", "保存失败");
        }
        request.put(JSON, ob);
        return JSON;
    }

    public String deleteVehicleVolumeById() {
        JSONObject ob = new JSONObject();
        try {
            baseinfoManager.deleteVehicleStandard(id);
            ob.put("msg", "success");
        } catch (Exception e) {
            log.error("deleteVehicleVolume is error :" + e);
        }
        request.put(JSON, ob);
        return JSON;
    }

    public String deleteLicensePlateById() {
        JSONObject ob = new JSONObject();
        try {
            baseinfoManager.deleteLicensePlateById(id);
            ob.put("msg", "success");
        } catch (Exception e) {
            log.error("deleteVehicleVolume is error :" + e);
        }
        request.put(JSON, ob);
        return JSON;
    }

    public String getStandardCode() {
        return standardCode;
    }


    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }


    public BigDecimal getVehicleVolume1() {
        return vehicleVolume1;
    }


    public void setVehicleVolume1(BigDecimal vehicleVolume1) {
        this.vehicleVolume1 = vehicleVolume1;
    }


    public BigDecimal getVehicleVolume2() {
        return vehicleVolume2;
    }


    public void setVehicleVolume2(BigDecimal vehicleVolume2) {
        this.vehicleVolume2 = vehicleVolume2;
    }

    public BigDecimal getVehicleVolume3() {
        return vehicleVolume3;
    }

    public void setVehicleVolume3(BigDecimal vehicleVolume3) {
        this.vehicleVolume3 = vehicleVolume3;
    }

    public BigDecimal getVehicleVolume4() {
        return vehicleVolume4;
    }

    public void setVehicleVolume4(BigDecimal vehicleVolume4) {
        this.vehicleVolume4 = vehicleVolume4;
    }

    public VehicleStandard getVehicleStandard() {
        return vehicleStandard;
    }

    public void setVehicleStandard(VehicleStandard vehicleStandard) {
        this.vehicleStandard = vehicleStandard;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(LicensePlate licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getVehicleStandard2() {
        return vehicleStandard2;
    }

    public void setVehicleStandard2(String vehicleStandard2) {
        this.vehicleStandard2 = vehicleStandard2;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
