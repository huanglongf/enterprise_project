package com.jumbo.wms.model.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

/**
 * Copyright (c) 1999-2003 Erry Network Technology, Inc. All Rights Reserved.
 * <p/>
 * This software is the confidential and proprietary information of Erry Network Technology, Inc. (
 * "Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with Erry.
 * <p/>
 * ERRY MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERRY SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * <p/>
 * $Author: benjamin $ $Date: 2011-05-24 09:04:10 $ $Revision: 1.1 $
 */
@Entity
@Table(name = "SEQUENCECOUNTER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class SequenceCounter implements Serializable {

    private static final long serialVersionUID = 8684579052317558337L;
    private long id;
    private int counter;
    private String name;
    private String category;
    private int version;
    private Date changeDate;

    public SequenceCounter() {}

    public SequenceCounter(String name, String category, Date changeDate, int counter) {
        this.counter = counter;
        this.name = name;
        this.category = category;
        this.changeDate = changeDate;
    }

    public SequenceCounter(String name, Date changeDate, int counter) {
        this.counter = counter;
        this.name = name;
        this.changeDate = changeDate;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CQ", sequenceName = "S_SEQUENCECOUNTER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CQ")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "COUNTER")
    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Column(name = "NAME", length = 64, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CATEGORY", length = 64)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "CHANGEDATE")
    @Temporal(TemporalType.DATE)
    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SequenceCounter other = (SequenceCounter) obj;
        if (category == null) {
            if (other.category != null) {
                return false;
            }
        } else if (!category.equals(other.category)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
