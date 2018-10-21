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
@Table(name = "T_WH_PDA_ORDER_LINE_SN")
public class PdaOrderLineSn extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1380141218816372625L;
    /**
     * id
     */
    private Long id;
    /**
     * sn号
     */
    private String snCode;
    /**
     * 所属明细
     */
    private PdaOrderLine pdaOrderLine;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PDA_ORDER", sequenceName = "S_T_WH_PDA_ORDER_LINE_SN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PDA_ORDER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SN_CODE", length = 50)
    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PDA_ORDER_LINE_ID")
    @Index(name = "IDX_PDA_ODERR_LINE_S_ID")
    public PdaOrderLine getPdaOrderLine() {
        return pdaOrderLine;
    }

    public void setPdaOrderLine(PdaOrderLine pdaOrderLine) {
        this.pdaOrderLine = pdaOrderLine;
    }
}
