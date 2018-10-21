package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_EX_CONFIRM_ORDER_QUEUE")
public class ExpressConfirmOrderQueue extends BaseModel {

	private static final long serialVersionUID = 267127638688452918L;
	
	private Long id;            // PK
    private Date createTime;    // 创建时间
    private Long exeCount;      // 执行次数
    private String transNo;     // 运单号
    private String staCode;     // 作业单号
    private String weight;      // 重量
    private String lpCode;		// 物流商编号
    private String height;      // 高度
    private String length;      // 长度
    private String width;      // 宽
    private String volume;      // 体积
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_EXPRESSCQQ", sequenceName = "S_EX_CONFIRM_ORDER_QUEUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXPRESSCQQ")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="create_time")
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Column(name="exe_count")
    public Long getExeCount() {
        return exeCount;
    }
    public void setExeCount(Long exeCount) {
        this.exeCount = exeCount;
    }
    @Column(name="trans_no")
    public String getTransNo() {
        return transNo;
    }
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }
    @Column(name="sta_code")
    public String getStaCode() {
        return staCode;
    }
    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }
    @Column(name="weight")
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    @Column(name="lpCode")
	public String getLpCode() {
		return lpCode;
	}
	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}
	@Column(name="height")
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	@Column(name="length")
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	@Column(name="width")
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	@Column(name="volume")
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
}
