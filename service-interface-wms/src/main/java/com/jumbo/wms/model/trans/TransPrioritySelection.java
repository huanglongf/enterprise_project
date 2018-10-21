package com.jumbo.wms.model.trans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 快递优先推荐关键字
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "T_BI_TRANS_PRIORITY_SELECTION")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class TransPrioritySelection extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    /**
     * PK
     */
    private Long id;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 类型
     */
    private TransPriSelType type;

    /**
     * 店铺id
     */
    private Long channelId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANS_PRIORITY", sequenceName = "S_T_BI_TRANS_PRIORITY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_PRIORITY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "keyword")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Column(name = "type", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.trans.TransPriSelType")})
    public TransPriSelType getType() {
        return type;
    }

    public void setType(TransPriSelType type) {
        this.type = type;
    }

    @Column(name = "channelId")
    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }


}
