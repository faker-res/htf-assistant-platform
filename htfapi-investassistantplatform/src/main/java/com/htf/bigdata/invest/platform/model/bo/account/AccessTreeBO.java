package com.htf.bigdata.invest.platform.model.bo.account;

import lombok.Data;

import java.util.List;

@Data
public class AccessTreeBO {

    private String id;

    private String name;

    private String description;

    private List<AccessTreeBO> child;
}
