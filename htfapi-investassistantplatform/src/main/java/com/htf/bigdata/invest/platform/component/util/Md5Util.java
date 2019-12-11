package com.htf.bigdata.invest.platform.component.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 * Created by chzhang@yoho.cn on 2015/11/6.
 */
public class Md5Util {

    private static final Logger logger = LogManager.getLogger(Md5Util.class);

    private static char[] hexChar = {
            '0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f'
    };

    /**
     * 对消息计算MD5， 结果用Hex（十六进制）编码
     *
     * @param message 消息
     * @return MD5之后的结果
     */
    public static String md5(String message) {
        return DigestUtils.md5Hex(message);
    }

    /**
     * 计算文件MD5
     * @author wuxiao
     */
    public static String md5File(File file) {
        try {
            InputStream ins = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            int len;
            while((len = ins.read(buffer)) != -1){
                md5.update(buffer, 0, len);
            }

            ins.close();
            byte[] bytes = md5.digest();
            StringBuilder sb = new StringBuilder(bytes.length*2);
            for (int i=0;i<bytes.length;i++){
                sb.append(hexChar[(bytes[i] & 0xf0) >>> 4]);
                sb.append(hexChar[bytes[i] & 0x0f]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
