package la.niub.abcapi.invest.indicatordatamigration.component.client.coder;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import la.niub.abcapi.invest.indicatordatamigration.component.util.RandomUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ObjectFormDataEncoder implements Encoder {
    @Override
    public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {
        Map<String,Object> params = (Map<String,Object>)o;
        Map<String,Object> querys = new HashMap<>();
        for (Map.Entry param : params.entrySet()){
            querys.putAll(bean2Map(param.getValue(),true));
        }

        String end = "\r\n";
        String twoHyphens = "--";
//        String boundary = "---------------------------681240323617137932268833";
        String boundary = "---------------------------"+ RandomUtil.getRandomStr(25,"PNUMBER");

        StringBuilder builder = new StringBuilder();
        for (Map.Entry param : querys.entrySet()){
            builder.append(twoHyphens + boundary + end);
            builder.append("Content-Disposition: form-data; name=\"");
            builder.append(param.getKey());
            builder.append("\"" + end);
            builder.append(end);
            builder.append(param.getValue());
            builder.append(end);
        }
        builder.append(twoHyphens + boundary + twoHyphens + end);
        builder.append(end);

        requestTemplate.header("Content-Type","multipart/form-data; boundary="+boundary);
        requestTemplate.body(builder.toString());
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
