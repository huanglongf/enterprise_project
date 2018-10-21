package com.jumbo.wms.model.vmi.ids;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "IDS_SERVER_INFORMATION")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class IdsServerInformation extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5671690591958375572L;


    /**
     * ID
     */
    private Long id;


    private String serverUrl;

    private String source;

    /**
     * 标记反馈接口所属品牌
     */
    private String vmiSource;

    private String key;

    private String Sign;

    private String bzKey;

    private String bzSign;

    private String storerKey;

    private String facility;

    private String facility2;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_IDS_SERVER_INFORMATION", sequenceName = "S_IDS_SERVER_INFORMATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IDS_SERVER_INFORMATION")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SERVER_URL", length = 50)
    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Column(name = "VMI_SOURCE", length = 50)
    public String getVmiSource() {
        return vmiSource;
    }

    public void setVmiSource(String vmiSource) {
        this.vmiSource = vmiSource;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "KEY", length = 20)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "SIGN", length = 100)
    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    @Column(name = "BZ_KEY", length = 20)
    public String getBzKey() {
        return bzKey;
    }

    public void setBzKey(String bzKey) {
        this.bzKey = bzKey;
    }

    @Column(name = "BZ_SIGN", length = 100)
    public String getBzSign() {
        return bzSign;
    }

    public void setBzSign(String bzSign) {
        this.bzSign = bzSign;
    }

    @Column(name = "STORER_KEY", length = 100)
    public String getStorerKey() {
        return storerKey;
    }

    public void setStorerKey(String storerKey) {
        this.storerKey = storerKey;
    }

    @Column(name = "FACILITY", length = 100)
    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    @Column(name = "FACILITY2", length = 100)
    public String getFacility2() {
        return facility2;
    }

    public void setFacility2(String facility2) {
        this.facility2 = facility2;
    }


}
