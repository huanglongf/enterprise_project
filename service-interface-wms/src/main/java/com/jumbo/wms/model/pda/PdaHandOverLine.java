package com.jumbo.wms.model.pda;

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

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_PDA_HANDOVER_LINE")
public class PdaHandOverLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4747306802045360826L;
    /**
     * id
     */
    private Long id;
    /**
     * 运单号
     */
    private String transNo;
    /**
     * 所属交接清单
     */
    private PdaOrder pdaOrder;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PDA_ORDER", sequenceName = "S_T_WH_PDA_HANDOVER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PDA_ORDER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRANS_NO", length = 20)
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PDA_ORDER_ID")
    @Index(name = "IDX_PDA_HAND_OVER_L_ID")
    public PdaOrder getPdaOrder() {
        return pdaOrder;
    }

    public void setPdaOrder(PdaOrder pdaOrder) {
        this.pdaOrder = pdaOrder;
    }

}
