package com.jumbo.wms.model.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

/**
 * 
 * 
 * 项目名称：scm-wms 类名称：StvCheckImg 类描述：核对拍照后保存STV对应图片路劲 创建人：bin.hu 创建时间：2014-8-19
 * 
 * @version
 * 
 */
@Entity
@Table(name = "T_WH_STV_CHECK_IMG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StvCheckImg extends BaseModel {


    private static final long serialVersionUID = -8972263416273660940L;

    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 访问路径
     */
    private String fileUrl;

    /**
     * 外键stv_id
     */
    private StockTransVoucher stv;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CMPSHOP", sequenceName = "S_T_WH_STV_CHECK_IMG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CMPSHOP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREAT_TIME")
    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Column(name = "FILE_URL")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STV_ID")
    @Index(name = "FK_STV_CHECK_STVID")
    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }
}
