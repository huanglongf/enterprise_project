package com.jumbo.wms.model.warehouse;

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
/**
 * 待创建队列日志-订单明细行特殊处理
 * @author jumbo
 *
 */
@Entity
@Table(name = "t_wh_qlg_gift_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class QueueLogGiftLine extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3241995807259481497L;
	/**
	 * PK
	 */
	private long id;
	/**
	 * 队列明细行
	 */
	private QueueLogStaLine queueLogStaLine;
	/**
	 * 信息备注
	 */
	private String memo;
	/**
	 * 礼品类型
	 */
	private int type;
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "SEQ_T_WH_QLG_GIFT_LINE", sequenceName = "S_t_wh_qlg_gift_line", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_QLG_GIFT_LINE")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qlg_sta_line_id")
	@Index(name = "IDX_QLG_STA_LINE")
	public QueueLogStaLine getQueueLogStaLine() {
		return queueLogStaLine;
	}
	public void setQueueLogStaLine(QueueLogStaLine queueLogStaLine) {
		this.queueLogStaLine = queueLogStaLine;
	}
	@Column(name = "memo", length=100)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name = "type")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	

	

}
