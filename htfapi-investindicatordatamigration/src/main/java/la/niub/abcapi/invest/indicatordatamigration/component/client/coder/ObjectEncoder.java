package la.niub.abcapi.invest.indicatordatamigration.component.client.coder;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ObjectEncoder implements Encoder {
    @Override
    public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {
        Map<String,Object> params = (Map<String,Object>)o;
        Map<String,Object> querys = new HashMap<>();
        for (Map.Entry param : params.entrySet()){
            querys.putAll(bean2Map(param.getValue(),true));
        }

        Map<String,String> querysStr = new HashMap<>();
        for (Map.Entry<String,Object> entry : querys.entrySet()){
            if (entry.getValue() == null){
                continue;
            }

            String value = "";
            if (entry.getValue().getClass().equals(Boolean.class)){
                value = ((boolean)entry.getValue()) ? "1" : "0";
            }else if (entry.getValue().getClass().equals(Integer.class)){
                value = String.valueOf(entry.getValue());
            }else{
                value = String.valueOf(entry.getValue());
                try {
                    value = URLEncoder.encode(URLDecoder.decode(value,"UTF-8"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    continue;
                }
            }

            querysStr.put(entry.getKey(),value);
        }

        if (requestTemplate.method().equals("POST")){
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String,String> entry : querysStr.entrySet()){
                builder.append(entry.getKey()+"="+ entry.getValue()+"&");
            }
            String queryStr = StringUtils.stripEnd(builder.toString(),"&");

            requestTemplate.header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            requestTemplate.body(queryStr);
        }else{
            for (Map.Entry<String,String> entry : querysStr.entrySet()){
                requestTemplate.query(entry.getKey(),entry.getValue());
            }
        }
    }

    /**
     * 从bean转换为map
     * @param bean
     * @param includeParent 是否包含父类属性
     * @return
     */
    public static Map<String,Object> bean2Map(Object bean,Boolean includeParent) {
        Map<String,Object> map = new HashMap<>();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields){
            try {
                field.setAccessible(true);
                map.put(field.getName(),field.get(bean));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (includeParent){
            Class parent = bean.getClass().getSuperclass();
            while (!parent.equals(Object.class)){
                fields = parent.getDeclaredFields();
                for (Field field : fields){
                    try {
                        field.setAccessible(true);
                        map.put(field.getName(),field.get(bean));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                parent = parent.getClass().getSuperclass();
            }
        }
        return map;
    }
}
