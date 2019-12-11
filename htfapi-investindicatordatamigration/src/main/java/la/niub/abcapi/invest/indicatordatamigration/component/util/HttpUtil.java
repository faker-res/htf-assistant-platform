package la.niub.abcapi.invest.indicatordatamigration.component.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class HttpUtil {

    public static RequestConfig getConfig(){
        return RequestConfig.custom()
                .setConnectTimeout(500)
                .setConnectionRequestTimeout(5000)
                .build();
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers) {
        RequestConfig requestConfig = getConfig();
        HttpClient httpClient = null;
        HttpResponse httpResponse = null;

        HttpPost httpPost = new HttpPost(url);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }

        try {

            if (params != null && !params.isEmpty()) {
                List<NameValuePair> list = new LinkedList<NameValuePair>();
                for (Map.Entry<String, String> param : params.entrySet()) {
                    list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"utf-8");
                httpPost.setEntity(formEntity);
            }

            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException("got error from http post:" + url,e);
        }


    }

    public static String postBody(String url, String json) {
        RequestConfig requestConfig = getConfig();
        HttpClient httpClient = null;
        HttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost(url);

        try {
            StringEntity postString = new StringEntity(json);
            httpPost.setEntity(postString);
            httpPost.setHeader("Content-type", "application/json");
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException("got error from http post:" + url,e);
        }
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers){
        RequestConfig requestConfig = getConfig();

        if (params != null && !params.isEmpty()) {
            String paramUrl = "";
            for (Map.Entry<String, String> param : params.entrySet()) {
                paramUrl += "&" + param.getKey() + "=" + param.getValue();
            }
            url += url.contains("?") ? paramUrl : ("?" + paramUrl);
        }
        HttpGet httpGet = new HttpGet(url);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpGet.setHeader(header.getKey(), header.getValue());
            }
        }

        HttpClient httpClient = null;
        HttpResponse httpResponse = null;

        try {
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity,"UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("got error from http get:" + url,e);
        }
    }

}
