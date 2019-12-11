package la.niub.abcapi.invest.indicatordatamigration.component.client.coder;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import la.niub.abcapi.invest.indicatordatamigration.component.util.RandomUtil;

import java.lang.reflect.Type;
import java.util.Map;

public class FormDataEncoder implements Encoder {
    @Override
    public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {
        String end = "\r\n";
        String twoHyphens = "--";
//        String boundary = "---------------------------681240323617137932268833";
        String boundary = "---------------------------"+ RandomUtil.getRandomStr(25,"PNUMBER");

        Map<String,Object> params = (Map<String,Object>)o;
        StringBuilder builder = new StringBuilder();
        for (Map.Entry param : params.entrySet()){
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
}
