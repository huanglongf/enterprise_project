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
@Table(name = "T_WH_TRANS_ZTO")
public class TransZtoInfo extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 6436267479669919578L;
    private Long id;
    private String userName;
    private String passWord;
    private Long ouId;
    
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_twts", sequenceName = "s_T_WH_TRANS_Zto", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_twts")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "user_name", length = 75)
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(name = "pass_word", length = 50)
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    @Column(name = "ou_id")
    public Long getOuId() {
        return ouId;
    }
    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }
    
    
}
