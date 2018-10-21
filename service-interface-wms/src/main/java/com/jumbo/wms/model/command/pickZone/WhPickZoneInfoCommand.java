package com.jumbo.wms.model.command.pickZone;

import java.io.Serializable;

import com.jumbo.wms.model.pickZone.WhPickZoon;


/**
 * @author gianni.zhang
 *
 * 2015年7月13日 上午10:27:03
 */
public class WhPickZoneInfoCommand extends WhPickZoon implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 722359642666414318L;
    
    private String location;
    
    private String district;
    
    private Integer sort;
    
    private Long locationId;
    
    private String createUser;

    private String whZoonCode;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getWhZoonCode() {
        return whZoonCode;
    }

    public void setWhZoonCode(String whZoonCode) {
        this.whZoonCode = whZoonCode;
    }


    
}
