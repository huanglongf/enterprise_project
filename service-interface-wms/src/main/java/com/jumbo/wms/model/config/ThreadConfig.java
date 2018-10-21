package com.jumbo.wms.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 线程池配置
 * 
 * @author jingkai
 *
 */
@Entity
@Table(name = "T_SYS_THREAD_CONFIG")
public class ThreadConfig extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5830281446469706130L;

    public static final String SYS_KEY_SERVICE = "SERVICE";
    public static final String SYS_KEY_DEAMON = "DEAMON";
    public static final String SYS_KEY_WEB = "WEB";

    /**
     * ID
     */
    private Long id;

    /**
     * 线程池编码
     */
    private String threadCode;
    /**
     * 线程池大小
     */
    private Integer threadCount;

    /**
     * 系统编码
     */
    private String sysKey;
    /**
     * 备注
     */
    private String memo;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SCC", sequenceName = "S_T_SYS_THREAD_CONFIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCC")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "THREAD_CODE", length = 50)
    public String getThreadCode() {
        return threadCode;
    }

    public void setThreadCode(String threadCode) {
        this.threadCode = threadCode;
    }

    @Column(name = "THREAD_COUNT")
    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    @Column(name = "MEMO", length = 500)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "SYS_KEY", length = 30)
    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }
}
