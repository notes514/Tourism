package com.example.tourism.entity;

import java.io.Serializable;

public class Country implements Serializable{

    private int countryId;
    private String countryName;
    private int countryPic;
    private String type;

    public Country(int countryId, String countryName, int countryPic,String type) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryPic = countryPic;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryPic() {
        return countryPic;
    }

    public void setCountryPic(int countryPic) {
        this.countryPic = countryPic;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                ", countryPic='" + countryPic + '\'' +
                '}';
    }
}
