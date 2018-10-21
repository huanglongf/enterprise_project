package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "t_wh_priority_channel_oms")
public class PriorityChannelOms extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1023901049001961893L;

    private Long id;

    private String code;

    private Long qty;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "s_t_wh_priority_channel_oms", sequenceName = "s_t_wh_priority_channel_oms", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_t_wh_priority_channel_oms")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "qty")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

}
