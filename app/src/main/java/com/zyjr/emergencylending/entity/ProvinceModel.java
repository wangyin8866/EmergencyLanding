package com.zyjr.emergencylending.entity;

import java.util.List;

public class ProvinceModel {
    private String name;
    private String provinceCode;
    private List<CityModel> cityList;

    public ProvinceModel() {
        super();
    }

    public ProvinceModel(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public ProvinceModel(String name, List<CityModel> cityList) {
        super();
        this.name = name;
        this.cityList = cityList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }


    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }

    @Override
    public boolean equals(Object o) {
        return provinceCode.equals(((ProvinceModel) o).provinceCode);
    }

    @Override
    public String toString() {
        return "ProvinceModel{" +
                "name='" + name + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityList=" + cityList +
                '}';
    }

}
