package com.jumbo.wms.model.vmi.philipsData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "t_philips_provice_code")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PhilipsProvice extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7942907906849024852L;

    private Long id;

    private String code;

    private String enName;

    private String name;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PHILIPS_PROVICE_CODE", sequenceName = "S_T_PHILIPS_PROVICE_CODE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PHILIPS_PROVICE_CODE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code", length = 10)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "E_NAME", length = 20)
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Column(name = "NAME", length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
