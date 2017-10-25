package com.zyjr.emergencylending.entity;

import java.util.List;

public class CityModel {
    private String name;
    private String cityCode;
    private List<DistrictModel> districtList;

    public CityModel() {
        super();
    }

    public CityModel(String cityCode) {
        this.cityCode = cityCode;
    }

    public CityModel(String name, List<DistrictModel> districtList) {
        super();
        this.name = name;
        this.districtList = districtList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public List<DistrictModel> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictModel> districtList) {
        this.districtList = districtList;
    }

    @Override
    public boolean equals(Object o) {
        return cityCode.equals(((CityModel) o).cityCode);
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "name='" + name + '\'' +
                ", districtList=" + districtList +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }

}
