package com.htf.bigdata.invest.platform.controller;

import com.htf.bigdata.invest.platform.model.response.Response;
import com.htf.bigdata.invest.platform.service.IDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共数据
 */
@RestController
@RequestMapping(path = "/data")
public class DataController {

    private final static Logger logger = LogManager.getLogger(DataController.class);

    @Autowired
    private IDataService dataService;

    /**
     * 基金公司列表
     * @return
     */
    @GetMapping(path = "/fund")
    Response fund() {
        return new Response(dataService.getFund());
    }

    /**
     * 券商公司列表
     * @return
     */
    @GetMapping(path = "/broker")
    Response broker() {
        return new Response(dataService.getBroker());
    }

    /**
     * 行业列表
     * @return
     */
    @GetMapping(path = "/industry")
    Response industry() {
        return new Response(dataService.getIndustry());
    }
}
