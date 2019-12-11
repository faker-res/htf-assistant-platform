package com.htf.bigdata.invest.platform.config.enums;

public enum ContentTypeEnum {

    BMP("image/bmp"),
    GIF("image/gif"),
    JPEG("image/jpeg"),
    JPG("image/jpeg"),
    PNG("image/png"),
    HTML("text/html"),
    TXT("text/plain"),
    XML("text/xml"),
    VSD("application/vnd.visio"),
    PPT("application/vnd.ms-powerpoint"),
    PPTX("application/vnd.ms-powerpoint"),
    DOC("application/msword"),
    DOCX("application/msword"),
    XLS("application/msexcel"),
    XLSX("application/msexcel"),
    CSV("application/csv"),;
    
    private String contenType;

    ContentTypeEnum(String contenType) {
        this.contenType = contenType;
    }

    public String getContenType() {
        return contenType;
    }
}
