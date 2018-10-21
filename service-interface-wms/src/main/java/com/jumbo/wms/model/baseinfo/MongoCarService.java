package com.jumbo.wms.model.baseinfo;

import java.math.BigDecimal;
import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class MongoCarService extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1253457794176869792L;

    private Long id;

    private String licensePlateNumber;// 车牌号码

    private BigDecimal vehicleVolume;// 体积

    private Date useTime;// 创建时间



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public BigDecimal getVehicleVolume() {
        return vehicleVolume;
    }

    public void setVehicleVolume(BigDecimal vehicleVolume) {
        this.vehicleVolume = vehicleVolume;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }


}
