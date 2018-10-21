package com.jumbo.wms.model.warehouse;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.BiChannel;

@Entity
@Table(name = "T_WH_BI_CHANNEL_REF")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class BiChannelRef extends BaseModel {


    private static final long serialVersionUID = 3650323050093331018L;
    /**
     * 渠道组ID
     */
    private BiChannelGroup biCg;

    /**
     * 渠道ID
     */
    private BiChannel biChannelId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "W_CG_ID")
    public BiChannelGroup getBiCg() {
        return biCg;
    }

    public void setBiCg(BiChannelGroup biCg) {
        this.biCg = biCg;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BI_CHANNEL_ID")
    public BiChannel getBiChannelId() {
        return biChannelId;
    }

    public void setBiChannelId(BiChannel biChannelId) {
        this.biChannelId = biChannelId;
    }

}
