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
 * 待创建队列-订单明细行特殊处理
 * @author jumbo
 *
 */
@Entity
@Table(name = "t_wh_q_gift_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class QueueGiftLine extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1892640227631766629L;
	/**
	 * PK
	 */
	private long id;
	/**
	 * 队列明细行
	 */
	private QueueStaLine queueStaLine;
	/**
	 * 信息备注
	 */
	private String memo;
	/**
	 * 礼品类型
	 */
	private int type;
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_Q_GIFT_LINE", sequenceName = "S_t_wh_q_gift_line", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Q_GIFT_LINE")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Q_STA_LINE_ID")
	@Index(name = "IDX_Q_STA_LINE")
	public QueueStaLine getQueueStaLine() {
		return queueStaLine;
	}
	public void setQueueStaLine(QueueStaLine queueStaLine) {
		this.queueStaLine = queueStaLine;
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
