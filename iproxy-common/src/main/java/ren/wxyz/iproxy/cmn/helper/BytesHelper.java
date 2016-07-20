package ren.wxyz.iproxy.cmn.helper;

/**
 * 字节帮助类
 *
 * @author wxyz
 * @since 0.0.1
 */
public class BytesHelper {
    /**
     * long型占用字节数
     */
    public static final int LONG_LENGTH = 8;

    /**
     * int型占用字节数
     */
    public static final int INTEGER_LENGTH = 4;

    /**
     * 将long型值转换为字节数组，存储模式为小端模式
     *
     * @param num long型值
     * @return 字节数组
     */
    public static byte[] toBytes(long num) {
        byte[] data = new byte[LONG_LENGTH];

        for (int i = 0; i < LONG_LENGTH; i++) {
            data[i] = (byte)((num >> (i * 8)) & 0xFF);
        }

        return data;
    }

    /**
     * 将int型值转换为字节数组，存储模式为小端模式
     *
     * @param num int型值
     * @return 字节数组
     */
    public static byte[] toBytes(int num) {
        byte[] data = new byte[INTEGER_LENGTH];

        for (int i = 0; i < INTEGER_LENGTH; i++) {
            data[i] = (byte)((num >> (i * 8)) & 0xFF);
        }

        return data;
    }

    /**
     * 将字节数组转换成long型数值，参数的存储模式为小端模式
     *
     * @param data 字节数组，存储模式为小端模式
     * @return long型数值
     */
    public static long toLong(byte[] data) {
        long num = 0;

        for (int i = LONG_LENGTH - 1; i > -1; i--) {
            num |= (data[i] & 0xFF) << (i * 8);
        }

        return num;
    }

    /**
     * 将字节数组转换成int型数值，参数的存储模式为小端模式
     *
     * @param data 字节数组，存储模式为小端模式
     * @return int型数值
     */
    public static int toInt(byte[] data) {
        int num = 0;

        for (int i = INTEGER_LENGTH - 1; i > -1; i--) {
            num |= (data[i] & 0xFF) << (i * 8);
        }

        return num;
    }
}
