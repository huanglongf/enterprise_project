package com.jumbo.wms.model.baseinfo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultLifeCycleStatus;

/**
 * 
 * 物流商
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_MA_TRANSPORTATOR")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class Transportator extends BaseModel {

    public static final String EMS = "EMS";
    public static final String EMS_COD = "EMSCOD";
    public static final String SF = "SF";
    public static final String SFHK = "SFHK";
    public static final String SFGJ = "SFGJ";
    public static final String SFCOD = "SFCOD";
    public static final String SFDSTH = "SFDSTH";
    public static final String STO = "STO";
    public static final String ZTO = "ZTO";
    public static final String ZTOOD = "ZTOOD";
    public static final String JD = "JD";
    public static final String JDCOD = "JDCOD";
    public static final String YTO = "YTO";
    public static final String YTOCOD = "YTOCOD";
    public static final String DHL = "DHL";
    public static final String ZJS = "ZJS";
    public static final String DFJY = "DEJY";
    public static final String YUNDA = "YUNDA";
    public static final String HTKY = "HTKY";
    public static final String JM = "JM";
    public static final String OTHTRAN = "OTHER";
    public static final String OTHER = "OTHER";
    public static final String TTKDEX = "TTKDEX";
    public static final String GLRS = "GLRS";
    public static final String NEDA = "NEDA";
    public static final String STARS_COD = "STARS_COD";
    public static final String STARS = "STARS";
    public static final String YD_QY = "YD_QY";
    public static final String EMS_EYB = "EMS_EYB";
    public static final String EMS_GJ = "EMS_GJ";
    public static final String HLWL = "HLWL";
    public static final String YTO_QY = "YTO_QY";
    public static final String JATC = "JATC";
    public static final String SBWL = "SBWL";
    public static final String XR = "XR";
    public static final String GG = "GG";
    public static final String ML = "ML";
    public static final String ESB = "ESB";
    public static final String MT = "MT";
    public static final String LB = "LB";
    public static final String CRE = "CRE";
    public static final String EMSLY = "EMSLY";
    public static final String STLY = "STLY";
    public static final String KERRY_A = "KERRY_A";
    public static final String KERRY = "KERRY";
    public static final String SFLY = "SFLY";
    public static final String UC = "UC";
    public static final String GTO = "GTO";
    public static final String YAMATO = "YAMATO";
    public static final String YCT = "YCT";
    public static final String TJCRE = "TJCRE";
    public static final String WX = "WX";
    public static final String CXC = "CXC";
    public static final String RFD = "RF";
    public static final String MDM = "MDM";
    public static final String CNP = "CNP";
    public static final String CNJH = "CNJH";
    public static final String ZJB = "ZJB";
    public static final String CS100 = "CS100";
    public static final String SFMACAO = "SFMACAO";

    public static final String SF_LOGO_IMAGE = "print_img/top.jpg";
    public static final String STO_LOGO_IMAGE = "print_img/STOLOG.jpg";
    public static final String ZTO_LOGO_IMAGE = "print_img/ztologo.jpg";
    /**
	 * 
	 */
    private static final long serialVersionUID = -7812676025062855078L;

    /**
     * PK
     */
    private Long id;

    /**
     * 生命周期
     */
    private DefaultLifeCycleStatus lifeCycleStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * Version
     */
    private Date version;

    /**
     * 物流商编码
     */
    private String code;

    /**
     * 物流商名称
     */
    private String name;

    /**
     * 物流商全称
     */
    private String fullName;

    /**
     * K3编码
     */
    private String k3Code;

    /**
     * 内部平台对接编码，全局唯一
     */
    private String expCode;

    /**
     * 外部平台对接编码
     */
    private String platformCode;

    /**
     * 是否支持COD
     */
    private Boolean isSupportCod;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * 电子面单模板
     */
    private String jasperOnLine;
    /**
     * 普通电子面单
     */
    private String jasperNormal;

    /**
     * 是否后置
     */
    private Boolean isTransAfter;

    /**
     * 是否分区域
     */
    private Boolean isRegion;

    /**
     * 货到付款最大金额
     */
    private BigDecimal codMaxAmt;
    /**
     * 
     */
    private Boolean isUseSf;

    @Column(name = "is_Use_Sf")
    public Boolean getIsUseSf() {
        return isUseSf;
    }

    public void setIsUseSf(Boolean isUseSf) {
        this.isUseSf = isUseSf;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_AREA", sequenceName = "S_T_MA_TRANSPORTATOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AREA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LIFE_CYCLE_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultLifeCycleStatus")})
    public DefaultLifeCycleStatus getLifeCycleStatus() {
        return lifeCycleStatus;
    }

    public void setLifeCycleStatus(DefaultLifeCycleStatus lifeCycleStatus) {
        this.lifeCycleStatus = lifeCycleStatus;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "IS_SUPPORT_COD")
    public Boolean getIsSupportCod() {
        return isSupportCod;
    }

    public void setIsSupportCod(Boolean isSupportCod) {
        this.isSupportCod = isSupportCod;
    }

    @Version
    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "CODE", length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "FULL_NAME", length = 255)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "K3_CODE", length = 20)
    public String getK3Code() {
        return k3Code;
    }

    public void setK3Code(String k3Code) {
        this.k3Code = k3Code;
    }

    @Column(name = "EXP_CODE", length = 20)
    public String getExpCode() {
        return expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
    }

    @Column(name = "PLATFORM_CODE", length = 20)
    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "JASPER_ONLINE", length = 200)
    public String getJasperOnLine() {
        return jasperOnLine;
    }

    public void setJasperOnLine(String jasperOnLine) {
        this.jasperOnLine = jasperOnLine;
    }

    @Column(name = "JASPER_NORMAL", length = 200)
    public String getJasperNormal() {
        return jasperNormal;
    }

    public void setJasperNormal(String jasperNormal) {
        this.jasperNormal = jasperNormal;
    }

    @Column(name = "IS_TRANS_AFTER")
    public Boolean getIsTransAfter() {
        return isTransAfter;
    }

    public void setIsTransAfter(Boolean isTransAfter) {
        this.isTransAfter = isTransAfter;
    }

    @Column(name = "IS_REGION")
    public Boolean getIsRegion() {
        return isRegion;
    }

    public void setIsRegion(Boolean isRegion) {
        this.isRegion = isRegion;
    }

    @Column(name = "COD_MAX_AMT")
    public BigDecimal getCodMaxAmt() {
        return codMaxAmt;
    }

    public void setCodMaxAmt(BigDecimal codMaxAmt) {
        this.codMaxAmt = codMaxAmt;
    }


}
