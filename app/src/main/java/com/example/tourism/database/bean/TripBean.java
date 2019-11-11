package com.example.tourism.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TripBean {
    @Id(autoincrement = true)
    private Long tripId;

    @NotNull
    private String tName;

    @NotNull
    private String tIdentitycard;

    private String tType;

    @Generated(hash = 2108513820)
    public TripBean(Long tripId, @NotNull String tName,
            @NotNull String tIdentitycard, String tType) {
        this.tripId = tripId;
        this.tName = tName;
        this.tIdentitycard = tIdentitycard;
        this.tType = tType;
    }

    @Generated(hash = 533839730)
    public TripBean() {
    }

    public Long getTripId() {
        return this.tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getTName() {
        return this.tName;
    }

    public void setTName(String tName) {
        this.tName = tName;
    }

    public String getTIdentitycard() {
        return this.tIdentitycard;
    }

    public void setTIdentitycard(String tIdentitycard) {
        this.tIdentitycard = tIdentitycard;
    }

    public String getTType() {
        return this.tType;
    }

    public void setTType(String tType) {
        this.tType = tType;
    }



}
