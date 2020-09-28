package com.danqiu.myapplication.fragment.city;

public class CityBean {
    String abc;// "string",
    String city;// "string",
    String id;// "string",
    String ishot;// "string",
    String py;// "string",
    String spy;// "string",
    String stationName;// "string",
    String teleCode;// "string"
    String name;

    public CityBean(String stationName, String teleCode, String name) {
        this.stationName = stationName;
        this.teleCode = teleCode;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIshot() {
        return ishot;
    }

    public void setIshot(String ishot) {
        this.ishot = ishot;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getSpy() {
        return spy;
    }

    public void setSpy(String spy) {
        this.spy = spy;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getTeleCode() {
        return teleCode;
    }

    public void setTeleCode(String teleCode) {
        this.teleCode = teleCode;
    }
}
