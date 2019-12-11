package com.htf.bigdata.invest.platform.config.enums;

public enum GenderEnum {

    OTHER("0"),
    MALE("1"),
    FEMALE("2"),;

    private String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
