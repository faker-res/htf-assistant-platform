package com.htf.bigdata.gateway.component.client;

import com.htf.bigdata.gateway.component.client.fallback.AssistanPlatformFallbackFactory;
import com.htf.bigdata.gateway.model.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "service-assistant-platform"
//        , url = "${feign.client.apiBestNews.url}"
        , fallbackFactory = AssistanPlatformFallbackFactory.class
//        , configuration = FeignObjectConfiguration.class
)
public interface IAssistanPlatformClient {

    @PostMapping(value = "/cas/set-token")
    Response setToken(@RequestParam("username") String username, @RequestParam("ticket") String ticket);

    @DeleteMapping(value = "/cas/remove-token")
    Response removeToken(@RequestParam("token") String token);

    @GetMapping(value = "/cas/verify-token")
    Response verifyToken(@RequestParam("userId") String userId, @RequestParam("token") String token);
}
