package com.example.tourism.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ContactsBean {
    @Id(autoincrement = true)
    private Long contactsid;

    @NotNull
    private String cName;

    @NotNull
    private String ctellPhone;

    private String cQQ;

    private String cWechat;

    @NotNull
    private Date cDate;

    @Generated(hash = 930552217)
    public ContactsBean(Long contactsid, @NotNull String cName,
            @NotNull String ctellPhone, String cQQ, String cWechat,
            @NotNull Date cDate) {
        this.contactsid = contactsid;
        this.cName = cName;
        this.ctellPhone = ctellPhone;
        this.cQQ = cQQ;
        this.cWechat = cWechat;
        this.cDate = cDate;
    }

    @Generated(hash = 747317112)
    public ContactsBean() {
    }

    public Long getContactsid() {
        return this.contactsid;
    }

    public void setContactsid(Long contactsid) {
        this.contactsid = contactsid;
    }

    public String getCName() {
        return this.cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getCtellPhone() {
        return this.ctellPhone;
    }

    public void setCtellPhone(String ctellPhone) {
        this.ctellPhone = ctellPhone;
    }

    public String getCQQ() {
        return this.cQQ;
    }

    public void setCQQ(String cQQ) {
        this.cQQ = cQQ;
    }

    public String getCWechat() {
        return this.cWechat;
    }

    public void setCWechat(String cWechat) {
        this.cWechat = cWechat;
    }

    public Date getCDate() {
        return this.cDate;
    }

    public void setCDate(Date cDate) {
        this.cDate = cDate;
    }



}
