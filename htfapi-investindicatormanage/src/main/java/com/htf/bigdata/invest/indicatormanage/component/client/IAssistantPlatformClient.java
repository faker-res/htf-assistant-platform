package com.htf.bigdata.invest.indicatormanage.component.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.htf.bigdata.invest.indicatormanage.model.response.Response;
import com.htf.bigdata.invest.indicatormanage.model.response.client.AccountResponse;

/**
 * @description: 平台数据
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
@FeignClient(name = "${feign.client.platform.name}"/*, url = "${feign.client.platform.url}"*/)
public interface IAssistantPlatformClient {

    @GetMapping("/account/info")
    Response<AccountResponse> getUserInfo(@RequestParam("userId") String userId);
}
