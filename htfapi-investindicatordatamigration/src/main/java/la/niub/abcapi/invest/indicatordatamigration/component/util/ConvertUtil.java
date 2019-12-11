package la.niub.abcapi.invest.indicatordatamigration.component.util;

import java.math.BigInteger;

/**
 * 类型转换
 */
public class ConvertUtil {

    /**
     * byte与int转换
     *
     * @param x
     * @return
     */
    public static byte intToByte(int x) {
        return (byte) x;
    }

    /**
     * byte与int转换
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }


    /**
     * byte[]与int转换
     *
     * @param b
     * @return
     */
    public static int byteArrayToInt(byte[] b) {
        return new BigInteger(1,b).intValue();
    }

    /**
     * byte[]与int转换
     *
     * @param a
     * @return
     */
    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
}
